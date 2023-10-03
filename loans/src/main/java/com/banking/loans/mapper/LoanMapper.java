package com.banking.loans.mapper;

import com.banking.loans.dto.LoanDto;
import com.banking.loans.entity.Loan;

public class LoanMapper {

    public static Loan fromDto(LoanDto loanDto) {
        Loan loan = new Loan();
        loan.setMobileNumber(loanDto.getMobileNumber());
        loan.setLoanNumber(loanDto.getLoanNumber());
        loan.setLoanType(loan.getLoanType());
        loan.setTotalLoan(loanDto.getTotalLoan());
        loan.setAmountPaid(loanDto.getAmountPaid());
        loan.setOutstandingAmount(loanDto.getOutstandingAmount());

        return loan;
    }


    public static LoanDto toDto(Loan loan) {
        LoanDto loanDto = LoanDto.builder()
                .loanType(loan.getLoanType())
                .loanNumber(loan.getLoanNumber())
                .mobileNumber(loan.getMobileNumber())
                .totalLoan(loan.getTotalLoan())
                .amountPaid(loan.getAmountPaid())
                .outstandingAmount(loan.getOutstandingAmount())
                .build();

        return loanDto;
    }
}
