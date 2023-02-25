package br.com.angeloni.precos.server.erp.model;

import br.com.angeloni.precos.server.config.JSONDateSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ControlePreco
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControlePreco implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("idUnidade")
  private String idUnidade;

  @JsonProperty("dataInicio")
  private Instant dataInicio;

  @JsonProperty("dataFim")
  private Instant dataFim;

  @JsonProperty("ttPrecificacaoItem")
  @Singular("item")
  private List<ControlePrecoItem> itens;

}
