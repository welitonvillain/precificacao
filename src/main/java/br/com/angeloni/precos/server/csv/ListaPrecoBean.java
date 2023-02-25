package br.com.angeloni.precos.server.csv;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ListaPrecoBean {

  private final PrecoManualBean preco;
  private final String nome;

  private String codigo;
  private BigDecimal valor;
  private BigDecimal valorRetorno;
  private BigDecimal valorFracionado;

  public ListaPrecoBean(PrecoManualBean preco, String nome) {
    this.preco = preco;
    this.nome = nome;
  }

}
