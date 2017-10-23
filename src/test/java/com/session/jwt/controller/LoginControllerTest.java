package com.session.jwt.controller;

import com.session.jwt.JWTApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JWTApplication.class)
public class LoginControllerTest {


    @Autowired
    WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    public void contextLoads() throws Exception {

    }

    @Test
    public void shouldGetLoginPageWithoutAuth() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldGetHomePageWhenILoginPageWithAuth() throws Exception {
        String json = "{\n" +
                "\"userName\" : \"user1\",\n" +
                "\"role\" : \"admin\",\n" +
                "\"passWord\" : \"123\"\n" +
                "}\n";


        mockMvc.perform(
                post("/login").contentType(
                        MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());


    }

    @Test
    public void shouldNotLoginWithBadCredentials() throws Exception {
        String json = "{\n" +
                "\"userName\" : \"user1\",\n" +
                "\"role\" : \"admin\",\n" +
                "\"passWord\" : \"1234\"\n" +
                "}\n";


        mockMvc.perform(
                post("/login").contentType(
                        MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());


    }
}