package br.com.angeloni.precos.server.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.UrlTemplateResolver;

@Configuration
public class TemplateEngineConfig {

  @Bean
  public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.addTemplateResolver(new UrlTemplateResolver());
    templateEngine.addTemplateResolver(templateResolver);
    templateEngine.addDialect(new SpringSecurityDialect());
    templateEngine.addDialect(new LayoutDialect());
    templateEngine.addDialect(new Java8TimeDialect());
    return templateEngine;
  }

}
