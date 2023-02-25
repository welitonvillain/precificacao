package br.com.angeloni.precos.server.service;

import br.com.angeloni.precos.server.csv.ListaPrecoBean;
import br.com.angeloni.precos.server.data.AuditoriaLog;
import br.com.angeloni.precos.server.erp.api.ControlePrecoApi;
import br.com.angeloni.precos.server.erp.model.ControlePreco;
import br.com.angeloni.precos.server.erp.model.ControlePrecoItem;
import br.com.angeloni.precos.server.erp.model.RetornoControlePreco;
import br.com.angeloni.precos.server.util.PrecosUtil;
import br.com.angeloni.ws.ItemPriceListInfo;
import br.com.angeloni.ws.PriceListInfo;
import br.com.angeloni.ws.ResultItemPriceListInfo;
import br.com.angeloni.ws.ResultPriceListInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RemarcacaoService {

  private static final int SYNC_PRICE_BATCH_SIZE = 1000;

  @Autowired
  private AtgService atgService;

  @Autowired
  private ControlePrecoApi controlePrecoApi;

  public void remarcarPrecos(AuditoriaLog auditoriaLog, List<ListaPrecoBean> remarcar) {
    if (remarcar.isEmpty()) {
      auditoriaLog.logError("Nenhum produto para remarcar!");
      return;
    }

    Map<String, List<ListaPrecoBean>> listas =
      remarcar.stream().collect(Collectors.groupingBy(ListaPrecoBean::getCodigo));

    for (Map.Entry<String, List<ListaPrecoBean>> e : listas.entrySet()) {
      PrecosUtil.batches(e.getValue(), SYNC_PRICE_BATCH_SIZE)
        .forEach(chunck -> remarcarPrecoBatch(auditoriaLog, e.getKey(), chunck));
    }
  }

  private void remarcarPrecoBatch(AuditoriaLog auditoriaLog, String codigoLista, List<ListaPrecoBean> listas) {
    String produtos = listas.stream().map(ListaPrecoBean::getPreco)
      .map(p -> p.getCodigo().toString())
      .collect(Collectors.joining(" ,"));

    try {
      // Garantido pela validação que todos os preços para o arquivo possuem a mesma data de início e fim
      LocalDateTime dataInicio = Optional.ofNullable(listas.get(0).getPreco().getDataInicio())
        .orElse(LocalDateTime.now());
      LocalDateTime dataFim = Optional.ofNullable(listas.get(0).getPreco().getDataFim())
        .orElse(LocalDateTime.now());
      // Garantido pelo banco que não existem duplicidade de listas para mais de uma loja
      Long loja = listas.get(0).getPreco().getLoja();

      auditoriaLog.log("Remarcando preços para a lista: " + codigoLista + ", data inicial: "
        + dataInicio.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " e produtos: " + produtos);

      PriceListInfo request = new PriceListInfo();
      List<ListaPrecoBean> listaRetorno = new ArrayList<>();

      request.setId(codigoLista);
      request.setIgnoreFrozen(true);
      request.setUserId(auditoriaLog.getAuditoria().getUsuario());
      request.setProcessingDate(DatatypeFactory.newInstance()
              .newXMLGregorianCalendar(dataInicio.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));

      for (ListaPrecoBean lista : listas) {
        String sku = lista.getPreco().getCodigo().toString();
        BigDecimal preco = lista.getValorFracionado() != null ? lista.getValorFracionado() : lista.getValor();

        ItemPriceListInfo item = new ItemPriceListInfo();
        item.setAction("SET");
        item.setMargin(0d);
        item.setPrice(preco.doubleValue());
        item.setSkuId(sku);
        request.getItems().add(item);

        if (lista.getValorRetorno() != null) listaRetorno.add(lista);
      }

      ResultPriceListInfo result = atgService.syncPriceList(request);
      StringBuilder erros = new StringBuilder();
      if (!result.getItemsNotOK().isEmpty()) {
        for (ResultItemPriceListInfo resultItem : result.getItemsNotOK()) {
          erros.append(ToStringBuilder.reflectionToString(resultItem.getItem(), ToStringStyle.NO_CLASS_NAME_STYLE))
                  .append(" -> ");
          resultItem.getViolations().getEntry().stream().forEach(e ->
                  erros.append(ToStringBuilder.reflectionToString(e, ToStringStyle.NO_CLASS_NAME_STYLE)));
          erros.append(System.getProperty("line.separator"));
        }
        auditoriaLog.logError("Preços não integrados, detalhes: " + erros);
      }
      boolean erro = erros.length() > 0;
      auditoriaLog.log("Integração com ATG id: " + result.getIntegrationId()
              + " concluída com " + (erro ? ("erros: " + erros) : "sucesso!"));

      if (erro) {
        return;
      }

      if (listaRetorno.size() > 0) {
        auditoriaLog.log("Realizando controle do Retorno de Preços no ATG ...");

        request.getItems().clear();
        request.setProcessingDate(DatatypeFactory.newInstance()
                .newXMLGregorianCalendar(dataFim.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));

        for (ListaPrecoBean lista : listaRetorno) {
          String sku = lista.getPreco().getCodigo().toString();

          ItemPriceListInfo item = new ItemPriceListInfo();
          item.setAction("SET");
          item.setMargin(0d);
          item.setPrice(lista.getValorRetorno().doubleValue());
          item.setSkuId(sku);
          request.getItems().add(item);
        }

        atgService.syncPriceList(request);
      }

      //TODO: Processar Retorno de Preços (Listas e SAMI) na API de Controle de Preços (a definir)
      auditoriaLog.log("Realizando controle de preços no ERP ...");
      ControlePreco controlePreco = new ControlePreco();
      controlePreco.setDataInicio(dataInicio.toInstant(ZoneOffset.UTC));
      controlePreco.setDataFim(dataFim.toInstant(ZoneOffset.UTC));
      controlePreco.setIdUnidade(loja.toString());
      List<ControlePrecoItem> listItemControle = new ArrayList<>();
      for (ListaPrecoBean lista : listas) {
        ControlePrecoItem item = new ControlePrecoItem();
        item.setIdProduto(lista.getPreco().getCodigo().toString());
        item.setQuantidadeDisponivel(lista.getPreco().getQtdPromocao());
        item.setRetornarPreco(lista.getPreco().isRetornoSami());
        listItemControle.add(item);
      }
      controlePreco.setItens(listItemControle);

      RetornoControlePreco retornoControlePreco = controlePrecoApi.controlePrecoV01Post(controlePreco)
        .getDsStatus().getRetorno().get(0);
      if (!retornoControlePreco.isSucesso()) {
        auditoriaLog.logError("Problema ao processar o controle de preços no ERP: "
          + retornoControlePreco.getDescricao());
      }
    } catch (Exception ex) {
      auditoriaLog.logError("Não foi possível remarcar os preços para a lista: " + codigoLista
        + " e produtos: " + produtos + ", erro: " + ex.getMessage());
      log.error(ex.getMessage(), ex);
    }
  }

}


/*
      request.setId(codigoLista);
      request.setIgnoreFrozen(true);
      request.setUserId(auditoriaLog.getAuditoria().getUsuario());

      request.setProcessingDate(DatatypeFactory.newInstance()
        .newXMLGregorianCalendar(dataInicio.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));

      for (ListaPrecoBean lista : listas) {
        String sku = lista.getPreco().getCodigo().toString();
        BigDecimal preco = lista.getValorFracionado() != null ? lista.getValorFracionado() : lista.getValor();

        ItemPriceListInfo item = new ItemPriceListInfo();
        item.setAction("SET");
        item.setMargin(0d);
        item.setPrice(preco.doubleValue());
        item.setSkuId(sku);
        request.getItems().add(item);
      }
 */