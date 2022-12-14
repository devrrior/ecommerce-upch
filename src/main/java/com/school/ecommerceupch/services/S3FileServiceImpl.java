package com.school.ecommerceupch.services;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.services.interfaces.IFileService;
import com.school.ecommerceupch.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service("s3")
public class S3FileServiceImpl implements IFileService {

//    @Value("${AWS_S3_ENDPOINT_URL}")
    private final String ENDPOINT_URL = "s3.us-east-1.amazonaws.com/";
//     @Value("${AWS_S3_BUCKET_NAME}")
    private final String BUCKET_NAME = "ecommerce-up";

//    @Value("${AWS_ACCESS_KEY}")
    private final String ACCESS_KEY = "AKIATLTSA5S7DKN6OW3J";

//    @Value("${AWS_SECRET_KEY}")
    private final String SECRET_KEY = "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";

    private AmazonS3 s3client;

    private final FileUtils fileUtils;

    public S3FileServiceImpl(FileUtils fileUtils) {
        this.fileUtils = fileUtils;
    }

    @Override
    public BaseResponse upload(MultipartFile multipartFile) {
        String fileUrl = "";

        try {
            File file = fileUtils.convertMultipartFileToFile(multipartFile);

            String fileName = fileUtils.generateFileName(multipartFile);

            fileUrl = "https://" + BUCKET_NAME + "." + ENDPOINT_URL + fileName;

            uploadFileToS3Bucket(fileName, file);

            file.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> message = new HashMap<>();
        message.put("Image URL", fileUrl);

        return BaseResponse.builder()
                .data(message)
                .message("Image uploaded")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public void delete(String imageUrl) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(BUCKET_NAME, imageUrl);
        s3client.deleteObject(deleteObjectRequest);
    }

    private void uploadFileToS3Bucket(String fileName, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead);
        s3client.putObject(putObjectRequest);
    }

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();
    }

}
