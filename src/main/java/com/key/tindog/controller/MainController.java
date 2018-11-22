package com.key.tindog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping(name = "/")
    public String helloWorld() {

        return "hello world!";
    }

}
