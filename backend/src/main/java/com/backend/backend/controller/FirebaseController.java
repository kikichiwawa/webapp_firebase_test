package com.backend.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.config.FirestoreConstants;
import com.backend.backend.entity.ErrorResponse;
import com.backend.backend.entity.FirebaseEntity;
import com.backend.backend.entity.GetAllImageResponse;
import com.backend.backend.entity.GetSingleImageResponse;
import com.backend.backend.entity.Image;
import com.backend.backend.entity.PostImageRequest;
import com.backend.backend.entity.PostImageResponse;
import com.backend.backend.exception.ResourceNotFoundException;
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
        response.setAllImageEntity(firestoreService.getAllImages(FirestoreConstants.IMAGE_COLLECTION));
        // System.out.println(response.getAllImageEntity());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSingleImage(@PathVariable String id) {
        try{
            GetSingleImageResponse response = new GetSingleImageResponse();
            response.setImageEntity(firestoreService.getSingleImage(FirestoreConstants.IMAGE_COLLECTION, id));
            
            return ResponseEntity.ok(response);
        }catch(ResourceNotFoundException e){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setCode("NOT_FOUND");
            errorResponse.setMessage("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }catch(Exception e){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setCode("INTERNAL_SERVER_ERROR");
            errorResponse.setMessage("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSingleImage(@PathVariable String id){
        try {
            firestoreService.deleteImage(FirestoreConstants.IMAGE_COLLECTION, id);
            return ResponseEntity.ok("{\"status\": \"success\"}");        
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setCode("INTERNAL_SERVER_ERROR");
            errorResponse.setMessage("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/post")
    public ResponseEntity<?> postImage(@RequestBody PostImageRequest request){
        try {
            FirebaseEntity<Image> postedImage = firestoreService.postImage(
                FirestoreConstants.IMAGE_COLLECTION,
                request.getImage()
            );
            PostImageResponse response = new PostImageResponse();
            response.setImageEntity(postedImage);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setCode("INTERNAL_SERVER_ERROR");
            errorResponse.setMessage("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}