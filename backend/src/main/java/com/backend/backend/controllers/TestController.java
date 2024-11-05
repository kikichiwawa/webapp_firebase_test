package com.backend.backend.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api-test")
public class TestController {

    @RequestMapping("/{id}")
    public String hello(@PathVariable("id") String id){
        return "hello " + id;
    }

    
}