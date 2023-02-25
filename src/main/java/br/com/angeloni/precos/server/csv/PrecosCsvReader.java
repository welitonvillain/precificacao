package br.com.angeloni.precos.server.csv;

import org.springframework.stereotype.Service;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PrecosCsvReader {

  private static final CsvPreference CSV_PREFERENCE =
      new CsvPreference.Builder('"', ';', "\n")
          .surroundingSpacesNeedQuotes(true)
          .build();

  private Map<String, String> getFieldMap(Layout.Template template) {
    Map<String, String> fieldMap = new HashMap<>();
    for (Field field : template.getBeanClass().getDeclaredFields()) {
      CsvBind bind = field.getAnnotation(CsvBind.class);
      if (bind != null) {
        fieldMap.put(bind.column(), field.getName());
        if (bind.aliases() != null && bind.aliases().length > 0) {
          for (String alias : bind.aliases()) {
            fieldMap.put(alias, field.getName());
          }
        }
      }
    }
    return fieldMap;
  }

  private String[] parseHeader(Layout.Template template, String[] header) throws PrecosCsvReaderException {
    Map<String, String> fieldMap = getFieldMap(template);
    String[] cols = new String[header.length];
    for (int i = 0; i < header.length; i++) {
      if (header[i] == null) continue;
      String col = header[i].trim();
      cols[i] = fieldMap.get(col);
      if (cols[i] == null) {
        throw new PrecosCsvReaderException("Erro: Coluna \"" + col + "\" inválida para o template \"" + template + "\"");
      }
    }
    return cols;
  }

  public <T> List<T> processa(InputStream csv, Layout.Template template) throws IOException {
    String[] header = null;
    List<T> list = new ArrayList<>();
    try (Reader csvReader = new InputStreamReader(csv, StandardCharsets.ISO_8859_1);
         ICsvBeanReader beanReader = new CsvBeanReader(csvReader, CSV_PREFERENCE)) {
      header = beanReader.getHeader(true);
      String[] parsedHeader = parseHeader(template, header);
      T bean;
      while ((bean = beanReader.read(template.getBeanClass(), parsedHeader, template.getProcessors())) != null) {
        System.out.println(">>> Bean: " + bean);
        list.add(bean);
      }
    } catch (SuperCsvException ex) {
      String message = "Erro: " + ex.getMessage();
      if (ex.getCsvContext() != null) {
        Integer columnNumber = ex.getCsvContext().getColumnNumber();
        Integer lineNumber = ex.getCsvContext().getLineNumber();
        String columnName = header != null ? header[columnNumber - 1].trim() : "" + columnNumber;
        message = "Erro ao processar a coluna \"" + columnName + "\" na linha " + lineNumber + "."
            + "\nProblema: " + ex.getMessage()
            + "\nDados: " + ex.getCsvContext().getRowSource();
      }
      throw new PrecosCsvReaderException(message, ex);
    }
    return list;
  }

  public Layout.Template detectaLayout(InputStream csv) throws IOException {
    try (Reader csvReader = new InputStreamReader(csv, StandardCharsets.ISO_8859_1);
         ICsvListReader listReader = new CsvListReader(csvReader, CSV_PREFERENCE)) {
      String[] header = listReader.getHeader(true);
      if (header == null || header.length < 1 || !header[0].trim().equalsIgnoreCase("TIPO PROMOCAO")) {
        throw new PrecosCsvReaderException("Não foi possível identificar o layout do arquivo!");
      }
      List<String> row = listReader.read();
      if (row != null && row.size() > 0) {
        String tipoPromo = row.get(0).trim().toUpperCase();
        try {
          return TipoPromocao.valueOf(tipoPromo).getTemplate();
        } catch (Exception ex) {
          throw new PrecosCsvReaderException("Não foi possível identificar o layout para o tipo de promoção \""+tipoPromo+"\"!");
        }
      }
      throw new PrecosCsvReaderException("Não foi possível identificar o layout do arquivo!");
    }
  }

}
