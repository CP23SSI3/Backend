package com.example.internhub.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class AwsConfig {
    @Value("${aws.access.key.id}")
    private String ACCESS_KEY;
    @Value("${aws.secret.key}")
    private String SECRET_KEY;
//    @Value("${company.logo.bucket.name}")
//    private String bucketName;

    public String getACCESS_KEY () {
        return ACCESS_KEY;
    }

    public String getSECRET_KEY () {
        return SECRET_KEY;
    }

//    public String getBucketName() {
//        return bucketName;
//    }
}
