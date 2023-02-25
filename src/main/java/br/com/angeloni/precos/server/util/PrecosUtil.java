package br.com.angeloni.precos.server.util;

import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@UtilityClass
public class PrecosUtil {

  public static final long LOJA_ELETRO = 51;
  public static final long LOJA_DIVVINO = 53;

  public static <T> Stream<List<T>> batches(List<T> source, int length) {
    if (length <= 0) throw new IllegalArgumentException("length = " + length);
    int size = source.size();
    if (size <= 0) return Stream.empty();
    int fullChunks = (size - 1) / length;
    return IntStream.range(0, fullChunks + 1)
        .mapToObj(n -> source.subList(n * length, n == fullChunks ? size : (n + 1) * length));
  }

  public static String gerarPromocaoId() {
    return "P" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
  }

  public static List<String> gerarSitesPromocao(List<Long> lojas) {
    Set<String> sites = new HashSet<>();
    if (lojas == null) {
      return Collections.emptyList();
    }
    for (Long loja : lojas) {
      String prefix = "super";
      if (loja == LOJA_ELETRO) {
        prefix = "eletro";
      } else if (loja == LOJA_DIVVINO) {
        prefix = "liquor";
      }
      sites.add(prefix);
      sites.add(prefix + "Mobile");
    }
    return new ArrayList<>(sites);
  }

}
