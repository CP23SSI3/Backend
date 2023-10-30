package com.example.internhub.services;

import io.minio.*;
import io.minio.messages.Bucket;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Service
public class MinioService {

    @Autowired
    MinioClient minioClient;

    String bucketName = "company-logo";

    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void getFile(String key) {
        try {
            DownloadObjectArgs dArgs = DownloadObjectArgs.builder()
                    .bucket("company-logo")
                    .filename("boba-370.png")
                    .build();
            minioClient.downloadObject(dArgs);
        } catch (Exception e) {

        }
    }

    public void uploadFile(HttpServletResponse response) {
        String objectName = "image.png";
        String contentType = "image/png";
        String filename = "/data/company-logo/boba-370.png";
        try {

        } catch (Exception e) {

        }
    }

    public void getFile(String fileName, HttpServletResponse response) {
        try {
            GetObjectResponse data = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
            byte[] fileData = data.readAllBytes();
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            response.setContentLength(fileData.length);
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(fileData);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void uploadPic(MultipartFile file, HttpServletResponse response) {
        try {
            if (!file.isEmpty()) {
                String objectName = file.getOriginalFilename();
                UploadObjectArgs uArgs = UploadObjectArgs.builder()
                        .bucket(bucketName)
                        .contentType(file.getContentType())
                        .object(file.getOriginalFilename())
                        .filename(file.getName())
                        .build();
//                minioClient.uploadObject(uArgs, file.getInputStream(), file.getSize());
//                minioClient.uploadObject(bucketName, objectName, file.getInputStream(), file.getSize(), file.getContentType());
//                return "File uploaded successfully!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }


}
