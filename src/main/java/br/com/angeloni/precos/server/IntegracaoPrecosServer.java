package br.com.angeloni.precos.server;

import br.com.angeloni.precos.server.config.SSLConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"br.com.angeloni.precos.server"})
public class IntegracaoPrecosServer {

    public static void main(final String[] args) {
        SSLConfig.execute();
        SpringApplication. run(IntegracaoPrecosServer.class, args);
    }

}
