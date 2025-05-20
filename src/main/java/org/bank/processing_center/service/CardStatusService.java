package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.helper.exception.DaoException;
import org.bank.processing_center.model.CardStatus;

import java.util.List;
import java.util.Optional;

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
    public CardStatus save(CardStatus cardStatus) {
        return cardStatusDao.save(cardStatus);
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
        return cardStatusDao.findById(id).orElseThrow(() -> new DaoException("Card status " + id + " not found"));
    }

    @Override
    public CardStatus update(CardStatus cardStatus) {
        return cardStatusDao.update(cardStatus);
    }

    /**
     * Finds a card status by name
     * @param statusName Status name to search for
     * @return Optional containing the card status if found
     */
    public Optional<CardStatus> findByName(String statusName) {
        return cardStatusDao.findAll().stream()
                .filter(status -> status.getStatusName().equalsIgnoreCase(statusName))
                .findFirst();
    }

    /**
     * Finds a card status name by ID
     * @param id ID of the card status to search for
     * @return The card status name if found, null otherwise
     */
    public String getStatusNameById(Long id) {
        Optional<CardStatus> cardStatusOpt = cardStatusDao.findById(id);
        if (cardStatusOpt.isPresent()) {
            CardStatus cardStatus = cardStatusOpt.get();
            return cardStatus.getStatusName();
        }
        return null;
    }
}
