package com.session.jwt.service;

import com.session.jwt.JWTApplication;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JWTApplication.class)
public class JwtServiceTest {

    @Autowired
    JwtService jwtService;


    @Test
    public void contextLoads() throws Exception {

    }

    @Test
    public void loadsValueProperty() throws Exception {
        Assert.assertThat(jwtService.getExpireHours(), Is.is(CoreMatchers.equalTo(24l)));
        Assert.assertThat(jwtService.getPlainSecret(), Is.is(CoreMatchers.equalTo("something-secret-you-cannot-keep-it")));
    }

    @Test
    public void loadsEncodedSecret() throws Exception {
        Assert.assertThat(jwtService.getEncodedSecret(), Is.is(CoreMatchers.equalTo("c29tZXRoaW5nLXNlY3JldC15b3UtY2Fubm90LWtlZXAtaXQ=")));
    }
}