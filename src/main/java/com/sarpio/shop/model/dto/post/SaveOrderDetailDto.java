package com.sarpio.shop.model.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveOrderDetailDto {
    private Long id;

    @Min(1)
    @NotNull(message = "must be positive value")
    @Schema(description = "Product quantity according to linked order detail", required = true)
    private Long quantity;

    @Min(0)
    @NotNull(message = "must be positive value")
    @Schema(description = "Unit price multiplied by quantity according to linked order detail", required = true)
    private Long productId;

    @NotNull(message = "must be positive value")
    @Schema(description = "Product Id linked order detail", required = true)
    private Long orderId;
}
