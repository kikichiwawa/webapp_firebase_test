package com.backend.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.config.FirestoreConstants;
import com.backend.backend.entity.GetAllImageResponse;
import com.backend.backend.entity.GetSingleImageResponse;
import com.backend.backend.service.FirestoreService;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/fb")
public class FirebaseController {
    
    @Autowired
    private FirestoreService firestoreService;

    @GetMapping("/allImages")
    public ResponseEntity<GetAllImageResponse> getAllImages(){
        GetAllImageResponse response = new GetAllImageResponse();
        response.setAllImage(firestoreService.getAllImages(FirestoreConstants.IMAGE_COLLECTION));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSingleImageResponse> getSingleImage(@PathVariable String id) {
        GetSingleImageResponse response = new GetSingleImageResponse();
        response.setImage(firestoreService.getSingleImage(FirestoreConstants.IMAGE_COLLECTION, id));
        
        return ResponseEntity.ok(response);
    }
}