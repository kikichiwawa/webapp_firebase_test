package com.backend.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "firebase")
public class FirebaseProperty {
    private String bucketName;
    private String jsonPath;
}
