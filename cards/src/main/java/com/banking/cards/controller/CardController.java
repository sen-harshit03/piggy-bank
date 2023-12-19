package com.banking.cards.controller;

import com.banking.cards.constants.CardConstants;
import com.banking.cards.dto.CardDto;
import com.banking.cards.dto.CardsContactInfoDto;
import com.banking.cards.dto.ErrorResponseDto;
import com.banking.cards.dto.ResponseDto;
import com.banking.cards.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "REST APIs for Card Service", description = "REST APIs to perform CRUD operations in the card service")
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CardController {

    private static final Logger log = LoggerFactory.getLogger(CardController.class);

    private CardService cardService;

    private CardsContactInfoDto cardsContactInfoDto;

    public CardController(CardService cardService, CardsContactInfoDto cardsContactInfoDto) {
        this.cardService = cardService;
        this.cardsContactInfoDto = cardsContactInfoDto;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Operation(description = "REST APIs to create cards in the piggy Bank")
    @ApiResponse(responseCode = "201", description = "HttpsStatus.CREATED")
    @PostMapping(path = "/create")
    public ResponseEntity<ResponseDto> createCard(@RequestParam
                                                      @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be of 10 digits")
                                                       String mobileNumber) {

        cardService.createCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardConstants.STATUS_201, CardConstants.MESSAGE_201));
    }


    @Operation(description = "REST APIs to fetch card details in the piggy Bank")
    @ApiResponse(responseCode = "200", description = "HttpsStatus.OK")
    @GetMapping(path = "/fetch")
    public ResponseEntity<CardDto> fetchCardDetails(
            @RequestHeader("piggybank-correlation-id") String correlationId,
            @RequestParam @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile Number must be of 10 digits")
                                                        String mobileNumber) {

        log.debug("Calling Cards service to fetch card details");
        CardDto cardDto = cardService.fetchCardDetails(mobileNumber);
        log.debug("Successfully fetched the cards details");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardDto);
    }


    @Operation(description = "REST APIs to update card details in the piggy Bank")
    @ApiResponses( {
            @ApiResponse(responseCode = "200", description = "HttpsStatus.OK"),
            @ApiResponse(responseCode = "417", description = "HttpsStatus.EXCEPTION_FAILED"),
            @ApiResponse(responseCode = "500", description = "HttpsStatus.INTERNAL_SERVER_ERROR", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            ))
    })
    @PutMapping(path = "/update")
    public ResponseEntity<ResponseDto> updateCardDetails(@RequestParam
                                                             @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile Number must be of 10 digits")
                                                             String mobileNumber, @Valid @RequestBody CardDto cardDto) {
        boolean isUpdated = cardService.updateCardDetails(mobileNumber, cardDto);

        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardConstants.STATUS_200, CardConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardConstants.STATUS_417, CardConstants.MESSAGE_417_UPDATE));
        }
    }


    @Operation(description = "REST APIs to delete cards in the piggy Bank")
    @ApiResponses( {
            @ApiResponse(responseCode = "200", description = "HttpsStatus.OK"),
            @ApiResponse(responseCode = "417", description = "HttpsStatus.EXCEPTION_FAILED"),
            @ApiResponse(responseCode = "500", description = "HttpsStatus.INTERNAL_SERVER_ERROR", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            ))
    })
    @DeleteMapping(path = "/delete")
    public ResponseEntity<ResponseDto> deleteCard(@RequestParam
                                                      @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile Number must be of 10 digits")
                                                      String mobileNumber) {
        boolean isDeleted = cardService.deleteCard(mobileNumber);

        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardConstants.STATUS_200, CardConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardConstants.STATUS_417, CardConstants.MESSAGE_417_DELETE));
        }
    }

    @GetMapping(path = "/contact-info")
    public ResponseEntity<CardsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsContactInfoDto);
    }

    @GetMapping(path = "/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

}
