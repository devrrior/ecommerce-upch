package com.school.ecommerceupch.controllers.dtos.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class CreateProductRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private MultipartFile file;

    @NotBlank
    private Integer stock;

    @NotBlank
    private Float price;

    @NotBlank
    private Long userId;

    private List<Long> categoryIds;

}
