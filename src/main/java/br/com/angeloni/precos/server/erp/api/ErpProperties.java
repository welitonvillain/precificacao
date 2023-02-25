package br.com.angeloni.precos.server.erp.api;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ToString
@Configuration
@ConfigurationProperties(prefix = "erp.api")
public class ErpProperties {

    private String baseUrl;
    private String precosUri;

}