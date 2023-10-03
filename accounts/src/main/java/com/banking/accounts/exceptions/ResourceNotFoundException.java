package com.banking.accounts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     *
     * @param resource - Customer, Account
     * @param fieldName - MobileNumber, customerId, ...
     * @param fieldValue - MobileNumber value ...
     */
    public ResourceNotFoundException(String resource, String fieldName, String fieldValue) {
        super(String.format("%s not found with given input data %s : %s ", resource, fieldName, fieldValue));
    }
}
