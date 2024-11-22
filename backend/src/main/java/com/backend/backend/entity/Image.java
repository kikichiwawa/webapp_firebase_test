package com.backend.backend.entity;

import com.google.cloud.Timestamp;

import lombok.Data;

@Data
public class Image{
    private String id;
    private String fileName;
    private String text;
    private Timestamp timestamp;
    private String greyFileName;
}
