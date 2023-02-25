package br.com.angeloni.precos.server.service;

import br.com.angeloni.precos.server.csv.*;
import br.com.angeloni.precos.server.data.AuditoriaEntity;
import br.com.angeloni.precos.server.data.AuditoriaLog;
import br.com.angeloni.precos.server.erp.api.ControlePrecoApi;
import br.com.angeloni.precos.server.erp.model.ControlePreco;
import br.com.angeloni.precos.server.erp.model.RetornoControlePreco;
import br.com.angeloni.precos.server.erp.wrapper.RetornoListWrapper;
import br.com.angeloni.precos.server.erp.wrapper.RetornoResponseWrapper;
import br.com.angeloni.ws.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RemarcacaoServiceTest {

  @Autowired
  private ValidacaoService validacaoService;

  @Autowired
  private RemarcacaoService remarcacaoService;

  @MockBean
  private AtgService atgService;

  @MockBean
  private ControlePrecoApi controlePrecoApi;

  @Test
  public void testRemarcacao() throws Exception {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(20d);
    priceInfo.setSkuId("123");
    priceInfo.setType("product");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    ResultPriceListInfo result = new ResultPriceListInfo();
    result.setIntegrationId("FAKE_ID");
    when(atgService.syncPriceList(any(PriceListInfo.class))).thenReturn(result);

    RetornoResponseWrapper retorno = new RetornoResponseWrapper();
    RetornoListWrapper dsStatus = new RetornoListWrapper();
    RetornoControlePreco controlePreco = new RetornoControlePreco();
    controlePreco.setSucesso(true);
    controlePreco.setDescricao("Sucesso!");
    dsStatus.setRetorno(Arrays.asList(controlePreco));
    retorno.setDsStatus(dsStatus);
    when(controlePrecoApi.controlePrecoV01Post(any(ControlePreco.class))).thenReturn(retorno);

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
    assertThat(auditoriaLog.isSuccess()).isTrue();
    assertThat(remarcar).hasSize(2);

    remarcacaoService.remarcarPrecos(auditoriaLog, remarcar);

    verify(atgService, times(2)).syncPriceList(any(PriceListInfo.class));
    verify(controlePrecoApi, times(2)).controlePrecoV01Post(any(ControlePreco.class));

    assertThat(auditoriaLog.isSuccess()).isTrue();
    assertThat(auditoriaLog.getLog())
      .contains("Remarcando preços para a lista: plist3020067")
      .contains("Remarcando preços para a lista: plist3630002")
    .contains("Integração com ATG id: FAKE_ID concluída com sucesso");
  }

  @Test
  public void testRemarcacaoNoop() throws Exception {
    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);
    List<ListaPrecoBean> remarcar = new ArrayList<>();
    remarcacaoService.remarcarPrecos(auditoriaLog, remarcar);

    verify(atgService, times(0)).syncPriceList(any(PriceListInfo.class));
    verify(controlePrecoApi, times(0)).controlePrecoV01Post(any(ControlePreco.class));

    assertThat(auditoriaLog.isSuccess()).isFalse();
    assertThat(auditoriaLog.getLog())
      .contains("Nenhum produto para remarcar");
  }

  @Test
  public void testRemarcacaoErroSyncPriceList() throws Exception {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(20d);
    priceInfo.setSkuId("123");
    priceInfo.setType("product");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    ResultPriceListInfo result = new ResultPriceListInfo();
    result.setIntegrationId("FAKE_ID");
    ResultItemPriceListInfo erro = new ResultItemPriceListInfo();
    ResultItemPriceListInfo.Violations violations = new ResultItemPriceListInfo.Violations();
    ResultItemPriceListInfo.Violations.Entry violationEntry = new ResultItemPriceListInfo.Violations.Entry();
    violationEntry.setKey("CODE: 000");
    violationEntry.setValue("DESCRIPTION: ERRO");
    violations.getEntry().add(violationEntry);
    ItemPriceListInfo item = new ItemPriceListInfo();
    item.setAction("SET");
    item.setPrice(10d);
    item.setSkuId("123");
    item.setMargin(0d);
    erro.setItem(item);
    erro.setViolations(violations);
    result.getItemsNotOK().add(erro);
    when(atgService.syncPriceList(any(PriceListInfo.class))).thenReturn(result);

    RetornoResponseWrapper retorno = new RetornoResponseWrapper();
    RetornoListWrapper dsStatus = new RetornoListWrapper();
    RetornoControlePreco controlePreco = new RetornoControlePreco();
    controlePreco.setSucesso(true);
    controlePreco.setDescricao("Sucesso!");
    dsStatus.setRetorno(Arrays.asList(controlePreco));
    retorno.setDsStatus(dsStatus);
    when(controlePrecoApi.controlePrecoV01Post(any(ControlePreco.class))).thenReturn(retorno);

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
    assertThat(auditoriaLog.isSuccess()).isTrue();
    assertThat(remarcar).hasSize(2);

    remarcacaoService.remarcarPrecos(auditoriaLog, remarcar);

    verify(atgService, times(2)).syncPriceList(any(PriceListInfo.class));
    verify(controlePrecoApi, times(0)).controlePrecoV01Post(any(ControlePreco.class));

    assertThat(auditoriaLog.isSuccess()).isFalse();
    assertThat(auditoriaLog.getLog())
      .contains("Remarcando preços para a lista: plist3020067")
      .contains("Remarcando preços para a lista: plist3630002")
      .contains("Integração com ATG id: FAKE_ID concluída com erros");
  }

  @Test
  public void testRemarcacaoErroControlePrecos() throws Exception {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(20d);
    priceInfo.setSkuId("123");
    priceInfo.setType("product");
    when(atgService.getPrice(eq(123L), anyString())).thenReturn(priceInfo);

    ResultPriceListInfo result = new ResultPriceListInfo();
    result.setIntegrationId("FAKE_ID");
    when(atgService.syncPriceList(any(PriceListInfo.class))).thenReturn(result);

    RetornoResponseWrapper retorno = new RetornoResponseWrapper();
    RetornoListWrapper dsStatus = new RetornoListWrapper();
    RetornoControlePreco controlePreco = new RetornoControlePreco();
    controlePreco.setSucesso(false);
    controlePreco.setDescricao("Erro!");
    dsStatus.setRetorno(Arrays.asList(controlePreco));
    retorno.setDsStatus(dsStatus);
    when(controlePrecoApi.controlePrecoV01Post(any(ControlePreco.class))).thenReturn(retorno);

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
    assertThat(auditoriaLog.isSuccess()).isTrue();
    assertThat(remarcar).hasSize(2);

    remarcacaoService.remarcarPrecos(auditoriaLog, remarcar);

    verify(atgService, times(2)).syncPriceList(any(PriceListInfo.class));
    verify(controlePrecoApi, times(2)).controlePrecoV01Post(any(ControlePreco.class));

    assertThat(auditoriaLog.isSuccess()).isFalse();
    assertThat(auditoriaLog.getLog())
      .contains("Remarcando preços para a lista: plist3020067")
      .contains("Remarcando preços para a lista: plist3630002")
      .contains("Integração com ATG id: FAKE_ID concluída com sucesso")
      .contains("Problema ao processar o controle de preços no ERP");
  }

}