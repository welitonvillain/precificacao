package br.com.angeloni.precos.server.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CodigoListaRepositoryTest {

  @Autowired
  private CodigoListaRepository codigoListaRepository;

  @Test
  public void testFindLista() {
    CodigoListaEntity lista = codigoListaRepository.findLista("Divvino", "ClubeD", 53L)
      .orElse(null);
    assertThat(lista).isNotNull();
    assertThat(lista.getCodigo()).isEqualTo("plist3200199");
  }

  @Test
  public void testFindListaErro() {
    CodigoListaEntity lista = codigoListaRepository.findLista("Divvino", "INVALID", 53L)
      .orElse(null);
    assertThat(lista).isNull();
  }

  @Test
  public void testFindListasByLojas() {
    List<CodigoListaEntity> listas = codigoListaRepository.findListasByLojas(Arrays.asList(6L, 7L));
    assertThat(listas).hasSize(16);
  }

}
