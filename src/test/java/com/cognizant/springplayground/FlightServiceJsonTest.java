package com.cognizant.springplayground;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.coyote.Request;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(com.cognizant.springplayground.FlightServiceJson.class)
public class FlightServiceJsonTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void testGetFlight() throws  Exception {
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


    @Test
    public void testCalculateTicketTotal() throws Exception {
        //input as string
        RequestBuilder rq = post("/flights/tickets/total").
                contentType(MediaType.APPLICATION_JSON).
                content("{\n" +
                        "  \"Tickets\": [\n" +
                        "    {\n" +
                        "      \"Passenger\": {\n" +
                        "        \"FirstName\": \"Some name\",\n" +
                        "        \"LastName\": \"Some other name\"\n" +
                        "      },\n" +
                        "      \"Price\": 200\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"Passenger\": {\n" +
                        "        \"FirstName\": \"Name B\",\n" +
                        "        \"LastName\": \"Name C\"\n" +
                        "      },\n" +
                        "      \"Price\": 150\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}");
        this.mvc.perform(rq).andExpect(jsonPath("$.result",is(350)));
    }
    @Test
    public void testCalculateTotalAsJackSon() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode t1 = mapper.createObjectNode();
        ObjectNode p1 = mapper.createObjectNode();
        p1.put("FirstName","Some name");
        p1.put("LastName","Some other name");
        t1.set("Passenger",p1);
        t1.put("Price",200);
        ObjectNode t2 = mapper.createObjectNode();
        ObjectNode p2 = mapper.createObjectNode();
        //p2.putPOJO()
        p2.put("FirstName","Name B");
        p2.put("LastName","Name C");
        t2.set("Passenger",p2);
        t2.put("Price",150);
        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode.addAll(Arrays.asList(t1,t2));
        ObjectNode f1 = mapper.createObjectNode();
        f1.set("Tickets",arrayNode);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(f1);
        // print json
        System.out.println(json);

        RequestBuilder rq = post("/flights/tickets/total").
                contentType(MediaType.APPLICATION_JSON).content(json);

        this.mvc.perform(rq).andExpect(status().isOk()).andExpect(jsonPath("$.result",is(350)));
    }

    @Test
    public void testJsonInputAsFile() throws Exception {
        String json = getJSON("/flights.json");
        RequestBuilder rq = post("/flights/tickets/total").
                contentType(MediaType.APPLICATION_JSON).
                content(json);
        this.mvc.perform(rq)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result",is(350)));
    }
    private String getJSON(String path) throws Exception {
        return new String (this.getClass().getResourceAsStream(path).readAllBytes());
    }
}
