package com.session.jwt.filter;

import com.session.jwt.exception.InvalidTokenException;
import com.session.jwt.security.LoginAuthenticationToken;
import com.session.jwt.security.LoginSuccessHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    public LoginAuthenticationFilter(LoginSuccessHandler loginSuccessHandler, AuthenticationManager authenticationManager) {
        super("/rest/**");
        super.setAuthenticationSuccessHandler(loginSuccessHandler);
        setAuthenticationManager(authenticationManager);

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String header = httpServletRequest.getHeader("x-auth-token");
        if (header==null)
        {
          throw new InvalidTokenException("Token Missing");
        }else {
            LoginAuthenticationToken loginAuthenticationToken = new LoginAuthenticationToken(header);
           return  getAuthenticationManager().authenticate(loginAuthenticationToken);
        }


    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request,response);
    }

    @Override
    public void setSessionAuthenticationStrategy(SessionAuthenticationStrategy sessionStrategy) {
        super.setSessionAuthenticationStrategy(sessionStrategy);
    }


}
