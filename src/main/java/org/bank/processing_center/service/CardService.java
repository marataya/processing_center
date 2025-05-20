package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.Card;

import java.util.List;

public class CardService implements Service<Card, Long> {

    private final Dao<Card, Long> cardDao;

    public CardService(Dao<Card, Long> dao) {
        this.cardDao = dao;
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
    public Card save(Card card) {
        // Validate card before saving using the method in the Card entity
        if (card != null && card.validateCard(card)) { // Assuming Card entity has a validate() method now
            return cardDao.save(card);
        } else {
            // Optional: Log or handle the validation failure if card is null or validation fails
            if (card == null) {
                System.err.println("Ошибка: Карта для сохранения не может быть null.");
            }
            return null;
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
    public Card findById(Long id) {
        return cardDao.findById(id);
    }

    @Override
    public Card update(Card card) {
        // Validate card before updating using the method in the Card entity
        if (card != null && card.validateCard(card)) { // Assuming Card entity has a validate() method now
            return cardDao.update(card);
        } else {
            // Optional: Log or handle the validation failure if card is null or validation fails
            if (card == null) {
                System.err.println("Ошибка: Карта для обновления не может быть null.");
            }
            return null;
        }
    }

}
