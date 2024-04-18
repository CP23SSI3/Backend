package com.example.internhub.services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.example.internhub.config.AwsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
//import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.AwsRequest;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.time.Duration;

@Service
public class S3Service {
    private static final Region AWS_REGION = Region.AP_SOUTHEAST_2;
//    @Autowired
    private AwsConfig awsConfig;
//    public S3Service(AwsConfig awsConfig) {
//        this.awsConfig = awsConfig;
//    }
    private String ACCESS_KEY;
//    @Value("${aws.secret.key}")
    private String SECRET_KEY;

    private static S3Client s3Client;

    public S3Service(AwsConfig awsConfig) {
        this.awsConfig = awsConfig;
        ACCESS_KEY = awsConfig.getACCESS_KEY();
        SECRET_KEY = awsConfig.getSECRET_KEY();
        System.out.println("access key " + awsConfig.getACCESS_KEY());
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

    public void uploadFileToS3(String bucketName, String key, MultipartFile file) {
//        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
//        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
//                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).withRegion(AWS_REGION.toString()).build();
//        TransferManager transferManager = new TransferManager(s3Client);
//        Upload upload = transferManager.upload(bucketName, key, file);
//        try {
//            upload.waitForCompletion();
//            System.out.println("File uploaded successfully to S3");
//        } catch (Exception e) {
//            System.err.println("Error uploading file to S3: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            transferManager.shutdownNow();
//        }

                System.out.println("uploading...?");
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType("image/jpeg")
                .build();
        System.out.println("create request");
        System.out.println(file);
//        System.out.println(file.toPath().toString());
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

    public void getPicture(String bucketName, String pictureKey) {
        S3Presigner presigner = S3Presigner.builder()
                .region(AWS_REGION)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
        System.out.println("presigner");
        PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(
                GetObjectPresignRequest.builder()
                        .getObjectRequest(builder -> builder.bucket(bucketName)
                                .key(pictureKey))
                        .signatureDuration(Duration.ofMinutes(60)).build()
        );
        URL url = presignedRequest.url();
        System.out.println("URL" + url.toString());
    }

    public void testAgain(String bucketName, String key, File file) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType("image/jpeg")
                .build();
        System.out.println("create request");
        System.out.println(file);
//        System.out.println(file.toPath().toString());
        try {
//            byte[] bytes = file.getBytes();
//            System.out.println(bytes);
//            PutObjectResponse response = s3Client.putObject(request, )
//            s3Client.putObject(bytes, bucketName, key);
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

}
