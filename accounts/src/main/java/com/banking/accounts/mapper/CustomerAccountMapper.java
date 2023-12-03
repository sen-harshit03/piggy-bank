package com.banking.accounts.mapper;

import com.banking.accounts.dto.CardDto;
import com.banking.accounts.dto.CustomerAccountDto;
import com.banking.accounts.dto.CustomerDetailsDto;
import com.banking.accounts.dto.LoanDto;
import com.banking.accounts.entity.Account;
import com.banking.accounts.entity.Customer;

public class CustomerAccountMapper {

    public static CustomerAccountDto mapToCustomerAccountDto(Account account, Customer customer) {

        return CustomerAccountDto
                .builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .mobileNumber(customer.getMobileNumber())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .branchAddress(account.getBranchAddress())
                .build();
    }


    public static CustomerDetailsDto mapToCustomerDetailsDto(Account account, Customer customer, LoanDto loanDto, CardDto cardDto) {
        CustomerAccountDto customerAccountDto = mapToCustomerAccountDto(account, customer);
            return CustomerDetailsDto.builder()
                .customerAccountDto(customerAccountDto)
                .cardDto(cardDto)
                .loanDto(loanDto)
                .build();
    }
}
