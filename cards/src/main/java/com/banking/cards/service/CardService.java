package com.banking.cards.service;

import com.banking.cards.dto.CardDto;

public interface CardService {
    /**
     * Method to create the card
     * @param mobileNumber - Mobile Number of the customer
     */
    void createCard(String mobileNumber);

    /**
     * Method to fetch the details of the cards
     * @param mobileNumber - Mobile Number of the customer
     * @return CardDto - Object
     */
    CardDto fetchCardDetails(String mobileNumber);

    /**
     * Method to update the details of the card
     * @param mobileNumber - Mobile Number of the customer
     * @param cardDto - CardDto Object
     * @return - Boolean - update successful or not
     */
    boolean updateCardDetails(String mobileNumber, CardDto cardDto);

    /**
     * Method to delete the card
     * @param mobileNumber - Mobile number of the customer
     * @return - Boolean - Update successful or not.
     */
    boolean deleteCard(String mobileNumber);
}
