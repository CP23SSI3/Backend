package com.example.internhub.controllers;

import com.example.internhub.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@RequestMapping("/api/v1/test-file")
@RestController
@CrossOrigin(origins = "${cors.allow.origin}")
public class TestAWS3Controller {

    @Autowired
    S3Service s3Service;

    @PostMapping("")
    public void uploadPicture(@RequestParam("file") MultipartFile file) {
        System.out.println(file.getName());
        System.out.println(file.getContentType());
        s3Service.uploadFileToS3("internhub-company-logo", (UUID.randomUUID().toString() + ".png"), file);
    }

    @GetMapping("")
    public void getPicture() {
//        s3Service.getPicture("internhub-company-logo", "02peekside-128.png");
        s3Service.test();
    }

    @PostMapping("/a")
    public void test(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println(file.getName());
        File convFile = new File( file.getOriginalFilename() );
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        s3Service.testAgain("internhub-company-logo", "final.png", convFile);
//        return convFile;
    }

}
