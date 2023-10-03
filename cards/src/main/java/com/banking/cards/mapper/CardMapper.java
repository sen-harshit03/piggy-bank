package com.banking.cards.mapper;

import com.banking.cards.dto.CardDto;
import com.banking.cards.entity.Card;

public class CardMapper {

    public static Card FromDto(CardDto cardDto) {
        Card card = new Card();
        card.setCardNumber(cardDto.getCardNumber());
        card.setCardType(cardDto.getCardType());
        card.setMobileNumber(cardDto.getMobileNumber());
        card.setTotalLimit(cardDto.getTotalLimit());
        card.setAmountUsed(cardDto.getAmountUsed());
        card.setAvailableAmount(cardDto.getAvailableAmount());

        return card;
    }

    public static CardDto ToDto(Card card) {
        CardDto cardDto = CardDto.builder()
                .cardNumber(card.getCardNumber())
                .cardType(card.getCardType())
                .mobileNumber(card.getMobileNumber())
                .totalLimit(card.getTotalLimit())
                .amountUsed(card.getAmountUsed())
                .availableAmount(card.getAvailableAmount())
                .build();

        return cardDto;
    }
}
