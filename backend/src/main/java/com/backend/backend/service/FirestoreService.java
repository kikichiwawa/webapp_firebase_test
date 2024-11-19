package com.backend.backend.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.backend.entity.Image;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;

@Service
public class FirestoreService {
    @Autowired
    private Firestore firestore;

    private Map<String, Object> convertImageToMap(Image image){
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.convertValue(image, new TypeReference<Map<String, Object>>() {});
        return map;
    }

    private Image convertMapToImage(Map<String, Object> map){
        ObjectMapper mapper = new ObjectMapper();
        Image image = mapper.convertValue(map, new TypeReference<Image>() {});
        return image;
    }

    public String postImage(String collectionName, Image image){
        try {
            DocumentReference docRef = firestore.collection(collectionName).document();
            docRef.set(image).get();
            return "Image updated successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating document: "+e.getMessage();
        }
    }

    public Image getSingleImage(String collectionName, String documentId){
        try {
            DocumentReference docRef = firestore.collection(collectionName).document(documentId);
            Map<String, Object> map = docRef.get().get().getData();
            return convertMapToImage(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Image> getAllImages(String collectionName){
        try {
            QuerySnapshot snapshot = firestore.collection(collectionName).get().get();
            return snapshot.getDocuments().stream().map(doc -> convertMapToImage(doc.getData())).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public String updateImage(String collectionName, String documentId, Image image){
        try {
            DocumentReference docRef = firestore.collection(collectionName).document(documentId);
            Map<String, Object> map = convertImageToMap(image);
            docRef.update(map).get();
            return "Image updated successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error updating document: " + e.getMessage();
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
