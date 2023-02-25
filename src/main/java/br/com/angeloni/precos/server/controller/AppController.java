package br.com.angeloni.precos.server.controller;

import br.com.angeloni.precos.server.data.AuditoriaEntity;
import br.com.angeloni.precos.server.service.AuditoriaService;
import br.com.angeloni.precos.server.service.PrecificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class AppController {

  private static final int HISTORY_SIZE = 100;

  @Autowired
  private AuditoriaService auditoriaService;

  @Autowired
  private PrecificacaoService precificacaoService;

  @GetMapping(value = "/login")
  public String login() {
    return "login";
  }

  @GetMapping(value = "/")
  public String index() {
    return "index";
  }

  @GetMapping(value = "/history")
  public String history(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page, Model model) {
    Sort sort = Sort.by(Sort.Direction.DESC, "dataHoraInicio");
    PageRequest pageRequest = PageRequest.of(page, HISTORY_SIZE, sort);
    List<AuditoriaEntity> lista = auditoriaService.findHistorico(pageRequest);
    model.addAttribute("lista", lista);
    return "history";
  }

  @GetMapping(value = "/result")
  public String result(@RequestParam("id") String stringId, Model model) {
    AuditoriaEntity auditoria = auditoriaService.findProcessamento(AuditoriaEntity.fromStringId(stringId));

    model.addAttribute("auditoria", auditoria);
    return "result";
  }

  @GetMapping(value = "/download")
  public HttpEntity<byte[]> download(@RequestParam("id") String stringId) {
    AuditoriaEntity auditoria = auditoriaService.findProcessamento(AuditoriaEntity.fromStringId(stringId));

    HttpHeaders header = new HttpHeaders();
    header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + auditoria.getNomeArquivo());
    header.setContentLength(auditoria.getConteudoArquivo().length);
    return new HttpEntity<>(auditoria.getConteudoArquivo(), header);
  }

  @PostMapping(value = "/upload")
  public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request, RedirectAttributes redirect)
      throws IOException {
    String ip = Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
    if (ip.equals("0:0:0:0:0:0:0:1")) {
      ip = "127.0.0.1";
    }
    String nomeArquivo = file.getOriginalFilename();
    byte[] conteudoArquivo = file.getBytes();

    AuditoriaEntity auditoria = precificacaoService.processaArquivo(ip, nomeArquivo, conteudoArquivo);

    redirect.addAttribute("id", auditoria.getStringId());
    return "redirect:result";
  }

}
