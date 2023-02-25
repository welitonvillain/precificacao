package br.com.angeloni.precos.server.service;

import br.com.angeloni.precos.server.csv.*;
import br.com.angeloni.precos.server.data.AuditoriaLog;
import br.com.angeloni.precos.server.data.CodigoListaEntity;
import br.com.angeloni.precos.server.data.CodigoListaRepository;
import br.com.angeloni.ws.PriceInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ValidacaoService {

  private static final double MARGEM = 3.0d; //3%

  @Autowired
  private CodigoListaRepository listaRepository;

  @Autowired
  private AtgService atgService;

  public void validaRegrasRemarcacao(AuditoriaLog auditoriaLog, Layout.Template template, List<PrecoManualBean> precos,
                                     List<ListaPrecoBean> remarcar) {
    auditoriaLog.log("Validando regras ...");

    LocalDateTime dataInicial = precos.get(0).getDataInicio();
    LocalDateTime dataFinal = precos.get(0).getDataFim();

    for (PrecoManualBean preco : precos) {
      if ((preco.getDataInicio() != null && dataInicial != null && !preco.getDataInicio().isEqual(dataInicial))
        || (preco.getDataInicio() == null && dataInicial != null)
        || (preco.getDataInicio() != null && dataInicial == null)) {
        auditoriaLog.logError("A data inicial de todos os preços do arquivo deve ser a mesma.");
        break;
      }

      if ((preco.getDataFim() != null && dataFinal != null && !preco.getDataFim().isEqual(dataFinal))
        || (preco.getDataFim() == null && dataFinal != null)
        || (preco.getDataFim() != null && dataFinal == null)) {
        auditoriaLog.logError("A data final de todos os preços do arquivo deve ser a mesma.");
        break;
      }

      Collection<ListaPrecoBean> listas = CodigoListaMapper.getListas(preco);

      for (ListaPrecoBean lista : listas) {
        if (lista.getValor() == null || lista.getValor().compareTo(BigDecimal.ZERO) < 1) {
          continue;
        }

        String codigoLista = listaRepository.findLista(template.toString(), lista.getNome(), preco.getLoja())
          .map(CodigoListaEntity::getCodigo).orElse(null);
        if (codigoLista == null) {
          auditoriaLog.logError("Não foi possível localizar o código para a lista: "
            + lista.getNome() + ", loja: " + preco.getLoja() + " e tipo: " + template.toString());
          continue;
        } else {
          lista.setCodigo(codigoLista);
        }

        try {
          PriceInfo priceInfo = atgService.getPrice(preco.getCodigo(), codigoLista);

          if (!preco.isSobreescrever()) {
            boolean erro = validaRegrasProduto(auditoriaLog, lista.getPreco().getCodigo(), lista.getValor(), codigoLista, priceInfo);
            if (erro) {
              continue;
            }
          }

          boolean precoFracionado = priceInfo.getType().equalsIgnoreCase("product-loose");
          if (precoFracionado) {
            // lista.setValorFracionado(lista.getValor().divide(BigDecimal.valueOf(1000)));
            lista.setValorFracionado(lista.getValor());
          }

        } catch (Exception ex) {
          auditoriaLog.logError("Não foi possível consultar o preço para o produto: " + preco.getCodigo()
            + " e lista: " + codigoLista + ", erro: " + ex.getMessage());
          log.error(ex.getMessage(), ex);
          continue;
        }

        remarcar.add(lista);
      }
    }
  }

  private Optional<AtivarPromocaoBean> findPromocao(List<AtivarPromocaoBean> ativarPromocoes, PromocaoManualBean promo) {
    return ativarPromocoes.stream()
      .filter(p -> p.getPromocao().getProduto().equals(promo.getProduto()) && p.getPromocao().getTipoPromocao() == promo.getTipoPromocao())
      .findFirst();
  }

  public void validaRegrasPromocao(AuditoriaLog auditoriaLog, List<PromocaoManualBean> promocoes, List<AtivarPromocaoBean> ativarPromocoes) {
    auditoriaLog.log("Validando regras ...");
    for (PromocaoManualBean promo : promocoes) {
      switch (promo.getTipoPromocao()) {
        case PV:
          if (promo.getProdutosDoados().isEmpty() || promo.getValorDoado() == null) {
            auditoriaLog.logError("Pack Virtual deve ter o código e o valor dos produtos doados para o produto: "
              + promo.getProduto() + " e lojas: " + StringUtils.join(promo.getLojas(), " ,"));
            continue;
          }
          break;
        case L:
          if (promo.getQuantidadePagar() == null || promo.getQuantidadeDoado() == null) {
            auditoriaLog.logError("Leve e Pague deve ter a quantidade a pagar e doada para o produto: "
              + promo.getProduto() + " e lojas: " + StringUtils.join(promo.getLojas(), " ,"));
            continue;
          }
          break;
        case B:
          if (promo.getProdutosDoados().isEmpty() || promo.getQuantidadeDoado() == null) {
            auditoriaLog.logError("Brinde deve ter o código e a quantidade dos produtos doados para o produto: "
              + promo.getProduto() + " e lojas: " + StringUtils.join(promo.getLojas(), " ,"));
            continue;
          }
          break;
        case U:
          if (promo.getPercentualDesconto() == null) {
            auditoriaLog.logError("Desconto Último Item ter o percentual de desconto para o produto: "
              + promo.getProduto() + " e lojas: " + StringUtils.join(promo.getLojas(), " ,"));
            continue;
          }
          break;
        case P:
          if (promo.getValorDoado() == null || promo.getQuantidadePagar() == null) {
            auditoriaLog.logError("Patamar ter o valor dos produtos doados e a quantidade a pagar para o produto: "
              + promo.getProduto() + " e lojas: " + StringUtils.join(promo.getLojas(), " ,"));
            continue;
          }
          break;
        case PN:
          if (promo.getValorPorcentagem() == null) {
            auditoriaLog.logError("Patamar em Nível ter o valor ou porcentagem dos produtos doados para o produto: "
              + promo.getProduto() + " e lojas: " + StringUtils.join(promo.getLojas(), " ,"));
            continue;
          }
          break;
        default:
          auditoriaLog.logError("Tipo de promoção inválido! (" + promo.getTipoPromocao() + ") para o produto: " + promo.getProduto());
          continue;
      }

      Optional<AtivarPromocaoBean> primeiroNivel = findPromocao(ativarPromocoes, promo);

      AtivarPromocaoBean ativar = new AtivarPromocaoBean(promo);
      if (!primeiroNivel.isPresent()) {
        ativarPromocoes.add(ativar);
      }

      // Busca todas as listas para as lojas da promoção que não são listas (DE)
      List<CodigoListaEntity> listas = listaRepository.findListasByLojas(promo.getLojas()).stream()
        .filter(l -> !l.getNome().endsWith(" DE") && !l.getNome().endsWith(" DE:"))
        .collect(Collectors.toList());
      if (listas.isEmpty()) {
        auditoriaLog.logError("Não foram encontradas listas para o produto: "
          + promo.getProduto() + " e lojas: " + StringUtils.join(promo.getLojas(), " ,"));
        continue;
      }
      ativar.setCodigosLista(listas
        .stream().map(l -> new ListaPromocaoBean(l.getCodigo(), l.getNome())).collect(Collectors.toList()));

      // Valida os preços para cada código de lista
      for (ListaPromocaoBean lista : ativar.getCodigosLista()) {

        try {

          // TESTE
//          PriceInfo priceInfo = new PriceInfo();
//
//          if (lista.getCodigo().equalsIgnoreCase("plist3630002")) {
//            priceInfo.setPrice(0.50);
//          } else {
//            priceInfo = atgService.getPrice(promo.getProduto(), lista.getCodigo());
//          }
          // FIM

          PriceInfo priceInfo = atgService.getPrice(promo.getProduto(), lista.getCodigo());

          lista.setValorLista(BigDecimal.valueOf(priceInfo.getPrice()));

          BigDecimal valor = promo.getValorDoado();
          boolean ignoraValidacaoValor = false;

          if (promo.getTipoPromocao() == TipoPromocao.PN) {
            if (promo.getPatamar() == PromocaoManualBean.Patamar.PF) {
              valor = promo.getValorPorcentagem();
            } else if (promo.getPatamar() == PromocaoManualBean.Patamar.PP) {
              BigDecimal desconto = (BigDecimal.valueOf(priceInfo.getPrice())
                .multiply(promo.getValorPorcentagem())
                .divide(BigDecimal.valueOf(100)));
              valor = BigDecimal.valueOf(priceInfo.getPrice()).subtract(desconto);
            }
            if (primeiroNivel.isPresent()) {
              ListaPromocaoBean listaPrimeiroNivel = primeiroNivel.get().getCodigosLista()
                .stream()
                .filter(c -> c.getCodigo().equals(lista.getCodigo())).findFirst()
                .orElseThrow(IllegalStateException::new);
              listaPrimeiroNivel.getValorNiveis().add(new NivelPromocaoBean(promo.getNivel(), valor));
            } else {
              lista.getValorNiveis().add(new NivelPromocaoBean(promo.getNivel(), valor));
            }
          } else {
            if (valor == null) {
              ignoraValidacaoValor = true;
              valor = lista.getValorLista();
            }
            ativar.setValor(valor);
          }

          if (!promo.isSobreescrever()) {
            boolean erro = validaRegrasProduto(auditoriaLog, promo.getProduto(), ignoraValidacaoValor ? null : valor, lista.getCodigo(), priceInfo);
            if (erro) {
              continue;
            }
          }
        } catch (Exception ex) {
          auditoriaLog.logError("Não foi possível consultar o preço para o produto: " + promo.getProduto()
            + " e lista: " + lista.getCodigo() + ", erro: " + ex.getMessage());
          log.error(ex.getMessage(), ex);
        }
      }
    }
  }

  private boolean validaRegrasProduto(AuditoriaLog auditoriaLog, Long skuId, BigDecimal valor, String codigoLista, PriceInfo priceInfo) {
    if (priceInfo.isComplexCampaign()) {
      auditoriaLog.logError("Produto: " + skuId + " está em campanha complexa para a lista: "
        + codigoLista + " e não será importado.");
      return true;
    }

    if (priceInfo.isFreezing()) {
      auditoriaLog.logError("Produto: " + skuId + " está congelado para a lista: "
        + codigoLista + " e não será importado.");
      return true;
    }

    if (valor != null) {
      if (BigDecimal.valueOf(priceInfo.getPrice()).compareTo(valor) == 0) {
        auditoriaLog.logError("Produto: " + skuId + " está com o mesmo preço para a lista: "
          + codigoLista + " e não será importado.");
        return true;
      }

      BigDecimal margem = (BigDecimal.valueOf(priceInfo.getCmv())
        .multiply(BigDecimal.valueOf(MARGEM))
        .divide(BigDecimal.valueOf(100)));
      BigDecimal precoMinimo = BigDecimal.valueOf(priceInfo.getCmv()).add(margem);

      if (valor.compareTo(precoMinimo) < 0) {
        auditoriaLog.logError("Produto: " + skuId + " está com o preço menor que a margem para a lista: "
          + codigoLista + " e não será importado.");
        return true;
      }
    }

    return false;
  }

}
