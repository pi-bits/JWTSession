package com.session.jwt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.util.Collections;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    LoginAuthenticationEntryPoint entryPoint()
    {
        return new LoginAuthenticationEntryPoint();
    }

    @Bean
    public LoginProvider loginProvider() {
        return new LoginProvider();
    }

    @Bean
    public AuthenticationManager authenticationManager(final LoginProvider loginProvider) {
        return new ProviderManager(Collections.singletonList(loginProvider));
    }

    @Bean
    public LoginAuthenticationFilter authenticationFilter(final AuthenticationManager authenticationManager) {
        LoginAuthenticationFilter authenticationFilter = new LoginAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManager);
        authenticationFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        return authenticationFilter;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("**/rest/**").authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll().and()
                .exceptionHandling().authenticationEntryPoint(entryPoint())
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
/**
 * .formLogin()
 * .loginPage("/rest/login")
 * .permitAll().and().
 **/
