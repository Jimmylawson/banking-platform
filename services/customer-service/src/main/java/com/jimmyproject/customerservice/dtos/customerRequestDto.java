package com.jimmyproject.customerservice.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class customerRequestDto {
    private Long id;
    @NotBlank(message = "Customer name is required")
    private String customerName;
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9\\s-]{10,20}$", message = "Phone number should be valid")
    private String phoneNumber;
}
