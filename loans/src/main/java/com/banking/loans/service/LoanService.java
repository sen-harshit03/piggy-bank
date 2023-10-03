package com.banking.loans.service;

import com.banking.loans.dto.LoanDto;

public interface LoanService {

    /**
     * Method to create loan
     * @param mobileNumber - Mobile number of the customer
     */
    void createLoan(String mobileNumber);

    /**
     * Method to fetch the loan details
     * @param mobileNumber - Mobile Number of the customer
     * @return LoanDto - object
     */
    LoanDto fetchLoanDetails(String mobileNumber);

    /**
     * Method to update the loan details
     * @param mobileNumber - Mobile Number of the loan
     * @param loanDto - LoanDto object with updated values
     * @return boolean - isUpdated - Shows if the update query is successful or not
     */
    boolean updateLoanDetails(String mobileNumber, LoanDto loanDto);

    /**
     * Method to delete the loan from the repository
     * @param mobileNumber - Mobile number of the loan which has to deleted
     * @return boolean - isDeleted - Whether delete operation was successful or not
     */
    boolean deleteLoan(String mobileNumber);
}
