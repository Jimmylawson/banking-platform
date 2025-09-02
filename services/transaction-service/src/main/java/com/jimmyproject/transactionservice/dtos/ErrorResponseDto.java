package com.jimmyproject.transactionservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {
    @NotBlank
    private String apiPath;
    @NotNull
    private HttpStatus statusCode;
    @NotBlank
    private String errorMessage;
    @Builder.Default
    private LocalDateTime errorTime = LocalDateTime.now();
}
