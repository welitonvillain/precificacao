package br.com.angeloni.precos.server.erp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * RetornoIntegracaoPedido
 */
@Data
public class RetornoControlePreco implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("Sucesso")
  private boolean sucesso;

  @JsonProperty("Descricao")
  private String descricao;

}

