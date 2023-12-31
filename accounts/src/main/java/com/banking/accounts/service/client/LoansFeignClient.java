package com.banking.accounts.service.client;

import com.banking.accounts.dto.CardDto;
import com.banking.accounts.dto.LoanDto;
import com.banking.accounts.service.client.fallbacks.LoansFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans", fallback = LoansFallback.class)
public interface LoansFeignClient {

    @GetMapping(path = "/api/fetch")
    public ResponseEntity<LoanDto> fetchLoanDetails(
            @RequestHeader("piggybank-correlation-id") String correlationId,
            @RequestParam String mobileNumber);
}
