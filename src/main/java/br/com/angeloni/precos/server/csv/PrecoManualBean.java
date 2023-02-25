package br.com.angeloni.precos.server.csv;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrecoManualBean {

  @CsvBind(column = "Tipo Promocao")
  private TipoPromocao tipoPromocao;
  @CsvBind(column = "Loja")
  private Long loja;
  @CsvBind(column = "Cod.")
  private Long codigo;
  @CsvBind(column = "Data Inicio")
  private LocalDateTime dataInicio;
  @CsvBind(column = "Data Fim")
  private LocalDateTime dataFim;

  //Eletro
  @CodigoLista("Preco DE:")
  @CsvBind(column = "Preco DE:")
  private BigDecimal precoDe;
  @CodigoLista("Preco POR")
  @CsvBind(column = "Preco POR")
  private BigDecimal precoPor;
  @CodigoLista(value = "Preco DE:", retorno = true)
  @CsvBind(column = "Retorno DE")
  private BigDecimal retornoDe;
  @CodigoLista(value = "Preco POR", retorno = true)
  @CsvBind(column = "Retorno POR")
  private BigDecimal retornoPor;
  @CodigoLista("Zoom DE")
  @CsvBind(column = "Zoom DE")
  private BigDecimal zoomDe;
  @CodigoLista("Zoom POR")
  @CsvBind(column = "Zoom POR")
  private BigDecimal zoomPor;
  @CodigoLista(value = "Zoom DE", retorno = true)
  @CsvBind(column = "Retorno Zoom DE")
  private BigDecimal retornoZoomDe;
  @CodigoLista(value = "Zoom POR", retorno = true)
  @CsvBind(column = "Retorno Zoom POR")
  private BigDecimal retornoZooomPor;
  @CodigoLista("Buscape DE")
  @CsvBind(column = "Buscape DE")
  private BigDecimal buscapeDe;
  @CodigoLista("Buscape POR")
  @CsvBind(column = "Buscape POR")
  private BigDecimal buscapePor;
  @CodigoLista(value = "Buscape DE", retorno = true)
  @CsvBind(column = "Retorno Buscape DE")
  private BigDecimal retornoBuscapeDe;
  @CodigoLista(value = "Buscape POR", retorno = true)
  @CsvBind(column = "Retorno Buscape POR")
  private BigDecimal retornoBuscapePor;
  @CodigoLista("G. Shopping DE")
  @CsvBind(column = "G. Shopping DE")
  private BigDecimal shoppingDe;
  @CodigoLista("G. Shopping POR")
  @CsvBind(column = "G. Shopping POR")
  private BigDecimal shoppingPor;
  @CodigoLista(value = "G. Shopping DE", retorno = true)
  @CsvBind(column = "Retorno G. Shopping DE")
  private BigDecimal retornoShoppingDe;
  @CodigoLista(value = "G. Shopping POR", retorno = true)
  @CsvBind(column = "Retorno G. Shopping POR")
  private BigDecimal retornoShoppingPor;
  @CodigoLista("Email Mkt DE")
  @CsvBind(column = "Email Mkt DE")
  private BigDecimal emailMktDe;
  @CodigoLista("Email Mkt POR")
  @CsvBind(column = "Email Mkt POR")
  private BigDecimal emailMktPor;
  @CodigoLista(value = "Email Mkt DE", retorno = true)
  @CsvBind(column = "Retorno Email Mkt DE")
  private BigDecimal retornoEmailMktDe;
  @CodigoLista(value = "Email Mkt POR", retorno = true)
  @CsvBind(column = "Retorno Email Mkt POR")
  private BigDecimal retornoEmailMktPor;

  //Super
  @CodigoLista("Func.")
  @CsvBind(column = "Func.")
  private BigDecimal func;
  @CodigoLista("Sr. Desc.")
  @CsvBind(column = "Sr. Desc.")
  private BigDecimal srDesc;
  @CodigoLista("Bradescard")
  @CsvBind(column = "Bradescard")
  private BigDecimal bradescard;
  @CodigoLista("Func. Bradescard")
  @CsvBind(column = "Func. Bradescard")
  private BigDecimal funcBradescard;
  @CodigoLista("Sr. Desc. + Brad.")
  @CsvBind(column = "Sr. Desc. + Brad.")
  private BigDecimal srDescBrad;
  @CodigoLista("Email")
  @CsvBind(column = "Email")
  private BigDecimal email;

  //Divvino
  @CodigoLista("ClubeD")
  @CsvBind(column = "ClubeD")
  private BigDecimal clubeD;
  @CodigoLista(value = "ClubeD", retorno = true)
  @CsvBind(column = "Retorno ClubeD")
  private BigDecimal retornoClubeD;

  @CsvBind(column = "Qtd Promocao")
  private BigDecimal qtdPromocao;

  @CsvBind(column = "Retorno SAMI")
  private boolean retornoSami;

  @CsvBind(column = "Sobrescrever")
  private boolean sobreescrever;
}
