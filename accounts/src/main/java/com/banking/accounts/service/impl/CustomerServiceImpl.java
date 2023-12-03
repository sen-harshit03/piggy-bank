package com.banking.accounts.service.impl;

import com.banking.accounts.dto.CardDto;
import com.banking.accounts.dto.CustomerDetailsDto;
import com.banking.accounts.dto.LoanDto;
import com.banking.accounts.entity.Account;
import com.banking.accounts.entity.Customer;
import com.banking.accounts.exceptions.ResourceNotFoundException;
import com.banking.accounts.mapper.CustomerAccountMapper;
import com.banking.accounts.repository.AccountRepository;
import com.banking.accounts.repository.CustomerRepository;
import com.banking.accounts.service.CustomerService;
import com.banking.accounts.service.client.CardsFeignClient;
import com.banking.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;


    /**
     * Method to fetch the customer details
     *
     * @param mobileNumber - Mobile number of the customer
     * @return
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer", "mobile number", mobileNumber)
        );

        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Account", "customer id", customer.getCustomerId().toString())
        );

        CardDto cardDto = null;
        ResponseEntity<CardDto> cardDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId ,mobileNumber);
        if(Objects.nonNull(cardDtoResponseEntity)) {
            cardDto = cardDtoResponseEntity.getBody();
        }

        LoanDto loanDto = null;
        ResponseEntity<LoanDto> loanDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        if (Objects.nonNull(loanDtoResponseEntity)) {
            loanDto = loanDtoResponseEntity.getBody();
        }

        CustomerDetailsDto customerDetailsDto = CustomerAccountMapper.mapToCustomerDetailsDto(account, customer, loanDto, cardDto);
        return customerDetailsDto;
    }
}
