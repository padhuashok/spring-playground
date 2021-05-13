package com.cognizant.springplayground;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MathService.class)
public class MathServiceTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void testCalculator() throws Exception {
        //test case for no / mandatory query string
        RequestBuilder rq = MockMvcRequestBuilders.get("/math/calculate").
                queryParam("y", "4").
                queryParam("operation", "add").
                accept(MediaType.TEXT_PLAIN);
        this.mvc.perform(rq).andExpect(status().isBadRequest());

        //test case for empty values in query string
        rq = MockMvcRequestBuilders.get("/math/calculate").
                queryParam("x", "").
                queryParam("y", "4").
                queryParam("operation", "add").
                accept(MediaType.TEXT_PLAIN);
        this.mvc.perform(rq).andExpect(status().isBadRequest());

        //test case for add
        rq = MockMvcRequestBuilders.get("/math/calculate").
                queryParam("x", "3").
                queryParam("y", "4").
                queryParam("operation", "add").
                accept(MediaType.TEXT_PLAIN);
        this.mvc.perform(rq).andExpect(status().isOk()).andExpect(content().string("3 + 4 = 7"));

        //test case for subtract
        rq = MockMvcRequestBuilders.get("/math/calculate").
                queryParam("x", "10").
                queryParam("y", "5").
                queryParam("operation", "subtract").
                accept(MediaType.TEXT_PLAIN);
        this.mvc.perform(rq).andExpect(status().isOk()).andExpect(content().string("10 - 5 = 5"));

        //test case for multiply
        rq = MockMvcRequestBuilders.get("/math/calculate").
                queryParam("x", "7").
                queryParam("y", "7").
                queryParam("operation", "multiply").
                accept(MediaType.TEXT_PLAIN);
        this.mvc.perform(rq).andExpect(status().isOk()).andExpect(content().string("7 * 7 = 49"));

        //test case for divide
        rq = MockMvcRequestBuilders.get("/math/calculate").
                queryParam("x", "91").
                queryParam("y", "7").
                queryParam("operation", "divide").
                accept(MediaType.TEXT_PLAIN);
        this.mvc.perform(rq).andExpect(status().isOk()).andExpect(content().string("91 / 7 = 13"));

        //without operation query string param
        rq = MockMvcRequestBuilders.get("/math/calculate").
                queryParam("x", "10").
                queryParam("y", "5").
                accept(MediaType.TEXT_PLAIN);
        this.mvc.perform(rq).andExpect(status().isOk()).andExpect(content().string("10 + 5 = 15"));
    }

    @Test
    public void testSumOfNumbers() throws Exception {
        RequestBuilder rq = MockMvcRequestBuilders.post("/math/sum").
                queryParam("n", "1", "2", "3", "4").
                accept(MediaType.TEXT_PLAIN);

        this.mvc.perform(rq).andExpect(status().isOk()).andExpect(content().string("1 + 2 + 3 + 4 = 10"));

    }

    @Test
    public void testSingleNumbers() throws Exception {
        RequestBuilder rq = MockMvcRequestBuilders.post("/math/sum").
                accept(MediaType.TEXT_PLAIN).
                queryParam("n", "1");

        this.mvc.perform(rq).andExpect(status().isOk()).andExpect(content().string("1 = 1"));
    }

    @Test
    public void testEmptyNumbers() throws Exception {
        RequestBuilder rq = MockMvcRequestBuilders.post("/math/sum").
                accept(MediaType.TEXT_PLAIN);

        this.mvc.perform(rq).
                andExpect(status().isOk()).andExpect(content().string("No values found"));
    }
}
