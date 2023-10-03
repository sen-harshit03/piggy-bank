package com.banking.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Schema(name = "Response", description = "Response of the HTTP request")
public class ResponseDto {

    @Schema(description = "HTTP status code of the response")
    private String statusCode;

    @Schema(description = "Message of the response")
    private String statusMessage;
}
