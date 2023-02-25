package br.com.angeloni.precos.server.csv;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class AtivarPromocaoBean {

  private final PromocaoManualBean promocao;

  @Builder.Default
  private List<ListaPromocaoBean> codigosLista = new ArrayList<>();

  private BigDecimal valor;

  public AtivarPromocaoBean(PromocaoManualBean promocao) {
    this.promocao = promocao;
  }

}
