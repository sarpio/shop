package com.sarpio.shop.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;
    @Schema(description = "Enter name of category")
    @NotNull(message = "category name cannot be null")
    @NotBlank(message = "category cannto be blank")
    private String name;
}
