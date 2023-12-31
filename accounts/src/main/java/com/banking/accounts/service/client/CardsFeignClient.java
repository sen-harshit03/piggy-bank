package com.banking.accounts.service.client;

import com.banking.accounts.dto.CardDto;
import com.banking.accounts.service.client.fallbacks.CardsFallback;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cards", fallback = CardsFallback.class)
public interface CardsFeignClient {

    @GetMapping(path = "/api/fetch")
    public ResponseEntity<CardDto> fetchCardDetails(
            @RequestHeader("piggybank-correlation-id") String correlationId,
            @RequestParam String mobileNumber);
}
