package com.sarpio.shop.model.dto.post;

import com.sarpio.shop.model.OrdersStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveOrdersDto {

    private Long id;

    @Schema(description = "Order number number")
    private Long number;

    @NotNull(message = "Must be valid Enum value")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Only Enum")
    private OrdersStatus status;
}
