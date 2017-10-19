package com.session.jwt.security;

import com.session.jwt.model.JwtUser;
import com.session.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginJwtValidator {

    @Autowired
    JwtService jwtService;

    public JwtUser validate(String token) {

       return jwtService.getUser(token);

    }
}
