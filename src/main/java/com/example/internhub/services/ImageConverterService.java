package com.example.internhub.services;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageConverterService {
    public File convertPngToJpg(File file) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file);
        BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, null);
        File tempJpgFile = File.createTempFile("converted_image", ".jpg");
        ImageIO.write(newBufferedImage, "jpg", tempJpgFile);
        return tempJpgFile;
    }

    public File convertPngToJpg(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        try {
            convFile.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            BufferedImage bufferedImage = ImageIO.read(convFile);
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, null);
            File tempJpgFile = File.createTempFile("converted_image", ".jpg");
            ImageIO.write(newBufferedImage, "jpg", tempJpgFile);
            convFile.delete();
            return tempJpgFile;
        } catch (IOException e) {
            convFile.delete();
            throw new IOException(e.getMessage());
        }
    }
}
