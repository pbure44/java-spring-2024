package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


//@Controller+@ResponseBody = @RestController
@RestController
public class  BasicController {


    @GetMapping(value = "/hello", produces = "text/plain")
    public String hello(){
        return "Hello from basic controller";
    }
}
