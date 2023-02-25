package br.com.angeloni.precos.server.service;

import br.com.angeloni.precos.server.csv.Layout;
import br.com.angeloni.precos.server.data.AuditoriaEntity;
import br.com.angeloni.precos.server.data.AuditoriaLog;
import org.aspectj.util.FileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PrecificacaoServiceTest {

  @Autowired
  private PrecificacaoService precificacaoService;

  @MockBean
  private ValidacaoService validacaoService;

  @MockBean
  private RemarcacaoService remarcacaoService;

  @MockBean
  private PromocaoService promocaoService;

  @Test
  public void processaArquivoSuper() throws Exception {
    String ip = "123.123.123.123";
    String arquivo = "Preco_manual_Super.csv";
    byte[] conteudo = FileUtil.readAsByteArray(new File("src/test/resources/mock/" + arquivo));

    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("testuser", null));

    AuditoriaEntity auditoria = precificacaoService.processaArquivo(ip, arquivo, conteudo);

    verify(validacaoService, times(1))
      .validaRegrasRemarcacao(any(AuditoriaLog.class), eq(Layout.Template.Super), any(List.class), any(List.class));
    verify(validacaoService, times(0))
      .validaRegrasPromocao(any(AuditoriaLog.class), any(List.class), any(List.class));
    verify(remarcacaoService, times(1))
      .remarcarPrecos(any(AuditoriaLog.class), any(List.class));
    verify(promocaoService, times(0)).ativarPromocoes(any(AuditoriaLog.class), any(List.class));

    assertThat(auditoria.getStringId()).isNotNull();
    assertThat(auditoria.getConteudoArquivo()).isEqualTo(conteudo);
    assertThat(auditoria.getNomeArquivo()).isEqualTo(arquivo);
    assertThat(auditoria.getIp()).isEqualTo(ip);
    assertThat(auditoria.getUsuario()).isEqualTo("testuser");
    assertThat(auditoria.getDataHoraInicio()).isNotNull();
    assertThat(auditoria.getDataHoraFim()).isNotNull();
    assertThat(auditoria.getSucesso()).isTrue();
    assertThat(auditoria.getResultado())
      .contains("Início do processamento do arquivo")
      .contains("Tipo de layout detectado")
      .contains("Processando arquivo")
      .contains("Processamento finalizado com SUCESSO");
  }

  @Test
  public void processaArquivoEletro() throws Exception {
    String ip = "123.123.123.123";
    String arquivo = "Preco_manual_Eletro.csv";
    byte[] conteudo = FileUtil.readAsByteArray(new File("src/test/resources/mock/" + arquivo));

    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("testuser", null));

    AuditoriaEntity auditoria = precificacaoService.processaArquivo(ip, arquivo, conteudo);

    verify(validacaoService, times(1))
      .validaRegrasRemarcacao(any(AuditoriaLog.class), eq(Layout.Template.Eletro), any(List.class), any(List.class));
    verify(validacaoService, times(0))
      .validaRegrasPromocao(any(AuditoriaLog.class), any(List.class), any(List.class));
    verify(remarcacaoService, times(1))
      .remarcarPrecos(any(AuditoriaLog.class), any(List.class));
    verify(promocaoService, times(0)).ativarPromocoes(any(AuditoriaLog.class), any(List.class));

    assertThat(auditoria.getStringId()).isNotNull();
    assertThat(auditoria.getConteudoArquivo()).isEqualTo(conteudo);
    assertThat(auditoria.getNomeArquivo()).isEqualTo(arquivo);
    assertThat(auditoria.getIp()).isEqualTo(ip);
    assertThat(auditoria.getUsuario()).isEqualTo("testuser");
    assertThat(auditoria.getDataHoraInicio()).isNotNull();
    assertThat(auditoria.getDataHoraFim()).isNotNull();
    assertThat(auditoria.getSucesso()).isTrue();
    assertThat(auditoria.getResultado())
      .contains("Início do processamento do arquivo")
      .contains("Tipo de layout detectado")
      .contains("Processando arquivo")
      .contains("Processamento finalizado com SUCESSO");
  }

  @Test
  public void processaArquivoDivvino() throws Exception {
    String ip = "123.123.123.123";
    String arquivo = "Preco_manual_Divvino.csv";
    byte[] conteudo = FileUtil.readAsByteArray(new File("src/test/resources/mock/" + arquivo));

    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("testuser", null));

    AuditoriaEntity auditoria = precificacaoService.processaArquivo(ip, arquivo, conteudo);

    verify(validacaoService, times(1))
      .validaRegrasRemarcacao(any(AuditoriaLog.class), eq(Layout.Template.Divvino), any(List.class), any(List.class));
    verify(validacaoService, times(0))
      .validaRegrasPromocao(any(AuditoriaLog.class), any(List.class), any(List.class));
    verify(remarcacaoService, times(1))
      .remarcarPrecos(any(AuditoriaLog.class), any(List.class));
    verify(promocaoService, times(0)).ativarPromocoes(any(AuditoriaLog.class), any(List.class));

    assertThat(auditoria.getStringId()).isNotNull();
    assertThat(auditoria.getConteudoArquivo()).isEqualTo(conteudo);
    assertThat(auditoria.getNomeArquivo()).isEqualTo(arquivo);
    assertThat(auditoria.getIp()).isEqualTo(ip);
    assertThat(auditoria.getUsuario()).isEqualTo("testuser");
    assertThat(auditoria.getDataHoraInicio()).isNotNull();
    assertThat(auditoria.getDataHoraFim()).isNotNull();
    assertThat(auditoria.getSucesso()).isTrue();
    assertThat(auditoria.getResultado())
      .contains("Início do processamento do arquivo")
      .contains("Tipo de layout detectado")
      .contains("Processando arquivo")
      .contains("Processamento finalizado com SUCESSO");
  }

  @Test
  public void processaArquivoPromocao() throws Exception {
    String ip = "123.123.123.123";
    String arquivo = "Promocao_manual.csv";
    byte[] conteudo = FileUtil.readAsByteArray(new File("src/test/resources/mock/" + arquivo));

    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("testuser", null));

    AuditoriaEntity auditoria = precificacaoService.processaArquivo(ip, arquivo, conteudo);

    verify(validacaoService, times(0))
      .validaRegrasRemarcacao(any(AuditoriaLog.class), any(Layout.Template.class), any(List.class), any(List.class));
    verify(validacaoService, times(1))
      .validaRegrasPromocao(any(AuditoriaLog.class), any(List.class), any(List.class));
    verify(remarcacaoService, times(0))
      .remarcarPrecos(any(AuditoriaLog.class), any(List.class));
    verify(promocaoService, times(1)).ativarPromocoes(any(AuditoriaLog.class), any(List.class));

    assertThat(auditoria.getStringId()).isNotNull();
    assertThat(auditoria.getConteudoArquivo()).isEqualTo(conteudo);
    assertThat(auditoria.getNomeArquivo()).isEqualTo(arquivo);
    assertThat(auditoria.getIp()).isEqualTo(ip);
    assertThat(auditoria.getUsuario()).isEqualTo("testuser");
    assertThat(auditoria.getDataHoraInicio()).isNotNull();
    assertThat(auditoria.getDataHoraFim()).isNotNull();
    assertThat(auditoria.getSucesso()).isTrue();
    assertThat(auditoria.getResultado())
      .contains("Início do processamento do arquivo")
      .contains("Tipo de layout detectado")
      .contains("Processando arquivo")
      .contains("Processamento finalizado com SUCESSO");
  }

  @Test
  public void processaArquivoPromocaoNivel() throws Exception {
    String ip = "123.123.123.123";
    String arquivo = "Promocao_PN_manual.csv";
    byte[] conteudo = FileUtil.readAsByteArray(new File("src/test/resources/mock/" + arquivo));

    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("testuser", null));

    AuditoriaEntity auditoria = precificacaoService.processaArquivo(ip, arquivo, conteudo);

    verify(validacaoService, times(0))
      .validaRegrasRemarcacao(any(AuditoriaLog.class), any(Layout.Template.class), any(List.class), any(List.class));
    verify(validacaoService, times(1))
      .validaRegrasPromocao(any(AuditoriaLog.class), any(List.class), any(List.class));
    verify(remarcacaoService, times(0))
      .remarcarPrecos(any(AuditoriaLog.class), any(List.class));
    verify(promocaoService, times(1)).ativarPromocoes(any(AuditoriaLog.class), any(List.class));

    assertThat(auditoria.getStringId()).isNotNull();
    assertThat(auditoria.getConteudoArquivo()).isEqualTo(conteudo);
    assertThat(auditoria.getNomeArquivo()).isEqualTo(arquivo);
    assertThat(auditoria.getIp()).isEqualTo(ip);
    assertThat(auditoria.getUsuario()).isEqualTo("testuser");
    assertThat(auditoria.getDataHoraInicio()).isNotNull();
    assertThat(auditoria.getDataHoraFim()).isNotNull();
    assertThat(auditoria.getSucesso()).isTrue();
    assertThat(auditoria.getResultado())
      .contains("Início do processamento do arquivo")
      .contains("Tipo de layout detectado")
      .contains("Processando arquivo")
      .contains("Processamento finalizado com SUCESSO");
  }
}