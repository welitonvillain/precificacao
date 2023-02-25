package br.com.angeloni.precos.server.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class PrecosUtilTest {

  @Test
  public void batches() {
    List<List<Integer>> batches = PrecosUtil.batches(Arrays.asList(1, 2, 3, 4, 5), 2).collect(Collectors.toList());
    assertThat(batches).hasSize(3);
    assertThat(batches.get(0)).hasSize(2).isSubsetOf(1, 2);
    assertThat(batches.get(1)).hasSize(2).isSubsetOf(3, 4);
    assertThat(batches.get(2)).hasSize(1).isSubsetOf(5);
  }

  @Test
  public void gerarPromocaoId() {
    assertThat(PrecosUtil.gerarPromocaoId()).isUpperCase().matches("P\\p{XDigit}{32}");
  }

  @Test
  public void gerarSitesPromocaoNull() {
    assertThat(PrecosUtil.gerarSitesPromocao(null)).isEmpty();
  }

  @Test
  public void gerarSitesPromocaoEmpty() {
    assertThat(PrecosUtil.gerarSitesPromocao(Collections.emptyList())).isEmpty();
  }

  @Test
  public void gerarSitesPromocaoSuper() {
    assertThat(PrecosUtil.gerarSitesPromocao(Arrays.asList(1L, 2L, 7L))).hasSize(2).isSubsetOf("super", "superMobile");
  }

  @Test
  public void gerarSitesPromocaoEletro() {
    assertThat(PrecosUtil.gerarSitesPromocao(Arrays.asList(51L))).hasSize(2).isSubsetOf("eletro", "eletroMobile");
  }

  @Test
  public void gerarSitesPromocaoDivvino() {
    assertThat(PrecosUtil.gerarSitesPromocao(Arrays.asList(53L))).hasSize(2).isSubsetOf("liquor", "liquorMobile");
  }

  @Test
  public void gerarSitesPromocaoTodos() {
    assertThat(PrecosUtil.gerarSitesPromocao(Arrays.asList(1L, 2L, 7L, 51L, 53L)))
      .hasSize(6).isSubsetOf("super", "superMobile", "eletro", "eletroMobile", "liquor", "liquorMobile");
  }
}