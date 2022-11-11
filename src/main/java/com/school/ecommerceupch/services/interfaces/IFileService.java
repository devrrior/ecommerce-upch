package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    BaseResponse upload(MultipartFile multipartFile);

    void delete(String imageUrl);

}
