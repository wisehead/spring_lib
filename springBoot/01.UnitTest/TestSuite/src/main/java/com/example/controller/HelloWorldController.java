package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public String index() {
        System.out.println("xxxxxxxxxxxxxxxxxxxxxx");
        return "Hello World";
    }
}