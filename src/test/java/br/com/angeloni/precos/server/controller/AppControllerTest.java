package br.com.angeloni.precos.server.controller;

import br.com.angeloni.precos.server.data.AuditoriaEntity;
import br.com.angeloni.precos.server.service.AuditoriaService;
import br.com.angeloni.precos.server.service.PrecificacaoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AppController.class)
@ActiveProfiles("test")
public class AppControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AuditoriaService auditoriaService;

  @MockBean
  private PrecificacaoService precificacaoService;

  @Test
  public void login() throws Exception {
    mockMvc.perform(get("/login"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(view().name("login"))
      .andExpect(content().string(containsString("<form action=\"/login\" method=\"post\">")));
  }

  @Test
  public void index() throws Exception {
    mockMvc.perform(get("/"))
      .andDo(print())
      .andExpect(status().is3xxRedirection());
  }

  @Test
  public void history() throws Exception {
    List<AuditoriaEntity> historico = new ArrayList<>();
    when(auditoriaService.findHistorico(any(PageRequest.class))).thenReturn(historico);

    mockMvc.perform(get("/history"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(view().name("history"))
      .andExpect(model().attributeExists("lista"));
  }

  @Test
  public void result() throws Exception {
    UUID id = UUID.randomUUID();
    AuditoriaEntity auditoria = new AuditoriaEntity();
    auditoria.setId(id);
    when(auditoriaService.findProcessamento(eq(id))).thenReturn(auditoria);

    mockMvc.perform(get("/result?id="+auditoria.getStringId()))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(view().name("result"))
      .andExpect(model().attributeExists("auditoria"));
  }

  @Test
  public void download() throws Exception {
    UUID id = UUID.randomUUID();
    String conteudo = "test";
    String arquivo = "test.csv";
    AuditoriaEntity auditoria = new AuditoriaEntity();
    auditoria.setId(id);
    auditoria.setConteudoArquivo(conteudo.getBytes());
    auditoria.setNomeArquivo(arquivo);
    when(auditoriaService.findProcessamento(eq(id))).thenReturn(auditoria);

    mockMvc.perform(get("/download?id="+auditoria.getStringId()))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(header().string("Content-Type", "application/octet-stream"))
      .andExpect(header().string("Content-Disposition", "attachment; filename=" + arquivo))
      .andExpect(header().longValue("Content-Length", conteudo.length()))
      .andExpect(content().bytes(conteudo.getBytes()));
  }

  @Test
  public void upload() throws Exception {
    UUID id = UUID.randomUUID();
    String ip = "127.0.0.1";
    String arquivo = "test.csv";
    String conteudo = "test";
    AuditoriaEntity auditoria = new AuditoriaEntity();
    auditoria.setId(id);
    auditoria.setIp(ip);
    auditoria.setNomeArquivo(arquivo);
    auditoria.setConteudoArquivo(conteudo.getBytes());
    when(precificacaoService.processaArquivo(eq(ip), eq(arquivo), eq(conteudo.getBytes()))).thenReturn(auditoria);

    MockMultipartFile file = new MockMultipartFile("file", arquivo, "text/csv", conteudo.getBytes());

    mockMvc.perform(multipart("/upload").file(file))
      .andDo(print())
      .andExpect(status().is3xxRedirection())
      .andExpect(redirectedUrl("result?id="+auditoria.getStringId()));
  }
}