package com.banking.loans.service.impl;

import com.banking.loans.constants.LoanConstants;
import com.banking.loans.dto.LoanDto;
import com.banking.loans.entity.Loan;
import com.banking.loans.exceptions.LoanAlreadyExistsException;
import com.banking.loans.exceptions.ResourceNotFoundException;
import com.banking.loans.mapper.LoanMapper;
import com.banking.loans.repository.LoanRepository;
import com.banking.loans.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {

    private LoanRepository loanRepository;


    /**
     * Method to create loan
     *
     * @param mobileNumber - Mobile number of the customer
     */
    @Override
    public void createLoan(String mobileNumber) {

        Optional<Loan> optionalLoan = loanRepository.findByMobileNumber(mobileNumber);
        if(optionalLoan.isPresent()) {
            throw new LoanAlreadyExistsException(String.format("Loan already exists for given mobile Number: %s", mobileNumber));
        }

        Loan loan = createNewLoan(mobileNumber);
        loanRepository.save(loan);
    }

    /**
     * Method to fetch the loan details
     *
     * @param mobileNumber - Mobile Number of the customer
     * @return LoanDto - object
     */
    @Override
    public LoanDto fetchLoanDetails(String mobileNumber) {

        Loan loan = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "Mobile Number", mobileNumber)
        );

        LoanDto loanDto = LoanMapper.toDto(loan);
        return loanDto;
    }

    /**
     * Method to update the loan details
     *
     * @param mobileNumber - Mobile Number of the loan
     * @param loanDto    - LoanDto object with updated values
     * @return boolean - isUpdated - Shows if the update query is successful or not
     */
    @Override
    public boolean updateLoanDetails(String mobileNumber, LoanDto loanDto) {
        Loan loan = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobile number", mobileNumber)
        );

        updateLoan(loan, loanDto);
        loanRepository.save(loan);
        return true;
    }

    /**
     * Method to delete the loan from the repository
     *
     * @param mobileNumber - Mobile number of the loan which has to deleted
     * @return boolean - isDeleted - Whether delete operation was successful or not
     */
    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loan loan = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobile number", mobileNumber)
        );

        loanRepository.deleteById(loan.getLoanId());
        return true;
    }

    private void updateLoan(Loan loan, LoanDto loanDto) {

        if(Objects.nonNull(loanDto.getLoanNumber()) && !"".equalsIgnoreCase(loanDto.getLoanNumber())) {
            loan.setLoanNumber(loanDto.getLoanNumber());
        }

        if(Objects.nonNull(loanDto.getLoanType()) && !"".equalsIgnoreCase(loanDto.getLoanType())) {
            loan.setLoanType(loanDto.getLoanType());
        }

        if(Objects.nonNull(loanDto.getMobileNumber()) && !"".equalsIgnoreCase(loanDto.getMobileNumber())) {
            loan.setMobileNumber(loanDto.getMobileNumber());
        }

        if(loanDto.getTotalLoan() > 0) {
            loan.setTotalLoan(loanDto.getTotalLoan());
        }

        if(loanDto.getAmountPaid() > 0) {
            loan.setAmountPaid(loan.getAmountPaid() + loanDto.getAmountPaid());
            loan.setOutstandingAmount(loan.getOutstandingAmount() - loanDto.getAmountPaid());
        }
    }

    private Loan createNewLoan(String mobileNumber) {
        Loan newLoan = new Loan();

        Long loanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(loanNumber));
        newLoan.setLoanType(LoanConstants.HOME_LOAN);
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);

        return newLoan;
    }
}
