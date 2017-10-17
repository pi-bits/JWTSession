package com.session.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(value="com.session.jwt.filter")
public class JWTApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(JWTApplication.class,args);
    }
}
