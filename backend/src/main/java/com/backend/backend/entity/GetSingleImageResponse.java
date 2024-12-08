package com.backend.backend.entity;

import lombok.Data;

@Data
public class GetSingleImageResponse {
    private FirebaseEntity<Image> imageEntity;
}
