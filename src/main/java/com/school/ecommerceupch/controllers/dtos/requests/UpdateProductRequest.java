package com.school.ecommerceupch.controllers.dtos.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class UpdateProductRequest {

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String description;

    private MultipartFile file;

    @NotNull
    @NotBlank
    private Integer stock;

    @NotNull
    @NotBlank
    private Float price;

    @NotNull
    @NotBlank
    private Long userId;

    private List<Long> categoryIds;


}
