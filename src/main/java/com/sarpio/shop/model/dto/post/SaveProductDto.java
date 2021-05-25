package com.sarpio.shop.model.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveProductDto {
    private Long id;

    @NotBlank(message = "Cannot be blank")
    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @Schema(description = "Product name", required = true)
    private String name;

    @Schema(description = "Product name")
    private String description;

    @NotNull(message = "Cannot be null")
    @Schema(description = "Product price", required = true)
    private Double price;

    @NotNull(message = "Cannot be null")
    @Schema(description = "CategoryId of the product", required = true)
    private Long categoryId;
}
