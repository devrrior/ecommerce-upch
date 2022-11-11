package com.school.ecommerceupch.controllers;

import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.services.interfaces.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/file")
public class FileController {

    @Autowired
    @Qualifier("cloudinary")
    private IFileService service;

    @PostMapping
    public ResponseEntity<BaseResponse> upload(@RequestParam MultipartFile file) {

        BaseResponse baseResponse = service.upload(file);

        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }

    @DeleteMapping
    public void delete(@RequestParam String imageUrl) {
        service.delete(imageUrl);
    }

}
