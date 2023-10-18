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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Card", description = "Schema of the card")
public class CardDto {

    @Schema(description = "Mobile number of the customer", example = "1234567890")
    @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile Number must be of 10 digits")
    private String mobileNumber;

    @Schema(description = "Card number of the customer", example = "123456789012")
    @Pattern(regexp = "^$|[0-9]{12}", message = "Card number must of 12 digits")
    private String cardNumber;

    @Schema(description = "Type of the card", example = "DEBIT/CREDIT")
    private String cardType;

    @Schema(description = "Total Limit amount of the card", example = "1000000")
    @Positive(message = "Total Limit must be greater than 0")
    private int totalLimit;

    @Schema(description = "Total amount used in the card", example = "1000")
    @PositiveOrZero(message = "Total amount used must be greater or equal to 0")
    private int amountUsed;

    @Schema(description = "Total available amount in the card", example = "100000")
    @PositiveOrZero(message = "Total available amount must be greater or equal to 0")
    private int availableAmount;
}
