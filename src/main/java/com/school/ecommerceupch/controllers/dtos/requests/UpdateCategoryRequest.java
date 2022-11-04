package com.school.ecommerceupch.controllers.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @Setter
public class UpdateCategoryRequest {

    @NotNull @NotBlank
    private String name;

    @NotBlank
    private List<Long> productCategoriesIds;

}
