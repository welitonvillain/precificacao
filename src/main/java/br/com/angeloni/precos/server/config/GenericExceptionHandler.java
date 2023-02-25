package br.com.angeloni.precos.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GenericExceptionHandler {

  @ExceptionHandler(Throwable.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String exception(final Throwable throwable, final Model model) {
    log.error("Erro inesperado", throwable);
    String errorMessage = (throwable != null ? throwable.getMessage() : "Erro inesperado");
    model.addAttribute("errorMessage", errorMessage);
    return "error";
  }

}
