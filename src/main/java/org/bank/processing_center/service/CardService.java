package org.bank.processing_center.service;

import org.bank.processing_center.dao.base.Dao;
import org.bank.processing_center.model.Card;

import java.util.List;
import java.util.Optional;

public class CardService implements Service<Card, Long> {

    private final Dao<Card, Long> cardDao;

    public CardService(Dao<Card, Long> cardDao) {
        this.cardDao = cardDao;
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
        cardDao.save(card);
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
        cardDao.update(card);
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
}
