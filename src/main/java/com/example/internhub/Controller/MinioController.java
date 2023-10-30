package com.example.internhub.Controller;

import com.example.internhub.services.MinioService;
import io.minio.messages.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/minio")
public class MinioController {

    @Autowired
    MinioService minioService;

    @GetMapping
    public List<Bucket> getAllBuckets() {
        return minioService.getAllBuckets();
    }

    @PostMapping
    public void upload(HttpServletResponse response) {
        minioService.uploadFile(response);
    }

    @GetMapping("/file/{key}")
    public void getFile(@PathVariable String key, HttpServletResponse response) {
        minioService.getFile(key, response);
    }


//    @PostMapping("/file")
//    public void uploadPic(@RequestParam("file")MultipartFile file, HttpServletResponse response) {
//        minioService.uploadPic(file, response);
//    }

}
