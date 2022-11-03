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
import com.school.ecommerceupch.services.interfaces.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.school.ecommerceupch.services.interfaces.IProductService;

@Service
public class FileServiceImpl implements IFileService {

    @Autowired
    private IProductService productService;

    private AmazonS3 s3client;

    private String ENDPOINT_URL = "";

    private String BUCKET_NAME = "";

    private String ACCESS_KEY = "";

    private String SECRET_KEY = "";

    @Override
    public String upload(MultipartFile multipartFile, Long idProduct) {
        String fileUrl= "";

        try {
            File file = convertMultipartFileToFile(multipartFile);

            String fileName = generateFileName(multipartFile);

            fileUrl = "https://" + BUCKET_NAME + "." + ENDPOINT_URL + fileName;

            uploadFileToS3Bucket(fileName, file);

            file.delete();

        } catch (Exception e){
            e.printStackTrace();
        }

        productService.updateProductImage(fileUrl, idProduct);

        return fileUrl;
    }

    @Override
    public void delete(String filename){
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(BUCKET_NAME, filename);
        s3client.deleteObject(deleteObjectRequest);
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multipartFile){
        return multipartFile.getName().replace(" ", "_");
    }

    private void uploadFileToS3Bucket(String fileName, File file){
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
