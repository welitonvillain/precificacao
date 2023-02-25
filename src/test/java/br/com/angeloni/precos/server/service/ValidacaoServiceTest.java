package br.com.angeloni.precos.server.service;

import br.com.angeloni.precos.server.csv.*;
import br.com.angeloni.precos.server.data.AuditoriaEntity;
import br.com.angeloni.precos.server.data.AuditoriaLog;
import br.com.angeloni.precos.server.data.CodigoListaEntity;
import br.com.angeloni.precos.server.util.PrecosUtil;
import br.com.angeloni.ws.PriceInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ValidacaoServiceTest {

  @Autowired
  private ValidacaoService validacaoService;

  @MockBean
  private AtgService atgService;

  @Test
  public void validaRegrasRemarcacaoSuperSobreescrever() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(true);
    priceInfo.setFreezing(true);
    priceInfo.setCmv(10d);
    priceInfo.setPrice(10d);
    priceInfo.setSkuId("123");
    priceInfo.setType("product");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PrecoManualBean> precos = new ArrayList<>();
    PrecoManualBean preco = PrecoManualBean.builder()
      .tipoPromocao(TipoPromocao.RS)
      .loja(7L)
      .codigo(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .email(BigDecimal.TEN)
      .sobreescrever(true)
      .build();
    precos.addAll(Arrays.asList(preco));

    List<ListaPrecoBean> remarcar = new ArrayList<>();
    validacaoService.validaRegrasRemarcacao(auditoriaLog, Layout.Template.Super, precos, remarcar);

    assertThat(remarcar).hasSize(1);

    ListaPrecoBean listaEmail = remarcar.get(0);
    assertThat(listaEmail.getNome()).isEqualTo("Email");
    assertThat(listaEmail.getCodigo()).isEqualTo("plist3630002");
    assertThat(listaEmail.getValor()).isEqualTo(BigDecimal.TEN);
    assertThat(listaEmail.getValorRetorno()).isNull();
    assertThat(listaEmail.getPreco()).isSameAs(preco);
    assertThat(listaEmail.getValorFracionado()).isNull();

    assertThat(auditoriaLog.isSuccess()).isTrue();
  }

  @Test
  public void validaRegrasRemarcacaoErroMargem() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(10d);
    priceInfo.setPrice(20d);
    priceInfo.setSkuId("123");
    priceInfo.setType("product");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PrecoManualBean> precos = new ArrayList<>();
    PrecoManualBean preco = PrecoManualBean.builder()
      .tipoPromocao(TipoPromocao.RS)
      .loja(7L)
      .codigo(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .func(BigDecimal.TEN)
      .sobreescrever(false)
      .build();
    precos.addAll(Arrays.asList(preco));

    List<ListaPrecoBean> remarcar = new ArrayList<>();
    validacaoService.validaRegrasRemarcacao(auditoriaLog, Layout.Template.Super, precos, remarcar);

    assertThat(auditoriaLog.isSuccess()).isFalse();
    assertThat(auditoriaLog.getLog())
      .contains("está com o preço menor que a margem")
      .hasLineCount(2);
  }

  @Test
  public void validaRegrasRemarcacaoErroPrecoIgual() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(10d);
    priceInfo.setSkuId("123");
    priceInfo.setType("product");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PrecoManualBean> precos = new ArrayList<>();
    PrecoManualBean preco = PrecoManualBean.builder()
      .tipoPromocao(TipoPromocao.RS)
      .loja(7L)
      .codigo(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .func(BigDecimal.TEN)
      .sobreescrever(false)
      .build();
    precos.addAll(Arrays.asList(preco));

    List<ListaPrecoBean> remarcar = new ArrayList<>();
    validacaoService.validaRegrasRemarcacao(auditoriaLog, Layout.Template.Super, precos, remarcar);

    assertThat(auditoriaLog.isSuccess()).isFalse();
    assertThat(auditoriaLog.getLog())
      .contains("está com o mesmo preço")
      .hasLineCount(2);
  }

  @Test
  public void validaRegrasRemarcacaoErroCongelado() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(true);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(20d);
    priceInfo.setSkuId("123");
    priceInfo.setType("product");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PrecoManualBean> precos = new ArrayList<>();
    PrecoManualBean preco = PrecoManualBean.builder()
      .tipoPromocao(TipoPromocao.RS)
      .loja(7L)
      .codigo(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .func(BigDecimal.TEN)
      .sobreescrever(false)
      .build();
    precos.addAll(Arrays.asList(preco));

    List<ListaPrecoBean> remarcar = new ArrayList<>();
    validacaoService.validaRegrasRemarcacao(auditoriaLog, Layout.Template.Super, precos, remarcar);

    assertThat(auditoriaLog.isSuccess()).isFalse();
    assertThat(auditoriaLog.getLog())
      .contains("está congelado")
      .hasLineCount(2);
  }

  @Test
  public void validaRegrasRemarcacaoErroCampanhaComplexa() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(true);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(20d);
    priceInfo.setSkuId("123");
    priceInfo.setType("product");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PrecoManualBean> precos = new ArrayList<>();
    PrecoManualBean preco = PrecoManualBean.builder()
      .tipoPromocao(TipoPromocao.RS)
      .loja(7L)
      .codigo(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .func(BigDecimal.TEN)
      .sobreescrever(false)
      .build();
    precos.addAll(Arrays.asList(preco));

    List<ListaPrecoBean> remarcar = new ArrayList<>();
    validacaoService.validaRegrasRemarcacao(auditoriaLog, Layout.Template.Super, precos, remarcar);

    assertThat(auditoriaLog.isSuccess()).isFalse();
    assertThat(auditoriaLog.getLog())
      .contains("está em campanha complexa")
      .hasLineCount(2);
  }

  @Test
  public void validaRegrasRemarcacaoErroGetPrice() {
    when(atgService.getPrice(eq(123L), anyString())).thenThrow(IllegalStateException.class);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PrecoManualBean> precos = new ArrayList<>();
    PrecoManualBean preco = PrecoManualBean.builder()
      .tipoPromocao(TipoPromocao.RS)
      .loja(7L)
      .codigo(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .func(BigDecimal.TEN)
      .sobreescrever(false)
      .build();
    precos.addAll(Arrays.asList(preco));

    List<ListaPrecoBean> remarcar = new ArrayList<>();
    validacaoService.validaRegrasRemarcacao(auditoriaLog, Layout.Template.Super, precos, remarcar);

    assertThat(auditoriaLog.isSuccess()).isFalse();
    assertThat(auditoriaLog.getLog())
      .contains("Não foi possível consultar o preço para o produto")
      .hasLineCount(2);
  }

  @Test
  public void validaRegrasRemarcacaoErroListaInvalida() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(20d);
    priceInfo.setSkuId("123");
    priceInfo.setType("product");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PrecoManualBean> precos = new ArrayList<>();
    PrecoManualBean preco = PrecoManualBean.builder()
      .tipoPromocao(TipoPromocao.RS)
      .loja(999999L)
      .codigo(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .func(BigDecimal.TEN)
      .sobreescrever(false)
      .build();
    precos.addAll(Arrays.asList(preco));

    List<ListaPrecoBean> remarcar = new ArrayList<>();
    validacaoService.validaRegrasRemarcacao(auditoriaLog, Layout.Template.Super, precos, remarcar);

    assertThat(auditoriaLog.isSuccess()).isFalse();
    assertThat(auditoriaLog.getLog())
      .contains("Não foi possível localizar o código para a lista")
      .hasLineCount(2);
  }

  @Test
  public void validaRegrasRemarcacaoIgnoraValorNegativo() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(20d);
    priceInfo.setSkuId("123");
    priceInfo.setType("product");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PrecoManualBean> precos = new ArrayList<>();
    PrecoManualBean preco = PrecoManualBean.builder()
      .tipoPromocao(TipoPromocao.RS)
      .loja(7L)
      .codigo(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .func(BigDecimal.valueOf(-1.0d))
      .sobreescrever(false)
      .build();
    precos.addAll(Arrays.asList(preco));

    List<ListaPrecoBean> remarcar = new ArrayList<>();
    validacaoService.validaRegrasRemarcacao(auditoriaLog, Layout.Template.Super, precos, remarcar);

    assertThat(remarcar).isEmpty();
    assertThat(auditoriaLog.isSuccess()).isTrue();
  }

  @Test
  public void validaRegrasRemarcacaoIgnoraValorZerado() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(20d);
    priceInfo.setSkuId("123");
    priceInfo.setType("product");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PrecoManualBean> precos = new ArrayList<>();
    PrecoManualBean preco = PrecoManualBean.builder()
      .tipoPromocao(TipoPromocao.RS)
      .loja(7L)
      .codigo(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .func(BigDecimal.ZERO)
      .sobreescrever(false)
      .build();
    precos.addAll(Arrays.asList(preco));

    List<ListaPrecoBean> remarcar = new ArrayList<>();
    validacaoService.validaRegrasRemarcacao(auditoriaLog, Layout.Template.Super, precos, remarcar);

    assertThat(remarcar).isEmpty();
    assertThat(auditoriaLog.isSuccess()).isTrue();
  }

  @Test
  public void validaRegrasRemarcacaoFracionado() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(20d);
    priceInfo.setSkuId("123");
    priceInfo.setType("product-loose");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PrecoManualBean> precos = new ArrayList<>();
    PrecoManualBean preco = PrecoManualBean.builder()
      .tipoPromocao(TipoPromocao.RS)
      .loja(7L)
      .codigo(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .func(BigDecimal.TEN)
      .sobreescrever(false)
      .build();
    precos.addAll(Arrays.asList(preco));

    List<ListaPrecoBean> remarcar = new ArrayList<>();
    validacaoService.validaRegrasRemarcacao(auditoriaLog, Layout.Template.Super, precos, remarcar);

    assertThat(remarcar).hasSize(1);

    ListaPrecoBean lista = remarcar.get(0);
    assertThat(lista.getNome()).isEqualTo("Func.");
    assertThat(lista.getCodigo()).isEqualTo("plist3020067");
    assertThat(lista.getValor()).isEqualTo(BigDecimal.TEN);
    assertThat(lista.getValorRetorno()).isNull();
    assertThat(lista.getPreco()).isSameAs(preco);
    assertThat(lista.getValorFracionado()).isEqualTo(BigDecimal.valueOf(0.01d));

    assertThat(auditoriaLog.isSuccess()).isTrue();
  }

  @Test
  public void validaRegrasRemarcacaoErroDataFinal() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(20d);
    priceInfo.setSkuId("123");
    priceInfo.setType("product");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PrecoManualBean> precos = new ArrayList<>();
    PrecoManualBean preco1 = PrecoManualBean.builder()
      .tipoPromocao(TipoPromocao.RS)
      .loja(7L)
      .codigo(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .func(BigDecimal.TEN)
      .email(BigDecimal.TEN.add(BigDecimal.ONE))
      .sobreescrever(false)
      .build();
    PrecoManualBean preco2 = PrecoManualBean.builder()
      .tipoPromocao(TipoPromocao.RS)
      .loja(7L)
      .codigo(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now().plus(1, ChronoUnit.DAYS))
      .func(BigDecimal.TEN)
      .email(BigDecimal.TEN.add(BigDecimal.ONE))
      .sobreescrever(false)
      .build();
    precos.addAll(Arrays.asList(preco1, preco2));

    List<ListaPrecoBean> remarcar = new ArrayList<>();
    validacaoService.validaRegrasRemarcacao(auditoriaLog, Layout.Template.Super, precos, remarcar);

    assertThat(auditoriaLog.isSuccess()).isFalse();
    assertThat(auditoriaLog.getLog())
      .contains("data final de todos os preços do arquivo deve ser a mesma")
      .hasLineCount(2);
  }

  @Test
  public void validaRegrasRemarcacaoErroDataInicio() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(20d);
    priceInfo.setSkuId("123");
    priceInfo.setType("product");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PrecoManualBean> precos = new ArrayList<>();
    PrecoManualBean preco1 = PrecoManualBean.builder()
      .tipoPromocao(TipoPromocao.RS)
      .loja(7L)
      .codigo(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .func(BigDecimal.TEN)
      .email(BigDecimal.TEN.add(BigDecimal.ONE))
      .sobreescrever(false)
      .build();
    PrecoManualBean preco2 = PrecoManualBean.builder()
      .tipoPromocao(TipoPromocao.RS)
      .loja(7L)
      .codigo(123L)
      .dataInicio(LocalDateTime.now().plus(1, ChronoUnit.DAYS))
      .dataFim(LocalDateTime.now())
      .func(BigDecimal.TEN)
      .email(BigDecimal.TEN.add(BigDecimal.ONE))
      .sobreescrever(false)
      .build();
    precos.addAll(Arrays.asList(preco1, preco2));

    List<ListaPrecoBean> remarcar = new ArrayList<>();
    validacaoService.validaRegrasRemarcacao(auditoriaLog, Layout.Template.Super, precos, remarcar);

    assertThat(auditoriaLog.isSuccess()).isFalse();
    assertThat(auditoriaLog.getLog())
      .contains("data inicial de todos os preços do arquivo deve ser a mesma")
      .hasLineCount(2);
  }

  @Test
  public void validaRegrasRemarcacaoSuper() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(20d);
    priceInfo.setSkuId("123");
    priceInfo.setType("product");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PrecoManualBean> precos = new ArrayList<>();
    PrecoManualBean preco = PrecoManualBean.builder()
      .tipoPromocao(TipoPromocao.RS)
      .loja(7L)
      .codigo(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .func(BigDecimal.TEN)
      .email(BigDecimal.TEN.add(BigDecimal.ONE))
      .sobreescrever(false)
      .build();
    precos.addAll(Arrays.asList(preco));

    List<ListaPrecoBean> remarcar = new ArrayList<>();
    validacaoService.validaRegrasRemarcacao(auditoriaLog, Layout.Template.Super, precos, remarcar);

    assertThat(remarcar).hasSize(2);

    ListaPrecoBean listaEmail = remarcar.get(0);
    assertThat(listaEmail.getNome()).isEqualTo("Email");
    assertThat(listaEmail.getCodigo()).isEqualTo("plist3630002");
    assertThat(listaEmail.getValor()).isEqualTo(BigDecimal.TEN.add(BigDecimal.ONE));
    assertThat(listaEmail.getValorRetorno()).isNull();
    assertThat(listaEmail.getPreco()).isSameAs(preco);
    assertThat(listaEmail.getValorFracionado()).isNull();

    ListaPrecoBean listaFunc = remarcar.get(1);
    assertThat(listaFunc.getNome()).isEqualTo("Func.");
    assertThat(listaFunc.getCodigo()).isEqualTo("plist3020067");
    assertThat(listaFunc.getValor()).isEqualTo(BigDecimal.TEN);
    assertThat(listaFunc.getValorRetorno()).isNull();
    assertThat(listaFunc.getPreco()).isSameAs(preco);
    assertThat(listaFunc.getValorFracionado()).isNull();

    assertThat(auditoriaLog.isSuccess()).isTrue();
  }

  @Test
  public void validaRegrasRemarcacaoEletro() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(20d);
    priceInfo.setSkuId("123");
    priceInfo.setType("product");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PrecoManualBean> precos = new ArrayList<>();
    PrecoManualBean preco = PrecoManualBean.builder()
      .tipoPromocao(TipoPromocao.RE)
      .loja(PrecosUtil.LOJA_ELETRO)
      .codigo(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .shoppingDe(BigDecimal.TEN.add(BigDecimal.ONE))
      .retornoShoppingDe(BigDecimal.TEN)
      .sobreescrever(false)
      .build();
    precos.addAll(Arrays.asList(preco));

    List<ListaPrecoBean> remarcar = new ArrayList<>();
    validacaoService.validaRegrasRemarcacao(auditoriaLog, Layout.Template.Eletro, precos, remarcar);

    assertThat(remarcar).hasSize(1);

    ListaPrecoBean listaShopping = remarcar.get(0);
    assertThat(listaShopping.getNome()).isEqualTo("G. Shopping DE");
    assertThat(listaShopping.getCodigo()).isEqualTo("plist520017");
    assertThat(listaShopping.getValor()).isEqualTo(BigDecimal.TEN.add(BigDecimal.ONE));
    assertThat(listaShopping.getValorRetorno()).isEqualTo(BigDecimal.TEN);
    assertThat(listaShopping.getPreco()).isSameAs(preco);
    assertThat(listaShopping.getValorFracionado()).isNull();

    assertThat(auditoriaLog.isSuccess()).isTrue();
  }

  @Test
  public void validaRegrasRemarcacaoDivvino() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(20d);
    priceInfo.setSkuId("123");
    priceInfo.setType("product");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PrecoManualBean> precos = new ArrayList<>();
    PrecoManualBean preco = PrecoManualBean.builder()
      .tipoPromocao(TipoPromocao.RE)
      .loja(PrecosUtil.LOJA_DIVVINO)
      .codigo(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .clubeD(BigDecimal.TEN.add(BigDecimal.ONE))
      .retornoClubeD(BigDecimal.TEN)
      .sobreescrever(false)
      .build();
    precos.addAll(Arrays.asList(preco));

    List<ListaPrecoBean> remarcar = new ArrayList<>();
    validacaoService.validaRegrasRemarcacao(auditoriaLog, Layout.Template.Divvino, precos, remarcar);

    assertThat(remarcar).hasSize(1);

    ListaPrecoBean listaClube = remarcar.get(0);
    assertThat(listaClube.getNome()).isEqualTo("ClubeD");
    assertThat(listaClube.getCodigo()).isEqualTo("plist3200199");
    assertThat(listaClube.getValor()).isEqualTo(BigDecimal.TEN.add(BigDecimal.ONE));
    assertThat(listaClube.getValorRetorno()).isEqualTo(BigDecimal.TEN);
    assertThat(listaClube.getPreco()).isSameAs(preco);
    assertThat(listaClube.getValorFracionado()).isNull();

    assertThat(auditoriaLog.isSuccess()).isTrue();
  }

  @Test
  public void validaRegrasPromocao() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(20d);
    priceInfo.setSkuId("123");
    when(atgService.getPrice(anyLong(), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PromocaoManualBean> promocoes = new ArrayList<>();

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.PV)
      .produto(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .produtosDoados(Arrays.asList(1L))
      .valorDoado(BigDecimal.TEN)
      .lojas(Arrays.asList(7L))
      .sobreescrever(false)
      .build());

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.L)
      .produto(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .quantidadePagar(BigDecimal.TEN)
      .quantidadeDoado(BigDecimal.ONE)
      .lojas(Arrays.asList(7L))
      .sobreescrever(false)
      .build());

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.B)
      .produto(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .produtosDoados(Arrays.asList(1L))
      .quantidadeDoado(BigDecimal.ONE)
      .lojas(Arrays.asList(7L))
      .sobreescrever(false)
      .build());

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.U)
      .produto(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .valorDoado(BigDecimal.TEN)
      .percentualDesconto(BigDecimal.ONE)
      .lojas(Arrays.asList(7L))
      .sobreescrever(false)
      .build());

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.P)
      .produto(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .valorDoado(BigDecimal.TEN)
      .quantidadePagar(BigDecimal.ONE)
      .lojas(Arrays.asList(7L))
      .sobreescrever(false)
      .build());

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.PN)
      .patamar(PromocaoManualBean.Patamar.PF)
      .nivel(1L)
      .produto(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .valorPorcentagem(BigDecimal.TEN)
      .lojas(Arrays.asList(7L))
      .sobreescrever(false)
      .build());

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.PN)
      .patamar(PromocaoManualBean.Patamar.PF)
      .nivel(2L)
      .produto(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .valorPorcentagem(BigDecimal.TEN)
      .lojas(Arrays.asList(7L))
      .sobreescrever(false)
      .build());

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.PN)
      .patamar(PromocaoManualBean.Patamar.PP)
      .nivel(1L)
      .produto(444L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .valorPorcentagem(BigDecimal.TEN)
      .lojas(Arrays.asList(7L))
      .sobreescrever(false)
      .build());

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.PN)
      .patamar(PromocaoManualBean.Patamar.PP)
      .nivel(2L)
      .produto(444L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .valorPorcentagem(BigDecimal.TEN)
      .lojas(Arrays.asList(7L))
      .sobreescrever(false)
      .build());

    List<AtivarPromocaoBean> ativarPromocoes = new ArrayList<>();
    validacaoService.validaRegrasPromocao(auditoriaLog, promocoes, ativarPromocoes);

    assertThat(ativarPromocoes).hasSize(7);

    for (AtivarPromocaoBean promo : ativarPromocoes) {
      switch (promo.getPromocao().getTipoPromocao()) {
        case PV:
          assertThat(promo.getValor()).isEqualTo(BigDecimal.TEN);
          break;
        case L:
          assertThat(promo.getValor()).isEqualTo(BigDecimal.valueOf(20.0d));
          break;
        case B:
          assertThat(promo.getValor()).isEqualTo(BigDecimal.valueOf(20.0d));
          break;
        case U:
          assertThat(promo.getValor()).isEqualTo(BigDecimal.TEN);
          break;
        case P:
          assertThat(promo.getValor()).isEqualTo(BigDecimal.TEN);
          break;
        case PN:
          if (promo.getPromocao().getPatamar() == PromocaoManualBean.Patamar.PF) {
            assertThat(promo.getValor()).isNull();
            for (ListaPromocaoBean lista : promo.getCodigosLista()) {
              assertThat(lista.getValorNiveis()).hasSize(2);
              for (NivelPromocaoBean nivel : lista.getValorNiveis()) {
                assertThat(nivel.getValor()).isEqualTo(BigDecimal.TEN);
              }
            }
          } else {
            assertThat(promo.getValor()).isNull();
            for (ListaPromocaoBean lista : promo.getCodigosLista()) {
              assertThat(lista.getValorNiveis()).hasSize(2);
              for (NivelPromocaoBean nivel : lista.getValorNiveis()) {
                assertThat(nivel.getValor()).isEqualTo(BigDecimal.valueOf(18.0d));
              }
            }
          }
          break;
      }

      assertThat(promo.getCodigosLista().stream().map(ListaPromocaoBean::getCodigo))
        .hasSize(7)
        .isSubsetOf("plist2470182", "plist3020067", "plist3020068", "plist3020069", "plist4040003", "plist3020070", "plist3630002");
    }

    assertThat(auditoriaLog.isSuccess()).isTrue();
  }

  @Test
  public void validaRegrasPromocaoErroCampos() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(20d);
    priceInfo.setSkuId("123");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PromocaoManualBean> promocoes = new ArrayList<>();

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.PV)
      .produto(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .produtosDoados(Arrays.asList(1L))
      .lojas(Arrays.asList(7L))
      .sobreescrever(false)
      .build());

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.L)
      .produto(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .quantidadePagar(BigDecimal.TEN)
      .lojas(Arrays.asList(7L))
      .sobreescrever(false)
      .build());

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.B)
      .produto(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .produtosDoados(Arrays.asList(1L))
      .lojas(Arrays.asList(7L))
      .sobreescrever(false)
      .build());

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.U)
      .produto(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .lojas(Arrays.asList(7L))
      .sobreescrever(false)
      .build());

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.P)
      .produto(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .lojas(Arrays.asList(7L))
      .sobreescrever(false)
      .build());

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.PN)
      .patamar(PromocaoManualBean.Patamar.PF)
      .nivel(1L)
      .produto(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .lojas(Arrays.asList(7L))
      .sobreescrever(false)
      .build());

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.PN)
      .patamar(PromocaoManualBean.Patamar.PP)
      .nivel(1L)
      .produto(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .lojas(Arrays.asList(7L))
      .sobreescrever(false)
      .build());

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.RS)
      .build());

    List<AtivarPromocaoBean> ativarPromocoes = new ArrayList<>();
    validacaoService.validaRegrasPromocao(auditoriaLog, promocoes, ativarPromocoes);

    for (AtivarPromocaoBean promo : ativarPromocoes) {
      assertThat(promo.getValor()).isNull();
      assertThat(promo.getCodigosLista()).isEmpty();
    }

    assertThat(auditoriaLog.isSuccess()).isFalse();
    assertThat(auditoriaLog.getLog())
      .contains("ERRO! Pack Virtual")
      .contains("ERRO! Leve e Pague")
      .contains("ERRO! Brinde")
      .contains("ERRO! Desconto Último Item")
      .contains("ERRO! Patamar")
      .contains("ERRO! Patamar em Nível")
      .contains("ERRO! Tipo de promoção inválido! (RS)")
      .hasLineCount(9);
  }

  @Test
  public void validaRegrasPromocaoErroCampanhaComplexa() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(true);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(20d);
    priceInfo.setSkuId("123");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PromocaoManualBean> promocoes = new ArrayList<>();

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.PV)
      .produto(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .produtosDoados(Arrays.asList(1L))
      .valorDoado(BigDecimal.TEN)
      .lojas(Arrays.asList(7L))
      .sobreescrever(false)
      .build());

    List<AtivarPromocaoBean> ativarPromocoes = new ArrayList<>();
    validacaoService.validaRegrasPromocao(auditoriaLog, promocoes, ativarPromocoes);

    assertThat(auditoriaLog.isSuccess()).isFalse();
    assertThat(auditoriaLog.getLog())
      .contains("está em campanha complexa")
      .hasLineCount(8);
  }

  @Test
  public void validaRegrasPromocaoErroCongelado() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(true);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(20d);
    priceInfo.setSkuId("123");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PromocaoManualBean> promocoes = new ArrayList<>();

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.PV)
      .produto(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .produtosDoados(Arrays.asList(1L))
      .valorDoado(BigDecimal.TEN)
      .lojas(Arrays.asList(7L))
      .sobreescrever(false)
      .build());

    List<AtivarPromocaoBean> ativarPromocoes = new ArrayList<>();
    validacaoService.validaRegrasPromocao(auditoriaLog, promocoes, ativarPromocoes);

    assertThat(auditoriaLog.isSuccess()).isFalse();
    assertThat(auditoriaLog.getLog())
      .contains("está congelado")
      .hasLineCount(8);
  }

  @Test
  public void validaRegrasPromocaoErroMargem() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(10d);
    priceInfo.setPrice(20d);
    priceInfo.setSkuId("123");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PromocaoManualBean> promocoes = new ArrayList<>();

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.PV)
      .produto(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .produtosDoados(Arrays.asList(1L))
      .valorDoado(BigDecimal.TEN)
      .lojas(Arrays.asList(7L))
      .sobreescrever(false)
      .build());

    List<AtivarPromocaoBean> ativarPromocoes = new ArrayList<>();
    validacaoService.validaRegrasPromocao(auditoriaLog, promocoes, ativarPromocoes);

    assertThat(auditoriaLog.isSuccess()).isFalse();
    assertThat(auditoriaLog.getLog())
      .contains("está com o preço menor que a margem")
      .hasLineCount(8);
  }

  @Test
  public void validaRegrasPromocaoErroPrecoIgual() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(10d);
    priceInfo.setSkuId("123");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PromocaoManualBean> promocoes = new ArrayList<>();

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.PV)
      .produto(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .produtosDoados(Arrays.asList(1L))
      .valorDoado(BigDecimal.TEN)
      .lojas(Arrays.asList(7L))
      .sobreescrever(false)
      .build());

    List<AtivarPromocaoBean> ativarPromocoes = new ArrayList<>();
    validacaoService.validaRegrasPromocao(auditoriaLog, promocoes, ativarPromocoes);

    assertThat(auditoriaLog.isSuccess()).isFalse();
    assertThat(auditoriaLog.getLog())
      .contains("está com o mesmo preço")
      .hasLineCount(8);
  }

  @Test
  public void validaRegrasPromocaoSobreescrever() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(true);
    priceInfo.setFreezing(true);
    priceInfo.setCmv(10d);
    priceInfo.setPrice(10d);
    priceInfo.setSkuId("123");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PromocaoManualBean> promocoes = new ArrayList<>();

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.PV)
      .produto(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .produtosDoados(Arrays.asList(1L))
      .valorDoado(BigDecimal.TEN)
      .lojas(Arrays.asList(7L))
      .sobreescrever(true)
      .build());

    List<AtivarPromocaoBean> ativarPromocoes = new ArrayList<>();
    validacaoService.validaRegrasPromocao(auditoriaLog, promocoes, ativarPromocoes);

    AtivarPromocaoBean promo = ativarPromocoes.get(0);
    assertThat(promo.getValor()).isEqualTo(BigDecimal.TEN);
    assertThat(promo.getCodigosLista().stream().map(ListaPromocaoBean::getCodigo))
      .hasSize(7)
      .isSubsetOf("plist2470182", "plist3020067", "plist3020068", "plist3020069", "plist4040003", "plist3020070", "plist3630002");
    assertThat(auditoriaLog.isSuccess()).isTrue();
  }

  @Test
  public void validaRegrasPromocaoErroGetPrice() {
    when(atgService.getPrice(eq(123L), anyString())).thenThrow(IllegalStateException.class);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PromocaoManualBean> promocoes = new ArrayList<>();

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.PV)
      .produto(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .produtosDoados(Arrays.asList(1L))
      .valorDoado(BigDecimal.TEN)
      .lojas(Arrays.asList(7L))
      .sobreescrever(false)
      .build());

    List<AtivarPromocaoBean> ativarPromocoes = new ArrayList<>();
    validacaoService.validaRegrasPromocao(auditoriaLog, promocoes, ativarPromocoes);

    AtivarPromocaoBean promo = ativarPromocoes.get(0);
    assertThat(promo.getValor()).isNull();
    assertThat(promo.getCodigosLista().stream().map(ListaPromocaoBean::getCodigo))
      .hasSize(7)
      .isSubsetOf("plist2470182", "plist3020067", "plist3020068", "plist3020069", "plist4040003", "plist3020070", "plist3630002");

    assertThat(auditoriaLog.isSuccess()).isFalse();
    assertThat(auditoriaLog.getLog())
      .contains("Não foi possível consultar o preço para o produto")
      .hasLineCount(8);
  }

  @Test
  public void validaRegrasPromocaoListaErro() {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(10d);
    priceInfo.setSkuId("123");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    List<PromocaoManualBean> promocoes = new ArrayList<>();

    promocoes.add(PromocaoManualBean.builder()
      .tipoPromocao(TipoPromocao.PV)
      .produto(123L)
      .dataInicio(LocalDateTime.now())
      .dataFim(LocalDateTime.now())
      .produtosDoados(Arrays.asList(1L))
      .valorDoado(BigDecimal.TEN)
      .lojas(Arrays.asList(9999L))
      .sobreescrever(false)
      .build());

    List<AtivarPromocaoBean> ativarPromocoes = new ArrayList<>();
    validacaoService.validaRegrasPromocao(auditoriaLog, promocoes, ativarPromocoes);

    AtivarPromocaoBean promo = ativarPromocoes.get(0);
    assertThat(promo.getValor()).isNull();
    assertThat(promo.getCodigosLista()).isEmpty();

    assertThat(auditoriaLog.isSuccess()).isFalse();
    assertThat(auditoriaLog.getLog())
      .contains("Não foram encontradas listas")
      .hasLineCount(2);
  }

}