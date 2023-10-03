package com.banking.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data @Builder
@Schema(name = "Account" , description = "Details about the account")
public class AccountDto {

    @Schema(description = "Account Number of the account", example = "xxxxxx4561")
    @NotNull(message = "Account Number cannot be null")
    @Digits(integer = 10, fraction = 0, message = "The account number must be of 10 digits")
    private Long accountNumber;


    @Schema(description = "Type of account",example = "SAVINGS/CURRENT")
    @NotEmpty(message = "Account Type cannot be empty or null")
    private String accountType;

    @Schema(description = "Branch address of the account", example = "123 Jane Street, New York")
    @NotEmpty(message = "Branch Address cannot be empty or null")
    private String branchAddress;
}
