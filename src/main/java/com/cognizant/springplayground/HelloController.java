package com.cognizant.springplayground;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String welcomeMessage(){
        return "Hello World";
    }

    @GetMapping("/math/pi")
    public String getMathPIValue(){
        return "3.141592653589793";
    }
}
