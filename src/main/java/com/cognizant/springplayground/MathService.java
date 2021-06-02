package com.cognizant.springplayground;

import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class MathService {

    @RequestMapping(value = "/math/calculate",method = GET)
    public String calculate(@RequestParam int x, @RequestParam int y, @RequestParam(required = false,defaultValue = "add") String operation){
        String response = "";
        switch(operation){
            case "subtract" : {
                response = "" + x + " - " + y + " = " + (x - y);
                break;
            }
            case "multiply" : {
                response = "" + x + " * " + y + " = " + (x * y);
                break;
            }
            case "divide" : {
                response = "" + x + " / " + y + " = " + (x / y);
                break;
            }
            case "add" :
            default : {
                response = "" + x + " + " + y + " = " + (x + y);
                break;
            }
        }
        return response;
    }

    @RequestMapping(value = "/math/sum" ,method = POST)
    public String sum(@RequestParam MultiValueMap<String,String> numbers) throws NumberFormatException{
        String response = "";
        int sum = 0;
        List<String> numList = numbers.get("n");
        if(numList == null){
            return response = "No values found";
        }
        for (int i = 0; i < numList.size() ; i++) {
            if(numList.get(i) != "")
            sum += Integer.parseInt(numList.get(i));
            if (i == 0)
                response = numList.get(i);
            else
            response += " + " + numList.get(i);
        }
        response += " = " + sum;
        return response;
        }

        @RequestMapping(value = "/math/volume/{length}/{width}/{height}",method = {GET,POST,DELETE,PUT,PATCH})
        public int volume(@PathVariable int length,@PathVariable int width,@PathVariable int height){
            return length * width * height;
        }

}
