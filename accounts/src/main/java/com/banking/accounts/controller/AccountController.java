package com.banking.accounts.controller;


import com.banking.accounts.constants.AccountConstants;
import com.banking.accounts.dto.*;
import com.banking.accounts.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(name = "CRUD REST APIs for Account Service", description = "CRUD REST APIs in Account Service of Piggy Band to Create, Read, Update and Delete accounts")
@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AccountController {

    private AccountService accountService;
    private AccountsContactInfo accountsContactInfo;

    public AccountController(AccountService accountService, AccountsContactInfo accountsContactInfo) {
        this.accountService = accountService;
        this.accountsContactInfo = accountsContactInfo;
    }

    @Value("${build.version}")
    private String buildVersion;


    @Operation(summary = "Create REST API for Account Service", description = "REST API to create accounts and customers")
    @ApiResponse(responseCode = "200", description = "HttpStatus.CREATED")
    @PostMapping(path = "/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        accountService.createAccount(customerDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
    }


    @Operation(summary = "Get REST API for Account Service", description = "REST API to get accounts and customers details")
    @ApiResponse(responseCode = "200", description = "HttpStatus.CREATED", content = @Content(schema = @Schema(implementation = AccountDto.class)))
    @GetMapping(path = "/fetch")
    public ResponseEntity<CustomerAccountDto> fetchAccountDetailsByMobileNumber(@RequestParam
                                                                                    @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be of 10 digits")
                                                                                    String mobileNumber) {
        CustomerAccountDto customerAccountDto = accountService.fetchAccountDetailsByMobileNumber(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerAccountDto);
    }


    @Operation(summary = "Update REST API for Account Service", description = "REST API to update accounts and customers details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HttpStatus.OK"),
            @ApiResponse(responseCode = "500", description = "HttpStatus.INTERNAL_SERVER_ERROR",
                    content = @Content( schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "417", description = "HttpStatus.EXPECTATION_FAILED")
    })
    @PutMapping(path = "/update")
    public ResponseEntity<ResponseDto> updateAccountByAccountNumber(@RequestParam
                                                                        @Digits(integer = 10, fraction = 0, message = "The account number must be of 10 digits")
                                                                        Long accountNumber, @RequestBody @Valid CustomerAccountDto customerAccountDto) {
        boolean isUpdated = accountService.updateAccountByAccountNumber(accountNumber, customerAccountDto);

        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE));
        }
    }


    @Operation(summary = "Delete REST API for Account Service", description = "REST API to delete accounts and customers")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HttpStatus.OK"),
            @ApiResponse(responseCode = "500", description = "HttpStatus.INTERNAL_SERVER_ERROR"),
            @ApiResponse(responseCode = "417", description = "HttpStatus.EXPECTATION_FAILED")
    })
    @DeleteMapping(path = "/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam
                                                         @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be of 10 digits")
                                                         String mobileNumber) {
        boolean isDeleted = accountService.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE));
        }
    }

    // TODO: Add the documentation
    @GetMapping(path = "/contact-info")
    public ResponseEntity<AccountsContactInfo> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfo);
    }

    @GetMapping(path = "/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

}
