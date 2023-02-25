package br.com.angeloni.precos.server.csv;

import java.io.IOException;

public class PrecosCsvReaderException extends IOException {

  public PrecosCsvReaderException(String message, Throwable cause) {
    super(message, cause);
  }

  public PrecosCsvReaderException(String message) {
    super(message);
  }

}
