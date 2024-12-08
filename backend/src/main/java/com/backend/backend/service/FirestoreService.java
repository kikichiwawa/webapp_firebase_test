package com.backend.backend.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.backend.entity.FirebaseEntity;
import com.backend.backend.entity.Image;
import com.backend.backend.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;

@Service
public class FirestoreService {

    @Autowired
    private Firestore firestore;
    
    @Autowired
    private ObjectMapper mapper;

    private Map<String, Object> convertImageToMap(Image image){
        Map<String, Object> map = mapper.convertValue(image, new TypeReference<Map<String, Object>>() {});
        return map;
    }

    private FirebaseEntity<Image> convertMapToImage(Map<String, Object> map, String id){
        // idの設定
        FirebaseEntity<Image> entity = new FirebaseEntity<Image>();
        Image image = mapper.convertValue(map, new TypeReference<Image>() {});
        entity.setEntity(image);
        entity.setId(id);
        return entity;
    }

    public FirebaseEntity<Image> postImage(String collectionName, Image image)throws ExecutionException, InterruptedException{
        try {
            // firestoreにImageをアップロードしid生成
            DocumentReference docRef = firestore.collection(collectionName).add(image).get();
            String newId = docRef.getId();

            FirebaseEntity<Image> entity = new FirebaseEntity<Image>();
            entity.setEntity(image);
            entity.setId(newId);
            return updateImage(collectionName, newId, image);
        } catch (Exception e) {
            throw e;
        }
    }

    public FirebaseEntity<Image> getSingleImage(String collectionName, String documentId) throws ExecutionException, InterruptedException, ResourceNotFoundException{
        try {
            DocumentReference docRef = firestore.collection(collectionName).document(documentId);
            if(!docRef.get().get().exists()){
                throw new ResourceNotFoundException("Document with ID nonexistent-id does not exist in collection images");
            }

            Map<String, Object> map = docRef.get().get().getData();
            
            System.out.println(map);
            String id = docRef.getId();
            return convertMapToImage(map, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<FirebaseEntity<Image>> getAllImages(String collectionName){
        try {
            QuerySnapshot snapshot = firestore.collection(collectionName).get().get();
            return snapshot.getDocuments().stream().map(doc -> convertMapToImage(doc.getData(), doc.getId()))
                .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public FirebaseEntity<Image> updateImage(String collectionName, String documentId, Image image)throws ExecutionException, InterruptedException{
        try {
            DocumentReference docRef = firestore.collection(collectionName).document(documentId);
            Map<String, Object> map = convertImageToMap(image);
            docRef.update(map).get();

            FirebaseEntity<Image> entity = new FirebaseEntity<Image>();
            entity.setEntity(image);
            entity.setId(documentId);
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String deleteImage(String collectionName, String documentId){
        try {
            firestore.collection(collectionName).document(documentId).delete().get();
            return "Document deleted successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error deleting document: " + e.getMessage();
        }
    }
}
