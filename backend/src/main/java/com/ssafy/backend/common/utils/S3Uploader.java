package com.ssafy.backend.common.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Uploader {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile file, String memberId) throws IOException {
        File fileObj = convertMultiPartFileToFile(file,memberId);
        StringBuffer sb = new StringBuffer();
        sb.append(memberId).append(".png");
        String fileName =  sb.toString();
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, fileObj));
        fileObj.delete();
        return amazonS3Client.getUrl(bucket,fileName).toString();
    }

    private File convertMultiPartFileToFile(MultipartFile file, String userName) throws IOException {
        // 현재 위치에 사용자 이름을 파일 이름으로 사용하여 파일 생성
        File convertFile = new File(System.getProperty("user.dir") + "/" + userName + ".png");

        // 파일에 데이터 쓰기
        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
            fos.write(file.getBytes());
        }

        return convertFile;
    }

}