package com.sarpio.shop.model.dto;

import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductsDto {

    @Schema(name = "Automatically generated value")
    private Long id;

    @NotBlank(message = "Cannot be blank")
    @NotNull(message = "Cannot be null")
    @Schema(description = "Product name", required = true)
    private String name;

    @Schema(description = "Product name")
    private String description;

//    @NotBlank(message = "Cannot be blank")
//    @NotNull(message = "Cannot be null")
    @Schema(description = "Product price", required = true)
    private Double price;

    @NotBlank(message = "Cannot be blank")
    @NotNull(message = "Cannot be null")
    @Schema(description = "Category of the product", required = true)
    private CategoryDto category;
}
