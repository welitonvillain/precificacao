package br.com.angeloni.precos.server.service;

import br.com.angeloni.precos.server.csv.*;
import br.com.angeloni.precos.server.data.AuditoriaEntity;
import br.com.angeloni.precos.server.data.AuditoriaLog;
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
public class PromocaoServiceTest {

  @Autowired
  private PromocaoService promocaoService;

  @Autowired
  private ValidacaoService validacaoService;

  @MockBean
  private AtgService atgService;

  @Test
  public void ativarPromocoesEmpty() {
    AuditoriaLog auditoriaLog = new AuditoriaLog(new AuditoriaEntity());
    auditoriaLog.setTimestamp(false);

    promocaoService.ativarPromocoes(auditoriaLog, new ArrayList<>());
    assertThat(auditoriaLog.isSuccess()).isFalse();
    assertThat(auditoriaLog.getLog())
      .contains("Nenhuma promoção para ativar");
  }

  @Test
  public void ativarPromocoes() throws Exception {
    PriceInfo priceInfo = new PriceInfo();
    priceInfo.setComplexCampaign(false);
    priceInfo.setFreezing(false);
    priceInfo.setCmv(1d);
    priceInfo.setPrice(20d);
    priceInfo.setSkuId("123");
    when(atgService.getPrice(anyLong(), anyString())).thenReturn(priceInfo);

    when(atgService.setVirtualPack(any(VirtualPackServiceListInfo.class))).thenReturn(Arrays.asList("FAKE_ID"));

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
    assertThat(auditoriaLog.isSuccess()).isTrue();

    assertThat(ativarPromocoes).hasSize(7);

    promocaoService.ativarPromocoes(auditoriaLog, ativarPromocoes);

    verify(atgService, times(1)).setVirtualPack(any(VirtualPackServiceListInfo.class));
    verify(atgService, times(1)).setGetXPayY(any(GetXPayYListInfo.class));
    verify(atgService, times(1)).setPromotionLastItem(any(PromotionLastItemListInfo.class));
    verify(atgService, times(1)).setGifts(any(GiftsListInfo.class));
    verify(atgService, times(1)).setBuyXOrMorePayY(any(BuyXOrMorePayYListInfo.class));
    verify(atgService, times(1)).setBulkPrice(any(BulkPriceListInfo.class));

    assertThat(auditoriaLog.isSuccess()).isTrue();
    assertThat(auditoriaLog.getLog())
      .contains("Ativando promoções do tipo PV...")
      .contains("Pack Virtual ativado com sucesso")
      .contains("Ativando promoções do tipo L...")
      .contains("Leve e Pague ativado com sucesso")
      .contains("Ativando promoções do tipo B...")
      .contains("Brinde ativado com sucesso")
      .contains("Ativando promoções do tipo U...")
      .contains("Desconto Último Item ativado com sucesso")
      .contains("Ativando promoções do tipo P...")
      .contains("Patamar ativado com sucesso")
      .contains("Ativando promoções do tipo PN...")
      .contains("Patamar em Nível ativado com sucesso");
  }
}