package com.banking.gatewayserver.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {

    @RequestMapping("/contactsupport")
    public Mono<String> getContactSupport() {
        return Mono.just("An error occurred. Please try after some time or contact the support team");
    }
}
