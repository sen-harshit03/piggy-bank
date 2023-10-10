package com.banking.loans.controller;

import com.banking.loans.constants.LoanConstants;
import com.banking.loans.dto.ErrorResponseDto;
import com.banking.loans.dto.LoanDto;
import com.banking.loans.dto.LoansContactInfoDto;
import com.banking.loans.dto.ResponseDto;
import com.banking.loans.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Loan Application REST APIs", description = "CRUD REST APIs for Loans Application")
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class LoanController {

    private LoanService loanService;
    private LoansContactInfoDto loansContactInfoDto;

    public LoanController(LoanService loanService, LoansContactInfoDto loansContactInfoDto) {
        this.loanService = loanService;
        this.loansContactInfoDto = loansContactInfoDto;
    }

    @Value("${build.version}")
    private String buildVersion;


    @Operation(summary = "REST API to create a loan", description = "REST API to create loan inside Piggy Bank")
    @ApiResponse(responseCode = "201", description = "HttpStatus.CREATED")
    @PostMapping(path = "/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam
                                                      @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must of 10 digits")
                                                      String mobileNumber) {
        loanService.createLoan(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoanConstants.STATUS_201, LoanConstants.MESSAGE_201));
    }


    @Operation(summary = "REST API to get loan details", description = "REST API to get the loan details inside Piggy Bank")
    @ApiResponse(responseCode = "200", description = "HttpStatus.OK")
    @GetMapping(path = "/fetch")
    public ResponseEntity<LoanDto> fetchLoanDetails(@RequestParam
                                                        @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must of 10 digits")
                                                        String mobileNumber) {

        LoanDto loanDto = loanService.fetchLoanDetails(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loanDto);

    }


    @Operation(summary = "REST API to update the loan", description = "REST API to update the loan details inside Piggy Bank")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HttpStatus.OK"),
            @ApiResponse(responseCode = "417", description = "HttpStatus.EXPECTATION_FAILED"),
            @ApiResponse(responseCode = "500", description = "HttpStatus.INTERNAL_SERVER_ERROR",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @PutMapping(path = "/update")
    public ResponseEntity<ResponseDto> updateLoanDetails(@RequestParam
                                                             @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must of 10 digits")
                                                             String mobileNumber,
                                                         @Valid @RequestBody LoanDto loanDto) {
        boolean isUpdated = loanService.updateLoanDetails(mobileNumber, loanDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_UPDATE));
        }
    }


    @Operation(summary = "REST API to delete a loan", description = "REST API to delete loan inside Piggy Bank")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HttpStatus.OK"),
            @ApiResponse(responseCode = "417", description = "HttpStatus.EXPECTATION_FAILED"),
            @ApiResponse(responseCode = "500", description = "HttpStatus.INTERNAL_SERVER_ERROR")
    })
    @DeleteMapping(path = "/delete")
    public ResponseEntity<ResponseDto> deleteLoan(@RequestParam
                                                      @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must of 10 digits")
                                                      String mobileNumber) {
        boolean isDeleted = loanService.deleteLoan(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_UPDATE));
        }
    }


    @GetMapping(path = "/contact-info")
    public ResponseEntity<LoansContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loansContactInfoDto);
    }

    @GetMapping(path = "/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }



}
