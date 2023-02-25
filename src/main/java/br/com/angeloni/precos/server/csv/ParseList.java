package br.com.angeloni.precos.server.csv;

import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.*;
import org.supercsv.util.CsvContext;

import java.util.ArrayList;
import java.util.List;


public class ParseList extends CellProcessorAdaptor implements StringCellProcessor {

  private final String separator;
  private final CellProcessor valueProcessor;

  public ParseList(final String separator, final CellProcessor valueProcessor) {
    super();
    checkPreconditions(separator, valueProcessor);
    this.separator = separator;
    this.valueProcessor = valueProcessor;
  }

  private static void checkPreconditions(final String separator, final CellProcessor valueProcessor) {
    if (separator == null) {
      throw new NullPointerException("separator should not be null");
    } else if (separator.length() == 0) {
      throw new IllegalArgumentException("separator should not be empty");
    }

    if (valueProcessor == null) {
      throw new NullPointerException("valueProcessor should not be null");
    }
  }

  public Object execute(final Object value, final CsvContext context) {
    validateInputNotNull(value, context);
    String[] result = value.toString().split(separator);
    List<Object> list = new ArrayList<>();
    for (String s : result) {
      list.add(valueProcessor.execute(s, context));
    }
    return next.execute(list, context);
  }

}
