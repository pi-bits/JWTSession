package com.session.jwt.security;

import com.session.jwt.filter.JwtFilter;
import com.session.jwt.filter.LoginAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

@Autowired
JwtFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(loginProvider());
    }

    @Bean
    LoginAuthenticationEntryPoint entryPoint() {
        return new LoginAuthenticationEntryPoint();
    }

    @Bean
    public LoginProvider loginProvider() {
        return new LoginProvider();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter(loginSuccessHandler(), authenticationManager());
        http.csrf().disable();

        http.authorizeRequests().antMatchers("**/rest/**","**/api/secure/**").authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll().and()
                .exceptionHandling().authenticationEntryPoint(entryPoint())
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(loginAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
/**
 *  .formLogin()
 .loginPage("/login")
 .permitAll().and()
 */
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/public/**","/login");
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }
}
