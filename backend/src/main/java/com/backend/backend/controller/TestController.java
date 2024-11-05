package com.backend.backend.controller;

import java.sql.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.entity.Image;
import com.backend.backend.entity.getAllImageRequest;
import com.backend.backend.entity.getAllImageResponse;

@RestController
@RequestMapping("")
public class TestController {

    @GetMapping("allImages")
    public ResponseEntity<getAllImageResponse> getAllImages(@RequestBody getAllImageRequest request){
        getAllImageResponse response = new getAllImageResponse();
        Image[] images = new Image[0];
        
        Image image = new Image();
        image.setFileName("1730361399646_PXL_20220410_021627323.jpg");
        image.setGreyFileName("1730361399646_PXL_20220410_021627323.jpg");
        image.setId("test");
        image.setText("test image");
        image.setTimestamp(new Date(System.currentTimeMillis()));

        response.setAllImage(images);
        return ResponseEntity.ok(response);
    }
}