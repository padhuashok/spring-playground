package com.cognizant.springplayground;

import com.fasterxml.jackson.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/flights")
public class FlightServiceJson {

    @RequestMapping(value = "/flight",method = GET)
    public Flight getCurrentFlight(){
         Person passenger = new Person();
         passenger.setFirstName("Some name");
         passenger.setLastName("Some other name");
         Ticket ticket = new Ticket();
         ticket.setPrice(200);
         ticket.setPassenger(passenger);
         Flight currentflight = new Flight();
         currentflight.departDate = Date.from(Instant.parse("2017-04-21T14:34:00.00Z"));
         currentflight.tickets = Arrays.asList(ticket);
         return currentflight;
    }

    @RequestMapping(value = "", method = GET)
    public List<Flight> getListOfFlights(){
        Person p1 = new Person();
        p1.setFirstName("Some name");
        p1.setLastName("Some other name");

        Ticket t1 = new Ticket();
        t1.setPrice(200);
        t1.setPassenger(p1);

        Flight f1 = new Flight();
        f1.setTickets(Arrays.asList(t1));
        Calendar cal = Calendar.getInstance();
        cal.set(2017,Calendar.APRIL,21,14,34);
        f1.setDepartDate(cal.getTime());

        Flight f2 = new Flight();
        p1 = new Person();
        p1.setFirstName("Some name");
        p1.setLastName(null);
        t1 = new Ticket();
        t1.setPrice(400);
        t1.setPassenger(p1);
        f2.setDepartDate(cal.getTime());
        f2.setTickets(Arrays.asList(t1));
        return Arrays.asList(f1,f2);
    }

    static class Flight{
        public Date getDepartDate() {
            return departDate;
        }

        @JsonProperty("Departs")
        public void setDepartDate(Date departDate) {
            this.departDate = departDate;
        }

        @JsonGetter("Tickets")
        public List<Ticket> getTickets() {
            return tickets;
        }

        public void setTickets(List<Ticket> tickets) {
            this.tickets = tickets;
        }

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        Date departDate;

        List<Ticket> tickets = new ArrayList<>();
    }

    static class Ticket{
        @JsonGetter("Passenger")
        public Person getPassenger() {
            return passenger;
        }

        public void setPassenger(Person passenger) {
            this.passenger = passenger;
        }

        @JsonGetter("Price")
        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }


        Person passenger = new Person();

        int price;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    static  class Person{
        @JsonGetter("FirstName")
        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
        @JsonGetter("LastName")
        public String getLastName() {
            return lastName;
        }


        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        String firstName;

        String lastName;
    }
}
