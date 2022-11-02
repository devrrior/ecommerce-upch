package com.school.ecommerceupch.controllers;

import com.school.ecommerceupch.services.interfaces.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private IFileService service;

    @PostMapping
    public void upload(@RequestParam MultipartFile file,
                       @RequestParam Long idUser){
        service.upload(file, idUser);
    }

    @DeleteMapping
    public void delete(@RequestParam String filename){
        service.delete(filename);
    }

}
