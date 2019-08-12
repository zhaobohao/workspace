package com.spring.web.test.mocktest;

import com.spring.web.test.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

@AutoConfigureMockMvc
public class MockMvcExampleTests  extends BaseTest {
    @Autowired
    private MockMvc mvc;
    @Resource
    WebApplicationContext context;
//    @BeforeMethod
//    public void setup() {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }
    @Test
    public void exampleTest() throws Exception {
        this.mvc.perform(get("/hello")).andExpect(status().isOk())
                .andExpect(content().string("zhaobo"))
                .andDo(MockMvcResultHandlers.print());
    }
}
