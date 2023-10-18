package com.banking.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor @Builder
@Schema(
        name = "Customer details about account, loan and card",
        description = "Customer details about account, loan and card"
)
public class CustomerDetailsDto {

    @Schema(description = "Customer and its Account details")
    private CustomerAccountDto customerAccountDto;

    @Schema(description = "Loan details of the customer")
    private LoanDto loanDto;

    @Schema(description = "Card details of the customer")
    private CardDto cardDto;

}
