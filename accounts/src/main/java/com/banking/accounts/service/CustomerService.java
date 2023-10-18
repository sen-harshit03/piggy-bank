package com.banking.accounts.service;

import com.banking.accounts.dto.CustomerDetailsDto;

public interface CustomerService {

    /**
     * Method to fetch the customer details
     * @param mobileNumber - Mobile number of the customer
     * @return
     */

    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
