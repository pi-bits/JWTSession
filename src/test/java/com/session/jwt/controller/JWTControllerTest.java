package com.session.jwt.controller;

import com.session.jwt.JWTApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = JWTApplication.class)
public class JWTControllerTest {

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
    public void testHelloPublic() throws Exception {
        mockMvc.perform(get("/api/public/hello/prashant"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testHelloSecuredWhenNoAuth() throws Exception {
        mockMvc.perform(get("/api/secure/hello/prashant"))
                .andExpect(status().is(401));
    }

    @Test
    public void testHelloSecured() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-auth-token","eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJlMTA4NzcxNC1lMmNkLTQ5MTQtOTdiZi1hZTVhNGYzNGQ3OWIiLCJzdWIiOiJ1c2VyMSIsInJvbGUiOiJhZG1pbiIsImlhdCI6MTUwODU4NjI2MywiZXhwIjoxNTA4NjcyNjYzfQ.4nGNCU2EFu5afzP9GGpYbYIIUTwe6XQU-tNdTsFJmY8bcKIV3uNQ4OODzHlR5f0m8ajsAKIDp3xZpJv-w8MwoQ");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/secure/hello/prashant").headers(headers);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldCreateAuth() throws Exception {
        String json = "{\n" +
                "\"userName\" : \"user1\",\n" +
                "\"role\" : \"admin\",\n" +
                "\"passWord\" : \"123\"\n" +
                "}\n";
        MvcResult mvcResult = mockMvc.perform(post("/api/public/auth").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful()).andReturn();
        Assert.assertNotNull(mvcResult.getResponse().getContentAsString());

    }


}