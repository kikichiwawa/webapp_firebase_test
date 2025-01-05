package com.backend.backend.controller;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.service.ImageProcessingService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/test")    

public class TestController {
    @Autowired
    private ImageProcessingService imageProcessingService;
    
    @GetMapping("convert")
    public void convert(){
        String tempImagePath = "//temp_images";
        String image = "/test.png";

        String imagePath = Paths.get(tempImagePath, image).toString();
        imageProcessingService.convertColorToGrey(imagePath);
    }
}