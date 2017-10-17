package com.session.jwt.controller;

import com.session.jwt.model.AuthDTO;
import com.session.jwt.model.JwtUser;
import com.session.jwt.service.JwtService;
import com.session.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class JWTController {

    @Autowired
    UserService userService;
    @Autowired
     JwtService jwtService;

    @GetMapping(path = "/api/public/hello/{name}")
    public ResponseEntity<?> helloPublic(@PathVariable String name) {
        String result = String.format("Hello JWT , %s ! (Public)" , name);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/api/secure/hello/{name}")
    public ResponseEntity<?> helloSecured(@PathVariable String name) {
        String result = String.format("Hello JWT , %s ! (Secured)" , name);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/api/public/auth")
    public ResponseEntity<?> auth(@RequestBody AuthDTO auth) {
        String userName = auth.getUserName();
        String passWord = auth.getPassWord();

        Boolean correctCredentials = userService.authenticate(userName, passWord);
        if (correctCredentials) {
            JwtUser jwtUser = new JwtUser(userName, passWord);
            return ResponseEntity.ok(jwtService.getToken(jwtUser));
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
