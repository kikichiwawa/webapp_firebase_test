package com.backend.backend.controller;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.config.FirestoreConstants;
import com.backend.backend.entity.ErrorResponse;
import com.backend.backend.entity.FirebaseEntity;
import com.backend.backend.entity.Image;
import com.backend.backend.entity.PutGrayResponse;
import com.backend.backend.service.FirebaseStorageService;
import com.backend.backend.service.FirestoreService;
import com.backend.backend.service.ImageProcessingService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/convert")    
public class ConvertController {
    @Autowired 
    private FirestoreService firestoreService;

    @Autowired
    private ImageProcessingService imageProcessingService;

    @Autowired
    private FirebaseStorageService firebaseStorageService;
    
    @PutMapping("/gray/{id}")
    public synchronized ResponseEntity<?> putMethodName(@PathVariable String id) {
        try{
            FirebaseEntity<Image> entity  = firestoreService.getSingleImage(FirestoreConstants.IMAGE_COLLECTION, id);
            Image image = entity.getEntity();

            // グレースケール画像が存在するかどうかで分岐
            if(image.getGrayFilePath() == null){
                String colorStoragePath = image.getFilePath();
                String colorLocalPath = Paths.get("temp_images", "color.png").toString();
                
                // グレー画像のパス生成
                StringBuilder sb = new StringBuilder();
                sb.append(colorStoragePath);
                sb.insert(colorStoragePath.lastIndexOf("."), "_gray");
                String grayStoragePath = sb.toString();
                String grayLocalPath = Paths.get("temp_images", "gray.png").toString();

                // System.out.println(colorLocalPath);
                // System.out.println(colorStoragePath);
                // System.out.println(grayLocalPath);
                // System.out.println(grayStoragePath);

                // System.out.println("カラーファイルの取得");
                firebaseStorageService.getFile(colorLocalPath, colorStoragePath);
                // System.out.println("グレーへの変換");
                imageProcessingService.convertColorToGray(colorLocalPath, grayLocalPath);
                // System.out.println("グレーファイルのアップロード");
                firebaseStorageService.postFile(grayLocalPath, grayStoragePath);
                
                // Imageエンティティの更新
                image.setGrayFilePath(grayStoragePath);
                firestoreService.updateImage(FirestoreConstants.IMAGE_COLLECTION, id, image);
            }

            // レスポンスの送信
            PutGrayResponse response = new PutGrayResponse();
            response.setImageEntity(entity);
            return ResponseEntity.ok(response);
        }catch(Exception e){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setCode("INTERNAL_SERVER_ERROR");
            errorResponse.setMessage(("An unexpected error occurred: " + e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
        
    }
}
