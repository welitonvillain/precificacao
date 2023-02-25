package br.com.angeloni.precos.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

import java.util.List;

@Configuration
public class LDAPSecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${ldap.url}")
  private String ldapUrl;

  @Value("${ldap.base-dn}")
  private String ldapBaseDn;

  @Value("${ldap.username}")
  private String ldapSecurityPrincipal;

  @Value("${ldap.password}")
  private String ldapPrincipalPassword;

  @Value("${ldap.user-filter}")
  private String ldapUserFilter;

  @Value("#{'${ldap.admins}'.split(',')}")
  private List<String> admins;

  @Bean
  public LdapContextSource ldapContextSource() {
    LdapContextSource contextSource = new LdapContextSource();
    contextSource.setUrl(ldapUrl);
    contextSource.setBase(ldapBaseDn);
    contextSource.setUserDn(ldapSecurityPrincipal);
    contextSource.setPassword(ldapPrincipalPassword);
    contextSource.afterPropertiesSet();
    return contextSource;
  }

  @Bean
  public LdapAuthoritiesPopulator ldapAuthoritiesPopulator() {
    DefaultLdapAuthoritiesPopulator populator = new DefaultLdapAuthoritiesPopulator(ldapContextSource(), null);
    populator.setIgnorePartialResultException(true);
    return populator;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
          .antMatchers("/login**").anonymous()
          .antMatchers("/css/**").permitAll()
          .antMatchers("/img/**").permitAll()
          .antMatchers("/js/**").permitAll()
          .antMatchers("/webjars/**").permitAll()
          .antMatchers("/").authenticated()
        .and()
          .formLogin().loginPage("/login")
        .and()
          .logout()
        .and()
          .csrf().disable();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .ldapAuthentication()
        .contextSource(ldapContextSource())
        .userSearchFilter(ldapUserFilter)
        .ldapAuthoritiesPopulator(ldapAuthoritiesPopulator());
  }

  public String getCurrentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return auth != null ? auth.getName() : null;
  }

  public boolean isCurrentUserAdmin() {
    return admins.contains(getCurrentUser());
  }
}
