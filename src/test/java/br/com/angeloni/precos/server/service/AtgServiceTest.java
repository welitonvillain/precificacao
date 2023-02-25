package br.com.angeloni.precos.server.service;

import br.com.angeloni.ws.PriceInfo;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AtgServiceTest {

    @Autowired
    private AtgService atgService;

    @Test
    @Ignore
    public void testGetPrice() {
        PriceInfo priceInfo = atgService.getPrice(4034624L, "plist3020082");
        assertThat(priceInfo.isComplexCampaign()).isTrue();
    }

    @Test(expected = Exception.class)
    @Ignore
    public void testGetPriceErro() {
        atgService.getPrice(999999999L, "plist3020082");
    }
}
