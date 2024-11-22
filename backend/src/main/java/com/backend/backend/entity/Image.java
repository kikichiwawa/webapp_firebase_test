package com.backend.backend.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Image{
    private String id;
    private String fileName;
    private String text;
    private Timestamp timestamp;
    private String greyFileName;
}
