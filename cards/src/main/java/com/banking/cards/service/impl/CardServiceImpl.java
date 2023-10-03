package com.banking.cards.service.impl;

import com.banking.cards.constants.CardConstants;
import com.banking.cards.dto.CardDto;
import com.banking.cards.entity.Card;
import com.banking.cards.exceptions.CardAlreadyExistsException;
import com.banking.cards.exceptions.ResourceNotFoundException;
import com.banking.cards.mapper.CardMapper;
import com.banking.cards.repository.CardRepository;
import com.banking.cards.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {

    private CardRepository cardRepository;

    /**
     * Method to create the card
     *
     * @param mobileNumber - Mobile Number of the customer
     */
    @Override
    public void createCard(String mobileNumber) {

        Optional<Card> optionalCard = cardRepository.findByMobileNumber(mobileNumber);
        if(optionalCard.isPresent()) {
            throw new CardAlreadyExistsException(String.format("Card with mobileNumber : %s already exists", mobileNumber));
        }

        Card card = createNewCard(mobileNumber);
        cardRepository.save(card);
    }

    /**
     * Method to fetch the details of the cards
     *
     * @param mobileNumber - Mobile Number of the customer
     * @return CardDto - Object
     */
    @Override
    public CardDto fetchCardDetails(String mobileNumber) {

        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );

        CardDto cardDto = CardMapper.ToDto(card);
        return cardDto;
    }

    /**
     * Method to update the details of the card
     *
     * @param mobileNumber - Mobile Number of the customer
     * @param cardDto      - CardDto Object
     * @return - Boolean - update successful or not
     */
    @Override
    public boolean updateCardDetails(String mobileNumber, CardDto cardDto) {

        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );

        // TODO: Implement the logic to fetch the details by cardNumber instead of mobileNumber

        updateDetails(card, cardDto);
        cardRepository.save(card);

        return true;
    }

    /**
     * Method to delete the card
     *
     * @param mobileNumber - Mobile number of the customer
     * @return - Boolean - Update successful or not.
     */
    @Override
    public boolean deleteCard(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );

        cardRepository.deleteById(card.getCardId());
        return true;
    }

    private void updateDetails(Card card, CardDto cardDto) {
        // mobile Number
        if(Objects.nonNull(cardDto.getMobileNumber()) && !"".equalsIgnoreCase(cardDto.getMobileNumber())) {
            card.setMobileNumber(cardDto.getMobileNumber());
        }

        // Card type
        if(Objects.nonNull(cardDto.getCardType()) && !"".equalsIgnoreCase(cardDto.getCardType())) {
            card.setCardType(cardDto.getCardType());
        }

        // total Limit
        if(cardDto.getTotalLimit() != card.getTotalLimit()) {
            int difference = cardDto.getTotalLimit() - card.getTotalLimit();
            card.setTotalLimit(cardDto.getTotalLimit());
            card.setAvailableAmount(card.getAvailableAmount() + difference);
        }

        // Amount Used
        if(cardDto.getAmountUsed() > 0) {
            card.setAmountUsed(cardDto.getAmountUsed() + card.getAmountUsed());
            card.setAvailableAmount(card.getAvailableAmount() - cardDto.getAmountUsed());
        }

    }

    private Card createNewCard(String mobileNumber) {
        Card newCard = new Card();
        long cardNumber = 100000000000L + new Random().nextInt(900000000);

        newCard.setCardNumber(Long.toString(cardNumber));
        newCard.setCardType(CardConstants.CREDIT_CARD);
        newCard.setMobileNumber(mobileNumber);
        newCard.setTotalLimit(CardConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardConstants.NEW_CARD_LIMIT);

        return newCard;
    }
}
