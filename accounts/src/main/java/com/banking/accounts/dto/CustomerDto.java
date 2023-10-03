package com.banking.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data @Builder
@Schema(name = "Customer", description = "Details about the customer")
public class CustomerDto {

    @Schema(description = "Name of the customer", example = "John Doe")
    @NotBlank(message = "Name cannot be empty or null")
    @Size(min = 3, message = "Name cannot be less than 3 characters")
    private String name;


    @Schema(description = "Email of the customer", example = "John@xyz.com")
    @NotEmpty(message = "Email address cannot be empty or null")
    @Email(message = "Email address should be a valid address")
    private String email;


    @Schema(description = "Phone number of the customer", example = "9756272136")
    @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be of 10 digits")
    private String mobileNumber;

//    private AccountDto accountDto;
}
