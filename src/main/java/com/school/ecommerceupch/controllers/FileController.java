package com.school.ecommerceupch.controllers;

import com.school.ecommerceupch.services.interfaces.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/file")
public class FileController {

    private final IFileService service;

    public FileController(IFileService service) {
        this.service = service;
    }

    @PostMapping
    public void upload(@RequestParam MultipartFile file){
        service.upload(file);
    }

    @DeleteMapping
    public void delete(@RequestParam String filename){
        service.delete(filename);
    }

}
