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

//    @JsonFormat(pattern="yyyy-MM-dd")
//    @Schema(description = "Acceptable date format: yyyy-MM-dd")
//    private LocalDate date;

    @NotNull(message = "Must be valid Enum value")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Only Enum")
    private OrdersStatus status;

//    @NotNull(message = "Value calculated as sum of all order details belong to specific order")
//    @NotBlank(message = "Value calculated as sum of all order details belong to specific order")
//    @Min(0)
//    @Schema(description = "Total value of the order")
//    private Double total;
}
