package com.backend.backend.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class Image{
    private String id;
    private String fileName;
    private String text;
    private Date timestamp;
    private String greyFileName;
}
