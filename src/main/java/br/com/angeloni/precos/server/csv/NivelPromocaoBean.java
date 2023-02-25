package br.com.angeloni.precos.server.csv;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class NivelPromocaoBean {
  private Long nivel;
  private BigDecimal valor;
}
