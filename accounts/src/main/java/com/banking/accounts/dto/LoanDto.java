package com.banking.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
@Schema(title = "Loan", description = "Schema of Loan")
public class LoanDto {

    @Schema(description = "Mobile number associated with loan", example = "9999988888")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must of 10 digits")
    private String mobileNumber;

    @Schema(description = "Type of loan", example = "HOME")
    private String loanType;

    @Schema(description = "loan number associated with loan", example = "68776xxxxxx")
    private String loanNumber;

    @Positive(message = "Total loan amount must be greater than 0")
    @Schema(description = "Total loan amount", example = "1000000")
    private int totalLoan;

    @PositiveOrZero(message = "Total loan amount paid should be equal to or greater than zero")
    @Schema(description = "Total loan amount paid", example = "0")
    private int amountPaid;

    @PositiveOrZero(message = "Total loan amount paid should be equal to or greater than zero")
    @Schema(description = "Total loan amount remaining to be paid", example = "1000000")
    private int outstandingAmount;
}
