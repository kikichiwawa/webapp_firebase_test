package com.backend.backend.entity;


import com.google.cloud.Timestamp;

import lombok.Data;

@Data
public class Image{
    private String filePath;
    private String text;
    private Timestamp timestamp;
    private String grayFilePath;
}
