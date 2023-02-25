package br.com.angeloni.precos.server.service;

import br.com.angeloni.precos.server.csv.*;
import br.com.angeloni.precos.server.data.AuditoriaLog;
import br.com.angeloni.precos.server.erp.api.ControlePrecoApi;
import br.com.angeloni.precos.server.erp.model.ControlePreco;
import br.com.angeloni.precos.server.erp.model.ControlePrecoItem;
import br.com.angeloni.precos.server.erp.model.RetornoControlePreco;
import br.com.angeloni.ws.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeFactory;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.util.stream.Collectors;

import static br.com.angeloni.precos.server.util.PrecosUtil.*;

@Service
@Slf4j
public class PromocaoService {

  private static final int PROMOTIONS_BATCH_SIZE = 100;

  private static final DateTimeFormatter OFERTA_FORMATTER =
    new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter();

  @Autowired
  private AtgService atgService;

  @Autowired
  private ControlePrecoApi controlePrecoApi;

  public void ativarPromocoes(AuditoriaLog auditoriaLog, List<AtivarPromocaoBean> promocoes) {
    if (promocoes.isEmpty()) {
      auditoriaLog.logError("Nenhuma promoção para ativar!");
      return;
    }

    Map<TipoPromocao, List<AtivarPromocaoBean>> grupoPromocoes =
      promocoes.stream().collect(Collectors.groupingBy(p -> p.getPromocao().getTipoPromocao()));

    for (Map.Entry<TipoPromocao, List<AtivarPromocaoBean>> e : grupoPromocoes.entrySet()) {
      batches(e.getValue(), PROMOTIONS_BATCH_SIZE)
        .forEach(chunk -> ativarPromocoesBatch(auditoriaLog, e.getKey(), chunk));
    }
  }

  private void ativarPromocoesBatch(AuditoriaLog auditoriaLog, TipoPromocao tipo, List<AtivarPromocaoBean> promocoes) {
    auditoriaLog.log("Ativando promoções do tipo " + tipo + "...");

    switch (tipo) {
      case PV:
        ativarPackVirtual(auditoriaLog, promocoes);
        break;
      case L:
        ativarLevePague(auditoriaLog, promocoes);
        break;
      case B:
        ativarBrinde(auditoriaLog, promocoes);
        break;
      case U:
        ativarUltimoItem(auditoriaLog, promocoes);
        break;
      case P:
        ativarPatamar(auditoriaLog, promocoes);
        break;
      case PN:
        ativarPatamarNivel(auditoriaLog, promocoes);
        break;
      default:
        auditoriaLog.logError("Tipo de promoção (" + tipo + ") inválido!");
    }
  }

