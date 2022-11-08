package com.school.ecommerceupch.controllers.dtos.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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

    @NotNull
    private MultipartFile image;

    @NotNull
    private Integer stock;

    @NotNull
    private Float price;

    @NotNull
    private Long productStatusId;

    @NotNull
    private List<@NotNull Long> categoryIds;

}
