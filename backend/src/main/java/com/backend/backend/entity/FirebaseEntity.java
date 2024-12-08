package com.backend.backend.entity;

import lombok.Data;

@Data
public class FirebaseEntity<T>{
    private String id;
    private T entity;
}
