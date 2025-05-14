package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.dao.factory.DaoFactory;
import org.bank.processing_center.model.Card;

import java.util.List;
import java.util.Optional;

public class CardService implements Service<Card, Long> {

    private final Dao<Card, Long> cardDao;

    public CardService(String daoType) {
        DaoFactory daoFactory = DaoFactory.getInstance(daoType);
        this.cardDao = daoFactory.getCardDao();
    }

    @Override
    public void createTable() {
        cardDao.createTable();
    }

    @Override
    public void dropTable() {
        cardDao.dropTable();
    }

    @Override
    public void clearTable() {
        cardDao.clearTable();
    }

    @Override
    public void save(Card card) {
        // Validate card number before saving
        if (validateCard(card)) {
            cardDao.save(card);
        }
    }

    @Override
    public void delete(Long id) {
        cardDao.delete(id);
    }

    @Override
    public List<Card> findAll() {
        return cardDao.findAll();
    }

    @Override
    public Optional<Card> findById(Long id) {
        return cardDao.findById(id);
    }

    @Override
    public void update(Card card) {
        // Validate card number before updating
        if (validateCard(card)) {
            cardDao.update(card);
        }
    }

    /**
     * Checks if a card is expired
     * @param card Card to check
     * @return true if the card is expired, false otherwise
     */
    public boolean isCardExpired(Card card) {
        return card.getExpirationDate().isBefore(java.time.LocalDate.now());
    }

    /**
     * Masks a card number for display (e.g., **** **** **** 1234)
     * @param cardNumber Full card number
     * @return Masked card number
     */
    public String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return cardNumber;
        }

        String lastFourDigits = cardNumber.substring(cardNumber.length() - 4);
        return "**** **** **** " + lastFourDigits;
    }

    /**
     * Validates a card number using the Luhn algorithm
     * @param cardNumber Card number to validate
     * @return true if the card number is valid, false otherwise
     */
    public boolean isValidCardNumber(String cardNumber) {
        if (cardNumber == null) {
            return false;
        }

        // Remove any non-digit characters
        String digitsOnly = cardNumber.replaceAll("\\D", "");

        if (digitsOnly.length() < 13 || digitsOnly.length() > 19) {
            // Most card numbers are between 13 and 19 digits
            return false;
        }

        // Luhn algorithm implementation
        int sum = 0;
        boolean alternate = false;

        // Process digits from right to left
        for (int i = digitsOnly.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(digitsOnly.charAt(i));

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit = digit - 9; // Same as summing the digits (e.g., 12 -> 1+2 = 3, which is 12-9)
                }
            }

            sum += digit;
            alternate = !alternate;
        }

        // If the sum is divisible by 10, the card number is valid
        return sum % 10 == 0;
    }

    /**
     * Validates a card before saving or updating
     * @param card Card to validate
     * @return true if the card is valid, false otherwise
     */
    public boolean validateCard(Card card) {
        if (card == null) {
            System.err.println("Ошибка: Карта не может быть null");
            return false;
        }

        if (card.getCardNumber() == null || card.getCardNumber().isEmpty()) {
            System.err.println("Ошибка: Номер карты не может быть пустым");
            return false;
        }

        if (!isValidCardNumber(card.getCardNumber())) {
            System.err.println("Ошибка: Недействительный номер карты (не прошел проверку по алгоритму Луна): " + card.getCardNumber());
            return false;
        }

        if (card.getExpirationDate() == null) {
            System.err.println("Ошибка: Дата истечения срока действия не может быть null");
            return false;
        }

        if (isCardExpired(card)) {
            System.err.println("Ошибка: Срок действия карты истек: " + card.getExpirationDate());
            return false;
        }

        if (card.getHolderName() == null || card.getHolderName().isEmpty()) {
            System.err.println("Ошибка: Имя держателя карты не может быть пустым");
            return false;
        }

        return true;
    }
}
