package com.nasa.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HomeController {
    @RequestMapping("/")
    public String home(){
        return "Hello, NASA SpaceShip!";
    }
}