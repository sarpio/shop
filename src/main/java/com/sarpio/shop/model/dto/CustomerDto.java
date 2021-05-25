package com.sarpio.shop.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    @Schema(description = "client id in the database must be provided while editing")
    private Long id;

    @Size(min = 2, max = 50)
    @NotNull(message = "first name must not be null")
    @NotBlank(message = "first name must not be blank")
    @Schema(description = "client first name", required = true)
    private String first_name;

    @Size(min = 2, max = 50)
    @NotNull(message = "last name cannot be null")
    @NotBlank(message = "last name cannot be blank")
    @Schema(description = "client last name", required = true)
    private String last_name;

    @Size(min = 5, max = 50)
    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email cannot be blank")
    @Schema(description = "Must be a valid email address", required = true)
    private String email;

    @Size(min = 9, max = 15)
    @NotNull(message = "phone cannot be null")
    @NotBlank(message = "phone cannot be blank")
    @Schema(description = "use pattern like 555-444-555, cannot be null and not empty", required = true)
    private String phone;

    @Size(min = 10, max = 255)
    @NotNull(message = "address cannot be null")
    @NotBlank(message = "address cannot be blank")
    @Schema(description = "Complete address, mixed string and numbers", required = true)
    private String address;
}
