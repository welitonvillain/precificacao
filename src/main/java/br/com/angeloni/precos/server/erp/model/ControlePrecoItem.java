package br.com.angeloni.precos.server.erp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ControlePrecoItem
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControlePrecoItem implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("idProduto")
  private String idProduto;

  @JsonProperty("quantidadeDisponivel")
  private BigDecimal quantidadeDisponivel;

  @JsonProperty("retornarPreco")
  private Boolean retornarPreco;

}

