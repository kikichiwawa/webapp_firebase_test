package com.backend.backend.entity;

import lombok.Data;

@Data
public class PostImageResponse {
    private FirebaseEntity<Image> imageEntity;
}
