package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.CardStatus;

import java.util.List;

public class CardStatusService implements Service<CardStatus, Long> {

    private final Dao<CardStatus, Long> cardStatusDao;

    public CardStatusService(Dao<CardStatus, Long> dao) {
        this.cardStatusDao = dao;
    }

    @Override
    public void createTable() {
        cardStatusDao.createTable();
    }

    @Override
    public void dropTable() {
        cardStatusDao.dropTable();
    }

    @Override
    public void clearTable() {
        cardStatusDao.clearTable();
    }

    @Override
    public void save(CardStatus cardStatus) {
        cardStatusDao.save(cardStatus);
    }

    @Override
    public void delete(Long id) {
        cardStatusDao.delete(id);
    }

    @Override
    public List<CardStatus> findAll() {
        return cardStatusDao.findAll();
    }

    @Override
    public CardStatus findById(Long id) {
        return cardStatusDao.findById(id);
    }

    @Override
    public void update(CardStatus cardStatus) {
        cardStatusDao.update(cardStatus);
    }

    /**
     * Finds a card status by name
     * @param statusName Status name to search for
     * @return card status if found
     */
    public CardStatus findByName(String statusName) {
        return cardStatusDao.findAll().stream().
                filter(status -> status.getStatusName().equalsIgnoreCase(statusName)).
                findFirst().orElse(null);
    }

    /**
     * Finds a card status name by ID
     * @param id ID of the card status to search for
     * @return The card status name if found, null otherwise
     */
    public String getStatusNameById(Long id) {
        CardStatus cardStatus = cardStatusDao.findById(id);
        if (cardStatus != null) {
            return cardStatus.getStatusName();
        }
        return null;
    }
}
