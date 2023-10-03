package com.banking.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(
        name = "CustomerAccount",
        description = "Details about customer and its account"
)
public class CustomerAccountDto {

    @Schema(description = "Name of the customer", example = "John Doe")
    @Size(min = 3, message = "Name cannot be less than 3 characters")
    private String name;

    @Schema(description = "Email of the customer", example = "John@xyz.com")
    @Email(message = "Email address should be a valid address")
    private String email;

    @Schema(description = "Phone number of the customer", example = "xxxxxx7894")
    @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be of 10 digits")
    private String mobileNumber;

    @Schema(description = "Account number of the account", example = "xxx00xxx78")
    private Long accountNumber;

    @Schema(description = "Account type of the account", example = "SAVINGS/CURRENT")
    private String accountType;

    @Schema(description = "Address of the branch", example = "123 Jane street, New York")
    private String branchAddress;

}
