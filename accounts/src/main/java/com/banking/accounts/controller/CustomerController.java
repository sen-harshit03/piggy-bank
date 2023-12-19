package com.banking.accounts.controller;

import com.banking.accounts.dto.CustomerAccountDto;
import com.banking.accounts.dto.CustomerDetailsDto;
import com.banking.accounts.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "REST API for customer", description = "REST API to fetch all Customer details")
@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @Operation(
            summary = "Fetch Customer details REST API",
            description = "Rest API to fetch customer and its account, loan, and card details"
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR")

            }
    )
    @GetMapping(path = "/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
            @RequestHeader("piggybank-correlation-id") String correlationId,
            @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be of 10 digits")
                                                                     String mobileNumber) {
        log.debug("Calling customer service to fetch customer details");
        CustomerDetailsDto customerDetailsDto = customerService.fetchCustomerDetails(mobileNumber, correlationId);
        log.debug("Successfully fetched the customer details");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDetailsDto);
    }
}
