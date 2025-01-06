package com.backend.backend.controller;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.service.FirebaseStorageService;
import com.backend.backend.service.ImageProcessingService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/test")    

public class TestController {
    @Autowired
    private ImageProcessingService imageProcessingService;

    @Autowired
    private FirebaseStorageService firebaseStorageService;
    
    @GetMapping("convert")
    public void convert(){
        String tempImagePath = "//temp_images";
        String image = "/test.png";

        String imagePath = Paths.get(tempImagePath, image).toString();
        imageProcessingService.convertColorToGrey(imagePath);
    }

    @GetMapping("download")
    public void download(@RequestParam String fileName, @RequestParam String destinationPath) throws IOException{
        firebaseStorageService.downloadFile(fileName, destinationPath);
    }
}