package br.com.angeloni.precos.server.service;

import br.com.angeloni.precos.server.csv.*;
import br.com.angeloni.precos.server.data.AuditoriaEntity;
import br.com.angeloni.precos.server.data.AuditoriaLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PrecificacaoService {

  @Autowired
  private PrecosCsvReader precosCsvReader;

  @Autowired
  private AuditoriaService auditoriaService;

  @Autowired
  private ValidacaoService validacaoService;

  @Autowired
  private RemarcacaoService remarcacaoService;

  @Autowired
  private PromocaoService promocaoService;

  public AuditoriaEntity processaArquivo(String ip, String nomeArquivo, byte[] conteudoArquivo) {
    AuditoriaEntity auditoria = auditoriaService.registraProcessamento(ip, nomeArquivo, conteudoArquivo);
    AuditoriaLog auditoriaLog = new AuditoriaLog(auditoria);

    auditoriaLog.log("Início do processamento do arquivo \"" + nomeArquivo + "\"");
    try {
      Layout.Template template = precosCsvReader.detectaLayout(new ByteArrayInputStream(conteudoArquivo));
      auditoriaLog.log("Tipo de layout detectado: " + template);
      auditoriaLog.log("Processando arquivo ...");
      if (template == Layout.Template.Eletro || template == Layout.Template.Super || template == Layout.Template.Divvino) {
        List<PrecoManualBean> precos = precosCsvReader.processa(new ByteArrayInputStream(conteudoArquivo), template);
        List<ListaPrecoBean> remarcar = new ArrayList<>();
        validacaoService.validaRegrasRemarcacao(auditoriaLog, template, precos, remarcar);
        if (auditoriaLog.isSuccess()) {
          remarcacaoService.remarcarPrecos(auditoriaLog, remarcar);
        }
      } else if (template == Layout.Template.Promocao || template == Layout.Template.PromocaoN) {
        List<PromocaoManualBean> promocoes = precosCsvReader.processa(new ByteArrayInputStream(conteudoArquivo), template);
        List<AtivarPromocaoBean> ativarPromocoes = new ArrayList<>();
        validacaoService.validaRegrasPromocao(auditoriaLog, promocoes, ativarPromocoes);
        if (auditoriaLog.isSuccess()) {
          promocaoService.ativarPromocoes(auditoriaLog, ativarPromocoes);
        }
      }
    } catch (Exception ex) {
      auditoriaLog.logError(ex.getMessage() != null ? ex.getMessage() : "Erro inesperado, contate o administrador.");
      log.error(ex.getMessage(), ex);
    } finally {
      auditoriaLog.log("Processamento finalizado com " + (auditoriaLog.isSuccess() ? "SUCESSO" : "ERRO") + "!");
      return auditoriaService.finalizaProcessamento(auditoria.getId(), auditoriaLog);
    }
  }

}
