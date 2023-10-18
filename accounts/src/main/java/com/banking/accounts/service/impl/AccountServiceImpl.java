package com.banking.accounts.service.impl;

import com.banking.accounts.constants.AccountConstants;
import com.banking.accounts.dto.CustomerAccountDto;
import com.banking.accounts.dto.CustomerDto;
import com.banking.accounts.entity.Account;
import com.banking.accounts.entity.Customer;
import com.banking.accounts.exceptions.CustomerAlreadyExistsException;
import com.banking.accounts.exceptions.ResourceNotFoundException;
import com.banking.accounts.mapper.CustomerAccountMapper;
import com.banking.accounts.mapper.CustomerMapper;
import com.banking.accounts.repository.AccountRepository;
import com.banking.accounts.repository.CustomerRepository;
import com.banking.accounts.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;


    /**
     * This method is used to create the account
     *
     * @param customerDto - Takes customerDto object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = CustomerMapper.FromDto(customerDto);

        // Each customer should have a unique phone number
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number :"
                    + customer.getMobileNumber());
        }

        Customer savedCustomer = customerRepository.save(customer);

        Account account = createNewAccount(savedCustomer);
        accountRepository.save(account);
    }

    /**
     * This method is used to fetch account details through mobile number
     *
     * @param mobileNumber - Mobile number of the account holder
     * @return CustomerDto object
     */
    @Override
    public CustomerAccountDto fetchAccountDetailsByMobileNumber(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobile Number", mobileNumber)
        );

        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        return CustomerAccountMapper.mapToCustomerAccountDto(account, customer);
    }


    /**
     *
     * @param accountNumber - Account Number of the account
     * @param customerAccountDto - Customer/Account details which needs to be updated.
     */

    @Override
    public boolean updateAccountByAccountNumber(Long accountNumber, CustomerAccountDto customerAccountDto) {

        Account account = accountRepository.findById(accountNumber).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "accountNumber", accountNumber.toString())
        );

        updateAccountDetails(account, customerAccountDto);
        accountRepository.save(account);


        Customer customer = customerRepository.findById(account.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "customerId", account.getCustomerId().toString())
        );

        updateCustomerDetails(customer, customerAccountDto);
        customerRepository.save(customer);

        return true;
    }

    /**
     * This method is responsible for deleting the account from the bank.
     *
     * @param mobileNumber - Mobile Number of the customer.
     * @return a boolean value which states whether account is deleted or not.
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }

    private void updateCustomerDetails(Customer customer, CustomerAccountDto customerAccountDto) {

        if(Objects.nonNull(customerAccountDto.getName()) && !"".equalsIgnoreCase(customerAccountDto.getName())) {
            customer.setName(customerAccountDto.getName());
        }

        if(Objects.nonNull(customerAccountDto.getEmail()) && !"".equalsIgnoreCase(customerAccountDto.getEmail())) {
            customer.setEmail(customerAccountDto.getEmail());
        }

        if(Objects.nonNull(customerAccountDto.getMobileNumber()) && !"".equalsIgnoreCase(customerAccountDto.getMobileNumber())) {
            customer.setMobileNumber(customerAccountDto.getMobileNumber());
        }
    }

    private void updateAccountDetails(Account account, CustomerAccountDto customerAccountDto) {

        if(Objects.nonNull(customerAccountDto.getAccountType()) && !"".equalsIgnoreCase(customerAccountDto.getAccountType()) ) {
            account.setAccountType(customerAccountDto.getAccountType());
        }

        if(Objects.nonNull(customerAccountDto.getBranchAddress()) && !"".equalsIgnoreCase(customerAccountDto.getBranchAddress()) ) {
            account.setBranchAddress(customerAccountDto.getBranchAddress());
        }
    }


    private Account createNewAccount(Customer customer) {
       // Relation is by customerId
        Account account = new Account();
        account.setCustomerId(customer.getCustomerId());

        Long accountNumber = 1000000000L + new Random().nextInt(900000000);

        account.setAccountNumber(accountNumber);
        account.setAccountType(AccountConstants.SAVINGS);
        account.setBranchAddress(AccountConstants.ADDRESS);
        return account;

        // Builder pattern doesn't work if the class extends any parent class;
    }
}
