package com.banking.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor @NoArgsConstructor @Builder
@Schema(name = "ErrorResponse", description = "Schema of the error response")
public class ErrorResponseDto {

    @Schema(description = "API path/URI of the request")
    private String apiPath;

    @Schema(description = "HTTP status code of the error response")
    private HttpStatus errorCode;

    @Schema(description = "Message of the error response")
    private String errorMsg;

    @Schema(description = "Time at the error response generated")
    private LocalDateTime errorTime;
}
