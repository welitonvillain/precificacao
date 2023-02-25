package br.com.angeloni.precos.server.csv;

import static br.com.angeloni.precos.server.csv.Layout.Template.*;

public enum TipoPromocao {
  RS(Super), //sites: super e supermobile
  RE(Eletro), //loja 51 = eletro
  RD(Divvino), //loja 53 = liquor
  L(Promocao), //leve e pague
  U(Promocao), //ultimo item
  PV(Promocao), //pack virtual
  B(Promocao), //brinde
  P(Promocao), //simples
  PN(PromocaoN); //patamar

  private Layout.Template template;

  TipoPromocao(Layout.Template tipo) {
    this.template = tipo;
  }

  public Layout.Template getTemplate() {
    return template;
  }
}
