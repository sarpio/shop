package com.sarpio.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sarpio.shop.model.OrdersStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jdk.jfr.Description;
import lombok.*;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDto {

    private Long id;

    @NotBlank(message = "must have 4 digits number")
    @NotNull(message = "must have 4 digits number")
    @Size(min = 4, max = 4, message = "Must have 4 digits")
    @Schema(description = "Order number of 4 digits")
    private Long number;

    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull
    @NotBlank
    @Schema(description = "Acceptable date fomat: yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "Must be valid Enum value")
    @NotBlank(message = "Must be valid Enum value")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Order status represented by Enum values")
    private OrdersStatus status;

//    @NotNull(message = "Value calculated as sum of all order details belong to specific order")
    @Schema(description = "Total value of the order")
    private Double total;

    @NotNull(message = "cannot be null")
    @NotBlank(message = "cannot be blank")
    @Schema(description = "Customer who set the order")
    @Description("Must be Long type representing Customer ID")
    private CustomerDto customerDto;

    @NotNull(message = "cannot be null")
    @NotBlank(message = "cannot be blank")
    @Schema(description = "Customer who set the order")
    @Description("Must be Long type repersenting Order Details belong to ID of the Order")
    private Set<OrderDetailDto> orderDetailsSet;
}
