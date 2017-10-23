package com.session.jwt;

import com.session.jwt.exception.InvalidTokenException;
import com.session.jwt.model.JwtUser;
import com.session.jwt.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JWTApplication.class})
public class SecuredResourcesTest {


    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    JwtService jwtService;
    protected MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    public void contextLoads() throws Exception {

    }

    @Test(expected = InvalidTokenException.class)
    public void testSecuredResourceWithNoAuth() throws Exception {
        mockMvc.perform(get("/rest/home"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());

    }


    @Test(expected = ExpiredJwtException.class)
    public void testSecuredResourceWithExpiredAuth() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-auth-token", "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJlMTA4NzcxNC1lMmNkLTQ5MTQtOTdiZi1hZTVhNGYzNGQ3OWIiLCJzdWIiOiJ1c2VyMSIsInJvbGUiOiJhZG1pbiIsImlhdCI6MTUwODU4NjI2MywiZXhwIjoxNTA4NjcyNjYzfQ.4nGNCU2EFu5afzP9GGpYbYIIUTwe6XQU-tNdTsFJmY8bcKIV3uNQ4OODzHlR5f0m8ajsAKIDp3xZpJv-w8MwoQ");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rest/home").headers(headers);

        mockMvc.perform(requestBuilder);
    }

    @Test
    public void testSecuredResourceWithvalidAuth() throws Exception {
        String validToken = jwtService.getToken(new JwtUser("user1", "123", "admin"));

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-auth-token", validToken);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rest/home").headers(headers);

        mockMvc.perform(requestBuilder).
                andExpect(MockMvcResultMatchers.status().isOk());

    }

}