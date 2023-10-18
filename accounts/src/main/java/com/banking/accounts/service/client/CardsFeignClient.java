package com.banking.accounts.service.client;

import com.banking.accounts.dto.CardDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards")
public interface CardsFeignClient {

    @GetMapping(path = "/api/fetch")
    public ResponseEntity<CardDto> fetchCardDetails(@RequestParam String mobileNumber);
}
