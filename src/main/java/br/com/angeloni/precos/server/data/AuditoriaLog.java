package br.com.angeloni.precos.server.data;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;

@Slf4j
public class AuditoriaLog {

  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss").toFormatter();

  @Setter
  @Getter
  private boolean timestamp = true;

  @Getter
  private boolean success = true;

  @Getter
  private final AuditoriaEntity auditoria;

  private StringBuilder msg = new StringBuilder();

  public AuditoriaLog(final AuditoriaEntity auditoria) {
    Objects.requireNonNull(auditoria);
    this.auditoria = auditoria;
  }

  public void log(String message) {
    if (timestamp) {
      msg.append(LocalDateTime.now().format(DATE_TIME_FORMATTER)).append(": ");
    }
    msg.append(message).append(System.getProperty("line.separator"));
    log.debug(message);
  }

  public void logError(String message) {
    success = false;
    log("ERRO! " + message);
  }

  public String getLog() {
    return msg.toString();
  }

}
