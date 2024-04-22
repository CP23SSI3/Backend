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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
    private ImageConverterService imageConverterService = new ImageConverterService();

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

//    @Override
//    public String testing(String bucketName, String fileName, MultipartFile file) throws IOException {
//        File convFile = new File(file.getOriginalFilename());
//        try {
//            convFile.deleteOnExit();
//            FileOutputStream fos = new FileOutputStream(convFile);
//            fos.write(file.getBytes());
//            fos.close();
//            String key = uploadFileWithFilenameToS3(bucketName, fileName, convFile);
//            convFile.delete();
//            return key;
//        } catch (IOException e) {
//            convFile.delete();
//            throw new IOException(e.getMessage());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public String uploadFileToS3(String bucketName, File file) throws AmazonSDKException {
        String key = UUID.randomUUID() + "." + FileNameUtils.getExtension(file.getName());
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
    public String uploadFileWithFilenameToS3(String bucketName, String fileName, File file) throws Exception {
        String key = fileName + "." + FileNameUtils.getExtension(file.getName());
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
        try {
            convFile.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            String key = uploadFileToS3(bucketName, convFile);
            convFile.delete();
            return key;
        } catch (IOException e) {
            convFile.delete();
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public String uploadMultiPartFileWithFilenameToS3(String bucketName, String fileName, MultipartFile file) throws IOException, AmazonSDKException {
        File convFile = new File(file.getOriginalFilename());
        try {
            convFile.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            String key = uploadFileWithFilenameToS3(bucketName, fileName, convFile);
            convFile.delete();
            return key;
        } catch (IOException e) {
            convFile.delete();
            throw new IOException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String uploadMultiPartFilePictureWithFilenameToJPGToS3(String bucketName, String fileName, MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        File tempJpgFile = null;
        try {
            convFile.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            BufferedImage bufferedImage = ImageIO.read(convFile);
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, null);
            tempJpgFile = File.createTempFile("converted_image", ".jpg");
            ImageIO.write(newBufferedImage, "jpg", tempJpgFile);
            String key = uploadFileWithFilenameToS3(bucketName, fileName, tempJpgFile);
            tempJpgFile.delete();
            convFile.delete();
            return key;
        } catch (IOException e) {
            tempJpgFile.delete();
            convFile.delete();
            throw new IOException(e.getMessage());
        } catch (Exception e) {
            tempJpgFile.delete();
            convFile.delete();
            throw new RuntimeException(e);
        }
    }
}
