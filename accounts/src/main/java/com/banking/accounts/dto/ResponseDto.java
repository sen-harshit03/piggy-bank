package com.banking.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
@Schema(name = "Response", description = "Details about the Response")
public class ResponseDto {

    @Schema(description = "Status code in the response")
    private String statusCode;

    @Schema(name = "Status Message in the response")
    private String statusMsg;
}
