package com.backend.backend.controller;

// import java.sql.Date;

// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.backend.backend.entity.Image;
// import com.backend.backend.entity.GetAllImageResponse;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("")
public class TestController {

    // @GetMapping("allImages")
    // public ResponseEntity<GetAllImageResponse> getAllImages(){
    //     GetAllImageResponse response = new GetAllImageResponse();
    //     Image[] images = new Image[1];
        
    //     Image image = new Image();
    //     image.setFileName("1730361399646_PXL_20220410_021627323.jpg");
    //     image.setGreyFileName("1730361399646_PXL_20220410_021627323.jpg");
    //     image.setText("test image");
    //     image.setTimestamp(new Date(System.currentTimeMillis()));

    //     images[0] = image;
    //     response.setAllImage(images);
    //     return ResponseEntity.ok(response);
    // }
}