package com.backend.backend.entity;

import java.util.List;

import lombok.Data;

@Data
public class GetAllImageResponse {
    private List<Image> allImage;
}
