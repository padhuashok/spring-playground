package com.cognizant.springplayground;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(com.cognizant.springplayground.FlightServiceJson.class)
public class FlightServiceJsonTest {

    @Autowired
    MockMvc mvc;

    @Test
    public  void testGetFlight() throws  Exception {
      this.mvc.perform(MockMvcRequestBuilders.get("/flights/flight").
              accept("application/json")).
              andExpect(status().isOk()).
             /* andExpect(jsonPath("$.departs",is("2017-04-21 14:34"))).
              andExpect(jsonPath("$.tickets[0].passenger.firstName",is("Some name"))).
              andExpect(jsonPath("$.tickets[0].passenger.lastName",is("Some other name"))).
              andExpect(jsonPath("$.tickets[0].price",is(200)));*/

                andExpect(jsonPath("$.Departs",is("2017-04-21 14:34"))).
                andExpect(jsonPath("$.Tickets[0].Passenger.FirstName",is("Some name"))).
                andExpect(jsonPath("$.Tickets[0].Passenger.LastName",is("Some other name"))).
                andExpect(jsonPath("$.Tickets[0].Price",is(200)));
    }

    @Test
    public void testGetFlights() throws Exception{
        this.mvc.perform(MockMvcRequestBuilders.get("/flights").accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.[0].Departs",is("2017-04-21 18:34"))).
                andExpect(jsonPath("$.[0].Tickets[0].Passenger.FirstName",is("Some name"))).
                andExpect(jsonPath("$.[0].Tickets[0].Passenger.LastName",is("Some other name"))).
                andExpect(jsonPath("$.[0].Tickets[0].Price",is(200))).
                andExpect(jsonPath("$.[1].Departs",is("2017-04-21 18:34"))).
                andExpect(jsonPath("$.[1].Tickets[0].Passenger.FirstName",is("Some name"))).
                //andExpect(jsonPath("$.[1].tickets[0].passenger.lastName",is(nullValue()))).
                andExpect(jsonPath("$.[1].Tickets[0].Price",is(400)));
    }
}
