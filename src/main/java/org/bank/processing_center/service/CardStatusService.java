package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.dao.factory.DaoFactory;
import org.bank.processing_center.model.CardStatus;

import java.util.List;
import java.util.Optional;

public class CardStatusService implements Service<CardStatus, Long> {

    private final Dao<CardStatus, Long> cardStatusDao;

    public CardStatusService(String daoType) {
        DaoFactory daoFactory = DaoFactory.getInstance(daoType);
        this.cardStatusDao = daoFactory.getCardStatusDao();
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
    public Optional<CardStatus> findById(Long id) {
        return cardStatusDao.findById(id);
    }

    @Override
    public void update(CardStatus cardStatus) {
        cardStatusDao.update(cardStatus);
    }

    /**
     * Finds a card status by name
     * @param statusName Status name to search for
     * @return Optional containing the card status if found
     */
    public Optional<CardStatus> findByName(String statusName) {
        return cardStatusDao.findAll().stream()
                .filter(status -> status.getStatus_name().equalsIgnoreCase(statusName))
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
            return cardStatus.getStatus_name();
        }
        return null;
    }
}
