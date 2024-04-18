package com.example.internhub.controllers;

import com.example.internhub.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.*;
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
        convFile.deleteOnExit();
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        s3Service.testAgain("internhub-company-logo", (UUID.randomUUID() + ".png"),
//convertMultipartFileToFile(file)
                convFile
//                new FileOutputStream(new File(file.getOriginalFilename())).write(file.getBytes())
        );
//        return convFile;
        convFile.delete();
    }

    public File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        // Create a temporary file
        File file = File.createTempFile("temp", null);

        // Delete the temporary file when the JVM exits
        file.deleteOnExit();

        try (OutputStream os = new FileOutputStream(file)) {
            // Copy the contents of the multipart file to the temporary file
            InputStream is = multipartFile.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }

        return file;
    }

}
