package br.com.angeloni.precos.server.csv;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ListaPromocaoBean {

  private String codigo;
  private String nome;
  private BigDecimal valorLista;
  private List<NivelPromocaoBean> valorNiveis = new ArrayList<>();

  public ListaPromocaoBean(String codigo, String nome) {
    this.codigo = codigo;
    this.nome = nome;
  }

}
