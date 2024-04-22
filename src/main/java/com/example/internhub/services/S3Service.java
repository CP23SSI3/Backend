package com.example.internhub.services;

import com.example.internhub.exception.AmazonSDKException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public interface S3Service {
    public String testing(String bucketName, String fileName, MultipartFile file) throws IOException;
    public String uploadFileToS3(String bucketName, File file) throws Exception;
    public String uploadFileWithFilenameToS3(String bucketName, String fileName, File file) throws Exception;
    public String uploadMultiPartFileToS3(String bucketName, MultipartFile file) throws IOException, AmazonSDKException;
    public String uploadMultiPartFileWithFilenameToS3(String bucketName, String fileName, MultipartFile file) throws IOException, AmazonSDKException;
}
