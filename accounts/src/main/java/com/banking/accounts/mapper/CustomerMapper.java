package com.banking.accounts.mapper;

import com.banking.accounts.dto.CustomerDto;
import com.banking.accounts.entity.Customer;

public class CustomerMapper {

    public static Customer FromDto(CustomerDto customerDto) {
        return Customer.builder()
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .mobileNumber(customerDto.getMobileNumber())
                .build();
    }


    public static CustomerDto ToDto(Customer customer) {
        return CustomerDto.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .mobileNumber(customer.getMobileNumber())
                .build();
    }
}
