package com.banking.accounts.mapper;

import com.banking.accounts.dto.AccountDto;
import com.banking.accounts.entity.Account;

public class AccountMapper {

    public static Account fromDto(AccountDto accountDto) {
        return Account.builder()
                .accountNumber(accountDto.getAccountNumber())
                .accountType(accountDto.getAccountType())
                .branchAddress(accountDto.getBranchAddress())
                .build();
    }

    public static AccountDto ToDto(Account account) {
        return AccountDto.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .branchAddress(account.getBranchAddress())
                .build();
    }
}
