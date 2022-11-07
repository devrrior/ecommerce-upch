package com.school.ecommerceupch.services;

import com.cloudinary.Cloudinary;
import com.school.ecommerceupch.services.interfaces.IFileService;
import com.school.ecommerceupch.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// TODO improve upload and delete methods, because not always we'll want to save in ecommerce-upch/product-images
@Service("cloudinary")
public class CloudinaryFileServiceImpl implements IFileService {

    @Value("${CLOUDINARY_NAME}")
    private String cloudName;

    @Value("${CLOUDINARY_API_KEY}")
    private String apiKey;

    @Value("${CLOUDINARY_API_SECRET}")
    private String apiSecret;

    private final FileUtils fileUtils;

    private final Cloudinary cloudinary = new Cloudinary();

    private final Map<String, String> cloudinaryConfig = new HashMap<>();

    public CloudinaryFileServiceImpl(FileUtils fileUtils) {
        this.fileUtils = fileUtils;
    }

    @Override
    public String upload(MultipartFile multipartFile) {
        String fileUrl = "";

        try {
            File file = fileUtils.convertMultipartFileToFile(multipartFile);

            String fileName = fileUtils.generateFileName(multipartFile);

            fileUrl = uploadFileToCloudinary(fileName, file);

            file.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileUrl;
    }

    @Override
    public void delete(String imageUrl) {

        String filename = getFilenameFromUrl(imageUrl);
        String publicId = "ecommerce-upch/product-images/" + fileUtils.removeExtensionFromFilename(filename);

        try {
            Map response = cloudinary.uploader().destroy(publicId, cloudinaryConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String getFilenameFromUrl(String filename) {
        return filename.substring(filename.lastIndexOf("/") + 1);
    }

    private String uploadFileToCloudinary(String fileName, File file) throws IOException {
        cloudinaryConfig.put("public_id", fileName);
        Map response = cloudinary.uploader().upload(file, cloudinaryConfig);

        return (String) response.get("url");
    }

    @PostConstruct
    private void initializeCloudinary() {
        cloudinaryConfig.put("cloud_name", cloudName);
        cloudinaryConfig.put("api_key", apiKey);
        cloudinaryConfig.put("api_secret", apiSecret);
        cloudinaryConfig.put("folder", "/ecommerce-upch/product-images");
    }
}
