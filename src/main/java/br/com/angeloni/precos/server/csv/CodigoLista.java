package br.com.angeloni.precos.server.csv;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CodigoLista {

  String value();

  boolean retorno() default false;

}
