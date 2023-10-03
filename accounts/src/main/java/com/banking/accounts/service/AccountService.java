package com.banking.accounts.service;

import com.banking.accounts.dto.CustomerAccountDto;
import com.banking.accounts.dto.CustomerDto;

public interface AccountService {

    /**
     * This method is used to create the account
     * @param customerDto - Takes customerDto object
     */
    void createAccount(CustomerDto customerDto);


    /**
     * This method is used to fetch account details through mobile number.
     * @param mobileNumber - Mobile Number of the customer
     * @return CustomerDto object
     */
    CustomerAccountDto fetchAccountDetailsByMobileNumber(String mobileNumber);

    /**
     * This method updates the account and customer details
     * @param accountNumber - Account Number of the account
     * @param customerAccountDto - Customer/Account details which needs to be updated.
     */
    boolean updateAccountByAccountNumber(Long accountNumber, CustomerAccountDto customerAccountDto);

    /**
     * This method is responsible for deleting the account from the bank.
     * @param mobileNumber - Mobile Number of the customer.
     * @return a boolean value which states whether account is deleted or not.
     */
    boolean deleteAccount(String mobileNumber);
}
