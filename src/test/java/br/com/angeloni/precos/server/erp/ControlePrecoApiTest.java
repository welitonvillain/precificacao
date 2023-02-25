package br.com.angeloni.precos.server.erp;

import br.com.angeloni.precos.server.erp.api.ErpProperties;
import br.com.angeloni.precos.server.erp.api.ControlePrecoApi;
import br.com.angeloni.precos.server.erp.model.ControlePreco;
import br.com.angeloni.precos.server.erp.model.ControlePrecoItem;
import br.com.angeloni.precos.server.erp.model.RetornoControlePreco;
import okhttp3.mockwebserver.MockResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ControlePrecoApiTest {

    @Autowired
    private ErpProperties erpProperties;

    @Autowired
    private ControlePrecoApi controlePrecoApi;

    private ErpServerMock mockServer = new ErpServerMock();

    @Before
    public void setUp() {
        mockServer.setRequestCallback(r -> {
            try {
                if (r.getMethod().equals("POST") && r.getPath().contains(erpProperties.getPrecosUri())) {
                    File mock = new File("src/test/resources/mock/controlePrecoRetorno.json");
                    return new MockResponse()
                            .setResponseCode(200)
                            .addHeader("Content-Type", "application/json")
                            .setBody(new String(Files.readAllBytes(mock.toPath()), StandardCharsets.UTF_8));
                }

                return new MockResponse()
                        .setResponseCode(404)
                        .setBody("Mock not found: " + r.getMethod() + " " + r.getPath());
            } catch (Exception ex) {
                return new MockResponse()
                        .setResponseCode(500)
                        .setBody(Optional.ofNullable(ex.getMessage()).orElse("null"));
            }
        });

        erpProperties.setBaseUrl(mockServer.getServerUrl());
    }

    @Test
    public void test_post() {
        ControlePreco controlePreco = ControlePreco.builder()
            .idUnidade("8")
            .dataInicio(LocalDateTime.of(2020, 2, 3, 10, 40, 0).toInstant(ZoneOffset.UTC))
            .dataFim(LocalDateTime.of(2020, 2, 28, 10, 40, 0).toInstant(ZoneOffset.UTC))
            .item(ControlePrecoItem.builder()
                .idProduto("4396919")
                .quantidadeDisponivel(BigDecimal.valueOf(200))
                .build())
            .build();
        RetornoControlePreco retorno = controlePrecoApi.controlePrecoV01Post(controlePreco)
                .getDsStatus()
                .getRetorno()
                .get(0);
        Assert.assertTrue(retorno.isSucesso());
        Assert.assertEquals("Controle de precos criado com sucesso.", retorno.getDescricao());
    }

}