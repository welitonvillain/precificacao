package br.com.angeloni.precos.server.csv;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

@UtilityClass
@Slf4j
public class CodigoListaMapper {

  public static Collection<ListaPrecoBean> getListas(PrecoManualBean preco) {
    Map<String, ListaPrecoBean> map = new HashMap<>();
    for (Field field : PrecoManualBean.class.getDeclaredFields()) {
      CodigoLista bind = field.getAnnotation(CodigoLista.class);
      if (bind != null) {
        try {
          String nome = bind.value();
          ListaPrecoBean lista = map.getOrDefault(nome, new ListaPrecoBean(preco, nome));
          field.setAccessible(true);
          BigDecimal valor = (BigDecimal) field.get(preco);
          if (!bind.retorno()) {
            lista.setValor(valor);
          } else {
            lista.setValorRetorno(valor);
          }
          map.put(nome, lista);
        } catch (IllegalAccessException ex) {
          log.error(ex.getMessage(), ex);
        }
      }
    }
    return map.values();
  }
}
