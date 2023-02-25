package br.com.angeloni.precos.server.csv;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromocaoManualBean {

  @CsvBind(column = "Tipo promocao")
  private TipoPromocao tipoPromocao;
  @CsvBind(column = "Data Inicio")
  private LocalDateTime dataInicio;
  @CsvBind(column = "Data Fim")
  private LocalDateTime dataFim;
  @CsvBind(column = "Produto (cod)")
  private Long produto;
  @CsvBind(column = "Quantidade a pagar")
  private BigDecimal quantidadePagar;
  @Builder.Default
  @CsvBind(column = "Produtos Doados (cod)")
  private List<Long> produtosDoados = new ArrayList<>();
  @CsvBind(column = "Quantidade do produto doado")
  private BigDecimal quantidadeDoado;
  @CsvBind(column = "Valor produto doado")
  private BigDecimal valorDoado;
  @CsvBind(column = "Percentual Desc.")
  private BigDecimal percentualDesconto;
  @Builder.Default
  @CsvBind(column = "Lojas")
  private List<Long> lojas = new ArrayList<>();
  @CsvBind(column = "Retorno SAMI")
  private Boolean retornoSAMI;

  //PN
  @CsvBind(column = "Patamar")
  private Patamar patamar;
  @CsvBind(column = "Sobrescrever")
  private boolean sobreescrever;
  @CsvBind(column = "Nivel")
  private Long nivel;
  @CsvBind(column = "Valor/Porcentagem")
  private BigDecimal valorPorcentagem;

  public enum Patamar {
    PF, PP
  }

}
