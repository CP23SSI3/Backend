package com.example.internhub.configs;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    
    @Value("${minio.access.name}")
    String accessName;
    @Value("${minio.access.secret}")
    String secret;
    @Value("${minio.url}")
    String minioUrl;

    @Bean
    public MinioClient generate() {
        MinioClient client = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessName, secret)
                .build();
        return client;
    }

}
