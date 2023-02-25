package br.com.angeloni.precos.server.data;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AuditoriaEntityRepositoryTest {

    @Autowired
    private CodigoListaRepository codigoListaRepository;

    @Autowired
    private AuditoriaRepository repository;

    @Test
    public void testSave() {
        LocalDateTime dataHora = LocalDateTime.now();
        String usuario = "teste";
        String ip = "127.0.0.1";
        String nomeArquivo = "teste.csv";

        String resultado = "Resultado do processamento do arquivo \"" + nomeArquivo + "\"\n";
        resultado += "Data: "+dataHora+"\n";
        resultado += "Usuário: "+usuario+"\n";
        resultado += "IP: "+ ip+ "\n";
        resultado += "Sucesso!\n";

        String codigoLista = codigoListaRepository.findLista("Divvino", "Preco DE", 53L)
                .map(CodigoListaEntity::getCodigo).orElse(null);
        resultado += "Código da Lista: " + codigoLista+"\n";

        AuditoriaEntity auditoria = new AuditoriaEntity();
        auditoria.setDataHoraInicio(dataHora);
        auditoria.setUsuario(usuario);
        auditoria.setIp(ip);
        auditoria.setNomeArquivo(nomeArquivo);
        auditoria.setConteudoArquivo("teste".getBytes(StandardCharsets.UTF_8));
        AuditoriaEntity auditoriaMerged = repository.saveAndFlush(auditoria);

        auditoriaMerged.setDataHoraFim(LocalDateTime.now());
        auditoriaMerged.setResultado(resultado);
        repository.saveAndFlush(auditoriaMerged);

        AuditoriaEntity auditoriaFind = repository.findById(auditoriaMerged.getId())
                .orElseThrow(EntityNotFoundException::new);
        Assert.assertEquals(auditoriaMerged.getUsuario(), auditoriaFind.getUsuario());
        Assert.assertEquals(auditoriaMerged.getIp(), auditoriaFind.getIp());
        Assert.assertEquals(auditoriaMerged.getDataHoraInicio(), auditoriaFind.getDataHoraInicio());
        Assert.assertEquals(auditoriaMerged.getDataHoraFim(), auditoriaFind.getDataHoraFim());
        Assert.assertEquals(auditoriaMerged.getNomeArquivo(), auditoriaFind.getNomeArquivo());
        Assert.assertEquals(auditoriaMerged.getResultado(), auditoriaFind.getResultado());
        Assert.assertArrayEquals(auditoriaMerged.getConteudoArquivo(), auditoriaFind.getConteudoArquivo());
    }

}
