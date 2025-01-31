package com.backend.backend.controller;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        String inputFileNema = "/test.png";
        String outputFileNema = "/test_edited.png";

        String inputPath = Paths.get(tempImagePath, inputFileNema).toString();
        String outputPath = Paths.get(tempImagePath, outputFileNema).toString();
        imageProcessingService.convertColorToGray(inputPath, outputPath);
    }

    @GetMapping("get")
    public void getFile(@RequestParam String localPath, @RequestParam String storagePath) throws IOException{
        firebaseStorageService.getFile(localPath, storagePath);
    }

    @PostMapping("post")
    public void postFile(@RequestParam String localPath, @RequestParam String storagePath) throws IOException{
        firebaseStorageService.postFile(localPath, storagePath);
    }
}