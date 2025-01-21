package com.backend.backend.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.backend.config.FirebaseProperty;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

@Service
public class FirebaseStorageService {
    @Autowired
    private FirebaseProperty firebaseProperty;

    @Autowired
    private Storage storage;

    public void getFile(String localPath, String storagePath) throws IOException{
        String bucketName = firebaseProperty.getBucketName();

        Blob blob = storage.get(bucketName, storagePath);
        if(blob == null){
            throw new IOException("File not found in Firebase Storage: " + localPath);
        }

        try(FileOutputStream outputStream = new FileOutputStream(localPath)){
            blob.downloadTo(outputStream);
        }
    }

    public void postFile(String localPath, String storagePath) throws IOException{
        String bucketName = firebaseProperty.getBucketName();
        File file = new File(localPath);

        if(!file.exists()){
            throw new IOException("Local file not found: " + localPath);
        }

        //MIMEタイプを取得
        String contentType = Files.probeContentType(file.toPath()); 
        if(contentType == null){
            contentType = "application/octet-stream";
        }

        BlobId blobId = BlobId.of(bucketName, storagePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
            .setContentType(contentType)
            .build();

        byte[] fileContent = Files.readAllBytes(file.toPath());
        storage.create(blobInfo, fileContent);
    }
}
