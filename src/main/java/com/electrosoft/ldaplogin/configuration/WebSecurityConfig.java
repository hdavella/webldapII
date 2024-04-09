package com.electrosoft.ldaplogin.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.cors().and().authorizeRequests().antMatchers("/hello").hasAnyAuthority("bla")
            .anyRequest().fullyAuthenticated().and().httpBasic().and().csrf().disable().formLogin().successHandler(new MyAuthenticationSuccessHandler());
    }

    @Bean
    public AuthenticationProvider activeDirectoryLdapAuthenticationProvider() {

        ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider
                = new ActiveDirectoryLdapAuthenticationProvider("grupobna.local", "ldap://172.21.45.70/");

        activeDirectoryLdapAuthenticationProvider.setSearchFilter("mailNickname={1}");
        activeDirectoryLdapAuthenticationProvider.setConvertSubErrorCodesToExceptions(true);
        activeDirectoryLdapAuthenticationProvider.setUseAuthenticationRequestCredentials(true);
        
        return activeDirectoryLdapAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .authenticationProvider(activeDirectoryLdapAuthenticationProvider());
    }
}
