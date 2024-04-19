package com.example.internhub.services;

import com.example.internhub.config.AwsConfig;
import com.example.internhub.exception.AmazonSDKException;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.UUID;

@Service
@Primary
public class MySQLS3Service implements S3Service{
    private static final Region AWS_REGION = Region.AP_SOUTHEAST_2;
    private AwsConfig awsConfig;
    private String ACCESS_KEY;
    private String SECRET_KEY;

    private static S3Client s3Client;

    public MySQLS3Service(AwsConfig awsConfig) {
        this.awsConfig = awsConfig;
        ACCESS_KEY = awsConfig.getACCESS_KEY();
        SECRET_KEY = awsConfig.getSECRET_KEY();
        AwsCredentials credentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
        s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(AWS_REGION)
                .build();
    }

    public void test() {
        try {
            ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
            s3Client.listBuckets(listBucketsRequest);
            System.out.println(s3Client.listBuckets(listBucketsRequest));
            System.out.println("Credentials are valid and usable.");
        } catch (Exception e) {
            System.err.println("Credentials are not valid or usable: " + e.getMessage());
        }
    }

    public void testBytes(String bucketName, String key, MultipartFile file) {
                System.out.println("uploading...?");
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType("image/jpeg")
                .build();
        System.out.println("create request");
        try {
            byte[] bytes = file.getBytes();
            System.out.println(bytes);
//            PutObjectResponse response = s3Client.putObject(request, )
//            s3Client.putObject(bytes, bucketName, key);
            PutObjectResponse response = s3Client.putObject(request, RequestBody.fromBytes(bytes));
            System.out.println("Upload complete. ETag: " + response.eTag());
        } catch (SdkException e) {
            System.err.println("AWS SDK error during file upload: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error during file upload: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void testAgain(String bucketName, String key, File file) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
//                .contentType("image/jpeg")
                .build();
        System.out.println(file);
        try {
            PutObjectResponse response = s3Client.putObject(request, RequestBody.fromFile(file));
            System.out.println("Upload complete. ETag: " + response.eTag());
        } catch (SdkException e) {
            System.err.println("AWS SDK error during file upload: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error during file upload: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String uploadFileToS3(String bucketName, File file) throws AmazonSDKException {
        String fileExtension = FileNameUtils.getExtension(file.getName());
        String id = UUID.randomUUID().toString();
        String key = id + "." + fileExtension;
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        try {
            s3Client.putObject(request, RequestBody.fromFile(file));
            return key;
        } catch (SdkException e) {
            throw new AmazonSDKException(e.getLocalizedMessage());
        }
    }

    @Override
    public String uploadMultiPartFileToS3(String bucketName, MultipartFile file) throws IOException, AmazonSDKException {
        File convFile = new File(file.getOriginalFilename());
        String key = "";
        try {
            convFile.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            key = uploadFileToS3(bucketName, convFile);
            convFile.delete();
            return key;
        } catch (IOException e) {
            convFile.delete();
            throw new IOException(e.getMessage());
        }
    }
}
