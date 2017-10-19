package com.session.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ServletComponentScan(value="com.session.jwt")
@EnableWebMvc
@EnableAutoConfiguration
public class JWTApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(JWTApplication.class,args);
    }
}
