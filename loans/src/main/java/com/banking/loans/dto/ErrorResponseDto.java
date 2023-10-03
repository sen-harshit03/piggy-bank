package com.banking.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data @AllArgsConstructor @Builder
@Schema(name = "Error Response", description = "Schema to show the error response")
public class ErrorResponseDto {

    @Schema(description = "Shows the API path/URI")
    private String apiPath;

    @Schema(description = "HTTP Status Code of the error response")
    private HttpStatus errorCode;

    @Schema(description = "Error message of the response")
    private String errorMessage;

    @Schema(description = "Time at which error occurred")
    private LocalDateTime errorTime;
}
