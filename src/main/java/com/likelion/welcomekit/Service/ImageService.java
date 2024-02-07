package com.likelion.welcomekit.Service;

import com.likelion.welcomekit.Exception.AnyExceptionsWithResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    private final Path rootLocation = Paths.get("/images");

    public Resource loadImageAsResource(String filename) throws MalformedURLException {
        Path file = rootLocation.resolve(filename);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("이미지 로드에 실패하였습니다: " + filename);
        }
    }

    public String handleUploadImage(MultipartFile imageFile, int targetWidth, int targetHeight){
        // 랜덤 파일 이름 생성
        String originalFileName = imageFile.getOriginalFilename();
        if (originalFileName == null || originalFileName.isEmpty()) {
            throw new AnyExceptionsWithResponse("파일 이름이 비어서는 안됩니다.");
        }
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String randomFileName = UUID.randomUUID().toString() + fileExtension;

        // 이미지 파일 저장
        String folderPath = "/images"; // 이미지를 저장할 폴더 경로
        Path directoryPath = Paths.get(folderPath);
        Path filePath = directoryPath.resolve(randomFileName);

        try {
            // 폴더 생성
            Files.createDirectories(directoryPath);

            // 기존 이미지가 있으면 삭제
            Files.deleteIfExists(filePath);

            // 이미지 리사이징
            BufferedImage originalImage = ImageIO.read(imageFile.getInputStream());
            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = resizedImage.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
            g.dispose();

            // 파일 저장
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, getFileExtension(originalFileName), baos);
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            Files.copy(is, filePath);
        } catch (IOException e) {
            throw new AnyExceptionsWithResponse("요청 처리 중 오류가 발생했습니다.");
        }

        // 이미지 URL 생성
        return "/api/v1/images/" + randomFileName;
    }
    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}