  private void controleDePrecoSami(AuditoriaLog auditoriaLog, AtivarPromocaoBean promo) {
    try {
      for (Long loja : promo.getPromocao().getLojas()) {
        ControlePreco controlePreco = new ControlePreco();
        controlePreco.setDataInicio(promo.getPromocao().getDataInicio().toInstant(ZoneOffset.UTC));
        controlePreco.setDataFim(promo.getPromocao().getDataFim().toInstant(ZoneOffset.UTC));
        controlePreco.setIdUnidade(loja.toString());

        List<ControlePrecoItem> listItemControle = new ArrayList<>();
        ControlePrecoItem item = new ControlePrecoItem();
        item.setIdProduto(promo.getPromocao().getProduto().toString());
        item.setRetornarPreco(promo.getPromocao().getRetornoSAMI());
        listItemControle.add(item);

        controlePreco.setItens(listItemControle);

        RetornoControlePreco retornoControlePreco = controlePrecoApi.controlePrecoV01Post(controlePreco)
                .getDsStatus().getRetorno().get(0);
        if (!retornoControlePreco.isSucesso()) {
          auditoriaLog.logError("Problema ao processar o controle de preço do item "
                  + promo.getPromocao().getProduto() + " no ERP: " + retornoControlePreco.getDescricao());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * PN - Patamar em Nível
   * <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.integration.angeloni.com.br/">
   * <soapenv:Header />
   * <soapenv:Body>
   * <ws:setBulkPrice>
   * <dataRequest>
   * <items>
   * <action>SET</action>
   * <displayName>Oferta 1234 valida de 01/05/2020 até 20/05/2020</displayName>
   * <initialDate>2019-04-05T09:30:00</initialDate>
   * <finalDate>2019-04-15T09:55:00</finalDate>
   * <id>P153209242944451</id>
   * <mainProductId>3660927</mainProductId>
   * <price>
   * <id>plist2470188</id>
   * <itemPriceListInfo>
   * <action>SET</action>
   * <margin>0</margin>
   * <price>19.57</price>
   * <priceLevel>
   * <price>19.57</price>
   * <quantity>1</quantity>
   * </priceLevel>
   * <priceLevel>
   * <price>19.00</price>
   * <quantity>2</quantity>
   * </priceLevel>
   * <promotionId>P153209242944451</promotionId>
   * <skuId>3660927</skuId>
   * </itemPriceListInfo>
   * </price>
   * <finalPrices>
   * <id>plist2470188</id>
   * <itemPriceListInfo>
   * <action>SET</action>
   * <margin>0</margin>
   * <price>19.57</price>
   * <promotionId>P153209242944451</promotionId>
   * <skuId>3660927</skuId>
   * </itemPriceListInfo>
   * </finalPrices>
   * <sites>
   * <string>super</string>
   * <string>superMobile</string>
   * </sites>
   * </items>
   * </dataRequest>
   * </ws:setBulkPrice>
   * </soapenv:Body>
   * </soapenv:Envelope>
   */
  private void ativarPatamarNivel(AuditoriaLog auditoriaLog, List<AtivarPromocaoBean> promocoes) {
    String produtos = StringUtils.join(promocoes.stream().map(p -> p.getPromocao().getProduto()).collect(Collectors.toList()), ",");
    try {
      BulkPriceListInfo patamar = new BulkPriceListInfo();
      for (AtivarPromocaoBean promo : promocoes) {
        BulkPriceInfo item = new BulkPriceInfo();
        String id = gerarPromocaoId();
        String displayName = "Oferta válida de " + promo.getPromocao().getDataInicio().format(OFERTA_FORMATTER) + " até "
          + promo.getPromocao().getDataFim().format(OFERTA_FORMATTER);
        item.setAction("SET");
        item.setUserId(auditoriaLog.getAuditoria().getUsuario());
        item.setId(id);
        item.setDisplayName(displayName);
        item.setInitialDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(promo.getPromocao().getDataInicio().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        item.setFinalDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(promo.getPromocao().getDataFim().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        item.setMainProductId(promo.getPromocao().getProduto().toString());
        item.getSites().addAll(gerarSitesPromocao(promo.getPromocao().getLojas()));
        for (ListaPromocaoBean lista : promo.getCodigosLista()) {
          PriceListPromotionInfo priceInfo = new PriceListPromotionInfo();
          priceInfo.setId(id);
          ItemPriceListInfo itemPriceInfo = new ItemPriceListInfo();
          itemPriceInfo.setAction("SET");
          itemPriceInfo.setMargin(0d);
          itemPriceInfo.setPrice(lista.getValorLista().doubleValue());
          for (NivelPromocaoBean nivel : lista.getValorNiveis()) {
            PriceLevelInfo priceLevel = new PriceLevelInfo();
            priceLevel.setQuantity(nivel.getNivel());
            priceLevel.setPrice(nivel.getValor().doubleValue());
            itemPriceInfo.getPriceLevel().add(priceLevel);
          }
          itemPriceInfo.setPromotionId(id);
          itemPriceInfo.setSkuId(promo.getPromocao().getProduto().toString());
          priceInfo.setItemPriceListInfo(itemPriceInfo);
          item.getPrice().add(priceInfo);

          PriceListPromotionInfo priceInfoFinal = new PriceListPromotionInfo();
          priceInfoFinal.setId(id);
          ItemPriceListInfo itemPriceInfoFinal = new ItemPriceListInfo();
          itemPriceInfoFinal.setAction("SET");
          itemPriceInfoFinal.setMargin(0d);
          itemPriceInfoFinal.setPrice(lista.getValorLista().doubleValue());
          itemPriceInfoFinal.setPromotionId(id);
          itemPriceInfoFinal.setSkuId(promo.getPromocao().getProduto().toString());
          priceInfoFinal.setItemPriceListInfo(itemPriceInfoFinal);
          item.getFinalPrices().add(priceInfoFinal);
        }
        patamar.getItems().add(item);
      }
      List<String> result = atgService.setBulkPrice(patamar);
      auditoriaLog.log("Patamar em Nível ativado com sucesso para os produtos: " + produtos + ", resultado: " + StringUtils.join(result, " ,"));
    } catch (Exception ex) {
      auditoriaLog.logError("Não foi possível ativar o Patamar em Nível para os produtos: " + produtos + ", erro: " + ex.getMessage());
      log.error(ex.getMessage(), ex);
    }
  }

  /**
   * P - Patamar
   * <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.integration.angeloni.com.br/">
   * <soapenv:Header />
   * <soapenv:Body>
   * <ws:setBuyXOrMorePayY>
   * <dataRequest>
   * <items>
   * <action>SET</action>
   * <displayName>Oferta 1234 valida de 01/05/2020 até 20/05/2020</displayName>
   * <id>P153209242944451</id>
   * <mainProductId>2225018</mainProductId>
   * <initialDate>2019-04-01T09:15:00</initialDate>
   * <finalDate>2019-04-10T09:20:00</finalDate>
   * <price>
   * <id>plist2470188</id>
   * <itemPriceListInfo>
   * <action>SET</action>
   * <margin>0</margin>
   * <price>60</price>
   * <priceLevel>
   * <price>60</price>
   * <quantity>1</quantity>
   * </priceLevel>
   * <promotionId>P153209242944451</promotionId>
   * <skuId>2225018</skuId>
   * </itemPriceListInfo>
   * </price>
   * <finalPrices>
   * <id>plist2470188</id>
   * <itemPriceListInfo>
   * <action>SET</action>
   * <margin>0</margin>
   * <price>170</price>
   * <promotionId>P153209242944451</promotionId>
   * <skuId>2225018</skuId>
   * </itemPriceListInfo>
   * </finalPrices>
   * <sites>
   * <string>super</string>
   * <string>superMobile</string>
   * </sites>
   * </items>
   * </dataRequest>
   * </ws:setBuyXOrMorePayY>
   * </soapenv:Body>
   * </soapenv:Envelope>
   */
  private void ativarPatamar(AuditoriaLog auditoriaLog, List<AtivarPromocaoBean> promocoes) {
    String produtos = StringUtils.join(promocoes.stream().map(p -> p.getPromocao().getProduto()).collect(Collectors.toList()), ",");
    try {
      BuyXOrMorePayYListInfo patamar = new BuyXOrMorePayYListInfo();
      for (AtivarPromocaoBean promo : promocoes) {
        BuyXOrMorePayYInfo item = new BuyXOrMorePayYInfo();
        String id = gerarPromocaoId();
        String displayName = "Oferta válida de " + promo.getPromocao().getDataInicio().format(OFERTA_FORMATTER) + " até "
          + promo.getPromocao().getDataFim().format(OFERTA_FORMATTER);
        item.setAction("SET");
        item.setUserId(auditoriaLog.getAuditoria().getUsuario());
        item.setId(id);
        item.setDisplayName(displayName);
        item.setInitialDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(promo.getPromocao().getDataInicio().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        item.setFinalDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(promo.getPromocao().getDataFim().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        item.setMainProductId(promo.getPromocao().getProduto().toString());
        item.getSites().addAll(gerarSitesPromocao(promo.getPromocao().getLojas()));
        for (ListaPromocaoBean lista : promo.getCodigosLista()) {
          PriceListPromotionInfo priceInfo = new PriceListPromotionInfo();
          priceInfo.setId(lista.getCodigo());
          ItemPriceListInfo itemPriceInfo = new ItemPriceListInfo();
          itemPriceInfo.setAction("SET");
          itemPriceInfo.setMargin(0d);
          itemPriceInfo.setPrice(lista.getValorLista().doubleValue());

          PriceLevelInfo priceLevelNormal = new PriceLevelInfo();
          priceLevelNormal.setPrice(lista.getValorLista().doubleValue());
          priceLevelNormal.setQuantity(1L);
          itemPriceInfo.getPriceLevel().add(priceLevelNormal);

          PriceLevelInfo priceLevelPatamar = new PriceLevelInfo();

          priceLevelPatamar.setPrice(promo.getPromocao().getValorDoado().doubleValue());
          priceLevelPatamar.setQuantity(promo.getPromocao().getQuantidadePagar().longValue());
          itemPriceInfo.getPriceLevel().add(priceLevelPatamar);

          itemPriceInfo.setPromotionId(id);
          itemPriceInfo.setSkuId(promo.getPromocao().getProduto().toString());
          priceInfo.setItemPriceListInfo(itemPriceInfo);
          item.getPrice().add(priceInfo);

          PriceListPromotionInfo priceInfoFinal = new PriceListPromotionInfo();
          priceInfoFinal.setId(id);
          ItemPriceListInfo itemPriceInfoFinal = new ItemPriceListInfo();
          itemPriceInfoFinal.setAction("SET");
          itemPriceInfoFinal.setMargin(0d);
          itemPriceInfoFinal.setPrice(lista.getValorLista().doubleValue());
          itemPriceInfoFinal.setPromotionId(id);
          itemPriceInfoFinal.setSkuId(promo.getPromocao().getProduto().toString());
          priceInfoFinal.setItemPriceListInfo(itemPriceInfoFinal);
          item.getFinalPrices().add(priceInfoFinal);
        }
        patamar.getItems().add(item);
      }
      List<String> result = atgService.setBuyXOrMorePayY(patamar);
      auditoriaLog.log("Patamar ativado com sucesso para os produtos: " + produtos + ", resultado: " + StringUtils.join(result, " ,"));

      for (AtivarPromocaoBean promo : promocoes) {
        controleDePrecoSami(auditoriaLog, promo);
      }
    } catch (Exception ex) {
      auditoriaLog.logError("Não foi possível ativar o Patamar para os produtos: " + produtos + ", erro: " + ex.getMessage());
      log.error(ex.getMessage(), ex);
    }
  }

  /**
   * U - Ultimo Item
   * <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.integration.angeloni.com.br/">
   * <soapenv:Header/>
   * <soapenv:Body>
   * <ws:setPromotionLastItem>
   * <dataRequest>
   * <items>
   * <action>SET</action>
   * <buyQuantity>2.0</buyQuantity>
   * <discount>50.0</discount>
   * <displayName>Oferta 1234 valida de 01/05/2020 até 20/05/2020</displayName>
   * <erpId>5</erpId>
   * <finalDate>
   * <time>1574387940000</time>
   * <timezone>America/Sao_Paulo</timezone>
   * </finalDate>
   * <id>B153248443693401</id>
   * <initialDate>
   * <time>1572573601000</time>
   * <timezone>America/Sao_Paulo</timezone>
   * </initialDate>
   * <mainProductId>4369340</mainProductId>
   * <prices>
   * <price>9.99</price>
   * <priceList>plist2470182</priceList>
   * </prices>
   * <prices>
   * <price>9.99</price>
   * <priceList>plist3020067</priceList>
   * </prices>
   * <prices>
   * <price>9.4905</price>
   * <priceList>plist3020068</priceList>
   * </prices>
   * <sites>
   * <string>super</string>
   * <string>superMobile</string>
   * </sites>
   * <skuId>4369340</skuId>
   * <storeIds>
   * <string>7</string>
   * <string>6</string>
   * <string>8</string>
   * <string>12</string>
   * <string>14</string>
   * <string>15</string>
   * <string>16</string>
   * <string>21</string>
   * <string>22</string>
   * </storeIds>
   * <typeDiscount>percentOff</typeDiscount>
   * <useSelo>true</useSelo>
   * </items>
   * </dataRequest>
   * </ws:setPromotionLastItem>
   * </soapenv:Body>
   * </soapenv:Envelope>
   */
  private void ativarUltimoItem(AuditoriaLog auditoriaLog, List<AtivarPromocaoBean> promocoes) {
    String produtos = StringUtils.join(promocoes.stream().map(p -> p.getPromocao().getProduto()).collect(Collectors.toList()), ",");
    try {
      PromotionLastItemListInfo ultimoItem = new PromotionLastItemListInfo();
      for (AtivarPromocaoBean promo : promocoes) {
        PromotionLastItemInfo item = new PromotionLastItemInfo();
        String id = gerarPromocaoId();
        String displayName = "Oferta válida de " + promo.getPromocao().getDataInicio().format(OFERTA_FORMATTER) + " até "
          + promo.getPromocao().getDataFim().format(OFERTA_FORMATTER);
        item.setAction("SET");
        item.setBuyQuantity(promo.getPromocao().getQuantidadePagar().doubleValue());
        item.setDiscount(promo.getPromocao().getPercentualDesconto().doubleValue());
        item.setTypeDiscount("percentOff");
        item.setUseSelo(true);
        item.setUserId(auditoriaLog.getAuditoria().getUsuario());
        item.setId(id);
        item.setDisplayName(displayName);
        item.setErpId(id);
        item.setInitialDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(promo.getPromocao().getDataInicio().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        item.setFinalDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(promo.getPromocao().getDataFim().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        item.setMainProductId(promo.getPromocao().getProduto().toString());
        item.setSkuId(promo.getPromocao().getProduto().toString());
        item.getStoreIds().addAll(promo.getPromocao().getLojas().stream().map(l -> Long.toString(l)).collect(Collectors.toList()));
        item.getSites().addAll(gerarSitesPromocao(promo.getPromocao().getLojas()));
        for (ListaPromocaoBean lista : promo.getCodigosLista()) {
          VirtualPackPriceInfo priceInfo = new VirtualPackPriceInfo();
          priceInfo.setPriceList(lista.getCodigo());
          priceInfo.setPrice(lista.getValorLista().doubleValue());
          item.getPrices().add(priceInfo);

          VirtualPackPriceInfo priceInfoFinal = new VirtualPackPriceInfo();
          priceInfoFinal.setPriceList(lista.getCodigo());
          priceInfoFinal.setPrice(lista.getValorLista().doubleValue());
          item.getFinalPrices().add(priceInfoFinal);
        }
        ultimoItem.getItems().add(item);
      }
      List<String> result = atgService.setPromotionLastItem(ultimoItem);

      auditoriaLog.log("Desconto Último Item ativado com sucesso para os produtos: " + produtos + ", resultado: " + StringUtils.join(result, " ,"));

      for (AtivarPromocaoBean promo : promocoes) {
        controleDePrecoSami(auditoriaLog, promo);
      }
    } catch (Exception ex) {
      auditoriaLog.logError("Não foi possível ativar o Desconto Último Item para os produtos: " + produtos + ", erro: " + ex.getMessage());
      log.error(ex.getMessage(), ex);
    }
  }

  /**
   * B - Brinde
   * <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.integration.angeloni.com.br/">
   * <soapenv:Header/>
   * <soapenv:Body>
   * <ws:setGifts>
   * <dataRequest>
   * <items>
   * <action>SET</action>
   * <buyQuantity>1.0</buyQuantity>
   * <displayName>Oferta 137294784176 valida de 01/05/2020 até 20/05/2020</displayName>
   * <finalDate>
   * <time>1575079201000</time>
   * <timezone>America/Sao_Paulo</timezone>
   * </finalDate>
   * <giftSku>784176</giftSku>
   * <id>137294784176</id>
   * <incrementItens>false</incrementItens>
   * <initialDate>
   * <time>1573005601000</time>
   * <timezone>America/Sao_Paulo</timezone>
   * </initialDate>
   * <mainSku>137294</mainSku>
   * <sites>
   * <string>7</string>
   * </sites>
   * <skuGiftQuantity>1</skuGiftQuantity>
   * </items>
   * </dataRequest>
   * </ws:setGifts>
   * </soapenv:Body>
   * </soapenv:Envelope>
   */
  private void ativarBrinde(AuditoriaLog auditoriaLog, List<AtivarPromocaoBean> promocoes) {
    String produtos = StringUtils.join(promocoes.stream().map(p -> p.getPromocao().getProduto()).collect(Collectors.toList()), ",");
    try {
      GiftsListInfo brinde = new GiftsListInfo();
      for (AtivarPromocaoBean promo : promocoes) {
        GiftsInfo item = new GiftsInfo();
        String id = gerarPromocaoId();
        String displayName = "Oferta válida de " + promo.getPromocao().getDataInicio().format(OFERTA_FORMATTER) + " até "
          + promo.getPromocao().getDataFim().format(OFERTA_FORMATTER);
        item.setAction("SET");
        item.setBuyQuantity(promo.getPromocao().getQuantidadePagar().doubleValue());
        item.setId(id);
        item.setDisplayName(displayName);
        item.setErpId(id);
        item.setInitialDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(promo.getPromocao().getDataInicio().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        item.setFinalDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(promo.getPromocao().getDataFim().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        item.setGiftSku(promo.getPromocao().getProdutosDoados().get(0).toString());
        item.setSkuGiftQuantity(promo.getPromocao().getQuantidadeDoado().intValue());
        item.setMainSku(promo.getPromocao().getProduto().toString());
        item.setIncrementItens(false);
        item.getSites().addAll(promo.getPromocao().getLojas().stream().map(l -> Long.toString(l)).collect(Collectors.toList()));
        brinde.getItems().add(item);
      }
      List<String> result = atgService.setGifts(brinde);
      auditoriaLog.log("Brinde ativado com sucesso para os produtos: " + produtos + ", resultado: " + StringUtils.join(result, " ,"));
    } catch (Exception ex) {
      auditoriaLog.logError("Não foi possível ativar o Brinde para os produtos: " + produtos + ", erro: " + ex.getMessage());
      log.error(ex.getMessage(), ex);
    }
  }

  /**
   * L - Leve Pague
   * <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.integration.angeloni.com.br/">
   * <soapenv:Header/>
   * <soapenv:Body>
   * <ws:setGetXPayY>
   * <dataRequest>
   * <items>
   * <action>SET</action>
   * <buyQuantity>4.0</buyQuantity>
   * <erpId>5</erpId>
   * <displayName>Oferta 1234 valida de 01/05/2020 até 20/05/2020</displayName>
   * <finalDate>
   * <time>1562900340000</time>
   * <timezone>America/Sao_Paulo</timezone>
   * </finalDate>
   * <id>G142644740346241</id>
   * <initialDate>
   * <time>1562727601000</time>
   * <timezone>America/Sao_Paulo</timezone>
   * </initialDate>
   * <mainProductId>4034624</mainProductId>
   * <prices>
   * <price>1.99</price>
   * <priceList>plist2470190</priceList>
   * </prices>
   * <prices>
   * <price>1.99</price>
   * <priceList>plist3020079</priceList>
   * </prices>
   * <prices>
   * <price>1.8905</price>
   * <priceList>plist3020080</priceList>
   * </prices>
   * <prices>
   * <price>1.8905</price>
   * <priceList>plist3020082</priceList>
   * </prices>
   * <sites>
   * <string>super</string>
   * <string>superMobile</string>
   * </sites>
   * <skuId>4034624</skuId>
   * <spendQuantity>3.0</spendQuantity>
   * <storeIds>
   * <string>15</string>
   * </storeIds>
   * </items>
   * </dataRequest>
   * </ws:setGetXPayY>
   * </soapenv:Body>
   * </soapenv:Envelope>
   */
  private void ativarLevePague(AuditoriaLog auditoriaLog, List<AtivarPromocaoBean> promocoes) {
    String produtos = StringUtils.join(promocoes.stream().map(p -> p.getPromocao().getProduto()).collect(Collectors.toList()), ",");
    try {
      GetXPayYListInfo levePague = new GetXPayYListInfo();
      for (AtivarPromocaoBean promo : promocoes) {
        GetXPayYInfo item = new GetXPayYInfo();
        String id = gerarPromocaoId();
        String displayName = "Oferta válida de " + promo.getPromocao().getDataInicio().format(OFERTA_FORMATTER) + " até "
          + promo.getPromocao().getDataFim().format(OFERTA_FORMATTER);
        item.setAction("SET");
        item.setBuyQuantity(promo.getPromocao().getQuantidadePagar().doubleValue());
        item.setSpendQuantity(promo.getPromocao().getQuantidadePagar().subtract(promo.getPromocao().getQuantidadeDoado()).doubleValue());
        item.setId(id);
        item.setUserId(auditoriaLog.getAuditoria().getUsuario());
        item.setDisplayName(displayName);
        item.setErpId(id);
        item.setInitialDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(promo.getPromocao().getDataInicio().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        item.setFinalDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(promo.getPromocao().getDataFim().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        item.setMainProductId(promo.getPromocao().getProduto().toString());
        item.setSkuId(promo.getPromocao().getProduto().toString());
        item.getStoreIds().addAll(promo.getPromocao().getLojas().stream().map(l -> Long.toString(l)).collect(Collectors.toList()));
        item.getSites().addAll(gerarSitesPromocao(promo.getPromocao().getLojas()));
        for (ListaPromocaoBean lista : promo.getCodigosLista()) {
          VirtualPackPriceInfo priceInfo = new VirtualPackPriceInfo();
          priceInfo.setPriceList(lista.getCodigo());
          priceInfo.setPrice(lista.getValorLista().doubleValue());
          item.getPrices().add(priceInfo);

          VirtualPackPriceInfo priceInfoFinal = new VirtualPackPriceInfo();
          priceInfoFinal.setPriceList(lista.getCodigo());
          priceInfoFinal.setPrice(lista.getValorLista().doubleValue());
          item.getFinalPrices().add(priceInfoFinal);
        }
        levePague.getItems().add(item);
      }
      List<String> result = atgService.setGetXPayY(levePague);
      auditoriaLog.log("Leve e Pague ativado com sucesso para os produtos: " + produtos + ", resultado: " + StringUtils.join(result, " ,"));

      for (AtivarPromocaoBean promo : promocoes) {
        controleDePrecoSami(auditoriaLog, promo);
      }
    } catch (Exception ex) {
      auditoriaLog.logError("Não foi possível ativar o Leve e Pague para os produtos: " + produtos + ", erro: " + ex.getMessage());
      log.error(ex.getMessage(), ex);
    }
  }

  /**
   * PV - Pack Virtual
   * <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.integration.angeloni.com.br/">
   * <soapenv:Header/>
   * <soapenv:Body>
   * <ws:setVirtualPack>
   * <dataRequest>
   * <items>
   * <action>SET</action>
   * <buyQuantity>1.0</buyQuantity>
   * <id>P153209242944451</id>
   * <displayName>Oferta 1234 valida de 01/05/2020 até 20/05/2020</displayName>
   * <virtualPackErp>153209</virtualPackErp>
   * <virtualPackMessage>Oferta 1234 valida de 01/05/2020 até 20/05/2020</virtualPackMessage>
   * <initialDate>
   * <time>1572573601000</time>
   * <timezone>America/Sao_Paulo</timezone>
   * </initialDate>
   * <finalDate>
   * <time>1574387940000</time>
   * <timezone>America/Sao_Paulo</timezone>
   * </finalDate>
   * <replaceValue>9.9</replaceValue>
   * <sites>
   * <string>super</string>
   * <string>superMobile</string>
   * </sites>
   * <mainProductId>4294445</mainProductId>
   * <skus>
   * <string>4226409</string>
   * <string>4226436</string>
   * </skus>
   * <storeIds>
   * <string>7</string>
   * <string>6</string>
   * </storeIds>
   * </items>
   * </dataRequest>
   * </ws:setVirtualPack>
   * </soapenv:Body>
   * </soapenv:Envelope>
   */
  private void ativarPackVirtual(AuditoriaLog auditoriaLog, List<AtivarPromocaoBean> promocoes) {
    String produtos = StringUtils.join(promocoes.stream().map(p -> p.getPromocao().getProduto()).collect(Collectors.toList()), ",");
    try {
      VirtualPackServiceListInfo virtualPack = new VirtualPackServiceListInfo();
      for (AtivarPromocaoBean promo : promocoes) {
        VirtualPackServiceInfo item = new VirtualPackServiceInfo();
        String id = gerarPromocaoId();
        String displayName = "Oferta válida de " + promo.getPromocao().getDataInicio().format(OFERTA_FORMATTER) + " até "
          + promo.getPromocao().getDataFim().format(OFERTA_FORMATTER);
        item.setAction("SET");
        item.setBuyQuantity(promo.getPromocao().getQuantidadePagar().doubleValue());
        item.setId(id);
        item.setDisplayName(displayName);
        item.setVirtualPackErp(id);
        item.setVirtualPackMessage(displayName);
        item.setInitialDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(promo.getPromocao().getDataInicio().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        item.setFinalDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(promo.getPromocao().getDataFim().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        item.setReplaceValue(promo.getValor().doubleValue());
        item.setMainProductId(promo.getPromocao().getProduto().toString());
        item.getSkus().addAll(promo.getPromocao().getProdutosDoados().stream().map(l -> Long.toString(l)).collect(Collectors.toList()));
        item.getStoreIds().addAll(promo.getPromocao().getLojas().stream().map(l -> Long.toString(l)).collect(Collectors.toList()));
        item.getSites().addAll(gerarSitesPromocao(promo.getPromocao().getLojas()));
        virtualPack.getItems().add(item);
      }
      List<String> result = atgService.setVirtualPack(virtualPack);
      auditoriaLog.log("Pack Virtual ativado com sucesso para os produtos: " + produtos + ", resultado: " + StringUtils.join(result, " ,"));
    } catch (Exception ex) {
      auditoriaLog.logError("Não foi possível ativar o Pack Virtual para os produtos: " + produtos + ", erro: " + ex.getMessage());
      log.error(ex.getMessage(), ex);
    }
  }

}
