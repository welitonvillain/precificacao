package br.com.angeloni.precos.server.csv;

import lombok.experimental.UtilityClass;
import org.supercsv.cellprocessor.*;
import org.supercsv.cellprocessor.constraint.Equals;
import org.supercsv.cellprocessor.constraint.IsElementOf;
import org.supercsv.cellprocessor.constraint.StrRegEx;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.time.ParseLocalDateTime;

import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Locale;

@UtilityClass
public class Layout {

  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy['T'HH:mm:ssz]")
          .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
          .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
          .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
          .parseDefaulting(ChronoField.NANO_OF_SECOND, 0)
          .parseDefaulting(ChronoField.OFFSET_SECONDS, 0)
          .toFormatter();

  private static final CellProcessor TIPO = new ParseEnum(TipoPromocao.class);
  private static final CellProcessor CODIGO = new ParseLong();
  private static final CellProcessor DATA = new ParseLocalDateTime(DATE_TIME_FORMATTER);
  private static final CellProcessor DATA_NULL = new Optional(DATA);
  private static final CellProcessor MONEY = new StrReplace("R\\r\\$", "",
      new ParseBigDecimal(DecimalFormatSymbols.getInstance(new Locale ("pt", "BR"))));
  private static final CellProcessor MONEY_NULL = new Optional(MONEY);
  private static final CellProcessor DECIMAL = new ParseBigDecimal(
      DecimalFormatSymbols.getInstance(new Locale ("pt", "BR")));
  private static final CellProcessor DECIMAL_NULL = new Optional(DECIMAL);
  private static final CellProcessor BOOL = new Truncate(1,
      new ParseBool("S", "N", true));
  private static final CellProcessor BOOL_NULL = new Optional(BOOL);
  private static final CellProcessor PATAMAR = new ParseEnum(PromocaoManualBean.Patamar.class);
  private static final CellProcessor LISTA = new ParseList(",", new ParseLong());

  public enum Template {
    Eletro(PrecoManualBean.class, new Equals("RE", TIPO), CODIGO, CODIGO, DATA_NULL, DATA_NULL,
        MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL,
        MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL,
        MONEY_NULL, MONEY_NULL, BOOL_NULL, DECIMAL, BOOL),
    Super(PrecoManualBean.class, new Equals("RS", TIPO), CODIGO, CODIGO, DATA, DATA, MONEY,
        MONEY, MONEY, MONEY, MONEY, MONEY, MONEY, MONEY, BOOL, DECIMAL_NULL, BOOL),
    Divvino(PrecoManualBean.class, new Equals("RD", TIPO), CODIGO, CODIGO, DATA_NULL, DATA_NULL,
        MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL,
        MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL,
        MONEY_NULL, MONEY_NULL, MONEY_NULL, MONEY_NULL, BOOL_NULL, DECIMAL, BOOL),
    Promocao(PromocaoManualBean.class, new IsElementOf(Arrays.asList("L", "U", "PV", "B", "P"), TIPO),
        DATA, DATA, CODIGO, DECIMAL, LISTA, DECIMAL_NULL, MONEY_NULL, DECIMAL_NULL, LISTA, BOOL, BOOL),
    PromocaoN(PromocaoManualBean.class, new Equals("PN", TIPO), DATA, DATA, CODIGO, LISTA, PATAMAR,
        LISTA, BOOL, CODIGO, MONEY);

    private final Class<?> beanClass;
    private final CellProcessor[] processors;

    Template(Class<?> beanClass, CellProcessor... processors) {
      this.beanClass = beanClass;
      this.processors = processors;
    }

    public CellProcessor[] getProcessors() {
      return this.processors;
    }

    public <T> Class<T> getBeanClass() {
      return (Class<T>) this.beanClass;
    }
  }
}
