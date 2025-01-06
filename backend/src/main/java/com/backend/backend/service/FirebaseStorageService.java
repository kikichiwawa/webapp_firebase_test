package com.backend.backend.service;

import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;

@Service
public class FirebaseStorageService {

    @Autowired
    private Storage storage;

    public void downloadFile(String fileName, String destinationPath) throws IOException{
        String bucketName = "fir-tutorial-6fd8a.appspot.com";

        Blob blob = storage.get(bucketName, destinationPath);
        if(blob == null){
            throw new IOException("File not found in Firebase Storage: " + fileName);
        }

        try(FileOutputStream outputStream = new FileOutputStream(fileName)){
            blob.downloadTo(outputStream);
        }
    }

}
