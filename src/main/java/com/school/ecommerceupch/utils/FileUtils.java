package com.school.ecommerceupch.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Component
public class FileUtils {

    public File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }

    public String generateFileName(MultipartFile multipartFile) {
        Date date = new Date();
        String filename = (date + "-" + multipartFile.getOriginalFilename()).replace(" ", "_");

        return removeExtensionFromFilename(filename);
    }

    public String removeExtensionFromFilename(String filename) {
        if (filename.indexOf(".") > 0)
            return filename.substring(0, filename.lastIndexOf("."));
        return filename;
    }

}
