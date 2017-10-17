package com.session.jwt.filter;

import com.session.jwt.model.JwtUser;
import com.session.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = { "/api/secure/*" })
public class JwtFilter implements Filter {

    @Autowired
    private JwtService jwtTokenService;

    @Value("${jwt.auth.header}")
    String authHeader;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        final HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        final String authHeaderVal = httpRequest.getHeader(authHeader);

        if (null==authHeaderVal)
        {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try
        {
            JwtUser jwtUser = jwtTokenService.getUser(authHeaderVal);
            httpRequest.setAttribute("jwtUser", jwtUser);
        }
        catch(Exception e)
        {
            httpResponse.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return;
        }

        filterChain.doFilter(httpRequest, httpResponse);
    }

    @Override
    public void destroy() {

    }
}
