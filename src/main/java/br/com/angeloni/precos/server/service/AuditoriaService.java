package br.com.angeloni.precos.server.service;

import br.com.angeloni.precos.server.config.LDAPSecurityConfig;
import br.com.angeloni.precos.server.data.AuditoriaEntity;
import br.com.angeloni.precos.server.data.AuditoriaLog;
import br.com.angeloni.precos.server.data.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AuditoriaService {

  @Autowired
  private AuditoriaRepository auditoriaRepository;

  @Autowired
  private LDAPSecurityConfig securityConfig;

  public AuditoriaEntity registraProcessamento(String ip, String nomeArquivo, byte[] conteudoArquivo) {
    AuditoriaEntity auditoria = new AuditoriaEntity();
    auditoria.setDataHoraInicio(LocalDateTime.now());
    auditoria.setUsuario(securityConfig.getCurrentUser());
    auditoria.setIp(ip);
    auditoria.setNomeArquivo(nomeArquivo);
    auditoria.setConteudoArquivo(conteudoArquivo);
    return auditoriaRepository.saveAndFlush(auditoria);
  }

  public AuditoriaEntity finalizaProcessamento(UUID id, AuditoriaLog log) {
    AuditoriaEntity auditoria = auditoriaRepository.findById(id).orElseThrow(EntityExistsException::new);
    auditoria.setDataHoraFim(LocalDateTime.now());
    auditoria.getResultado(); //force load from DB
    auditoria.setResultado(log.getLog());
    auditoria.setSucesso(log.isSuccess());
    return auditoriaRepository.saveAndFlush(auditoria);
  }

  public AuditoriaEntity findProcessamento(UUID id) {
    AuditoriaEntity auditoria = auditoriaRepository.findById(id).orElseThrow(EntityExistsException::new);
    if (!securityConfig.isCurrentUserAdmin() && !auditoria.getUsuario().equals(securityConfig.getCurrentUser())) {
      throw new IllegalArgumentException("Acesso negado!");
    }
    return auditoria;
  }

  public List<AuditoriaEntity> findHistorico(PageRequest pageRequest) {
    Page<AuditoriaEntity> page = securityConfig.isCurrentUserAdmin() ? auditoriaRepository.findAll(pageRequest) :
        auditoriaRepository.findHistorico(securityConfig.getCurrentUser(), pageRequest);
    return page.getContent();
  }

}
