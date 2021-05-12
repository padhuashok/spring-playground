package com.cognizant.springplayground;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
public class HelloControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void testGreeting() throws Exception {

        //Request
        RequestBuilder rq = MockMvcRequestBuilders.get("/hello").accept(MediaType.TEXT_PLAIN);

        this.mvc.perform(rq).
                andExpect(status().isOk()).
                andExpect(content().string("Hello World"));

    }
    @Test
    public void testPIValue() throws Exception {
        RequestBuilder rq = MockMvcRequestBuilders.get("/math/pi").accept(MediaType.TEXT_PLAIN);
        this.mvc.perform(rq).
                andExpect(status().isOk()).
                andExpect(content().string("3.141592653589793"));
    }
}

