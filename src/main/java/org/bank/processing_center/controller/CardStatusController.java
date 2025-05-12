package org.bank.processing_center.controller;

import org.bank.processing_center.model.CardStatus;
import org.bank.processing_center.service.CardStatusService;
import org.bank.processing_center.view.ConsoleView;
import java.util.List;
import java.util.Optional;

/**
 * Controller for CardStatus-related operations
 */
public class CardStatusController implements Controller<CardStatus, Long> {

    private final CardStatusService cardStatusService;
    private final ConsoleView view;

    public CardStatusController(CardStatusService cardStatusService, ConsoleView view) {
        this.cardStatusService = cardStatusService;
        this.view = view;
    }

    /**
     * Creates the entity table
     */
    @Override
    public void createTable() {
        try {
            cardStatusService.createTable();
            view.showMessage("Таблица статусов карт создана успешно.");
        } catch (Exception e) {
            view.showError("Ошибка при создании таблицы card_status: " + e.getMessage());
        }
    }

    /**
     * Adds a new entity
     * 
     * @param cardStatus Entity to add
     */
    @Override
    public void addEntity(CardStatus cardStatus) {
        try {
            cardStatusService.save(cardStatus);
            view.showMessage("Статус карты добавлен: " + cardStatus);
        } catch (Exception e) {
            view.showError("Ошибка при добавлении card_status: " + e.getMessage());
        }
    }

    /**
     * Retrieves and displays all entities
     */
    @Override
    public List<CardStatus> getAllEntities() {
        try {
            List<CardStatus> statuses = cardStatusService.findAll();
            view.showList(statuses, "Card Statuses List:");
            return statuses;
        } catch (Exception e) {
            view.showError("Ошибка при получении списка card_status: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Clears the entity table
     */
    @Override
    public void clearTable() {
        try {
            cardStatusService.clearTable();
            view.showMessage("Таблица card_status очищена.");
        } catch (Exception e) {
            view.showError("Ошибка при очистке таблицы статусов карт: " + e.getMessage());
        }
    }

    /**
     * Drops the entity table
     */
    @Override
    public void dropTable() {
        try {
            cardStatusService.dropTable();
            view.showMessage("Таблица статусов карт удалена.");
        } catch (Exception e) {
            view.showError("Ошибка при удалении таблицы card_status: " + e.getMessage());
        }
    }

    /**
     * Deletes an entity by ID
     * 
     * @param id Entity ID
     */
    @Override
    public void deleteEntity(Long id) {
        try {
            cardStatusService.delete(id);
            view.showMessage("Статус карты с ID " + id + " удален.");
        } catch (Exception e) {
            view.showError("Ошибка при удалении card_status с ID " + id + ": " + e.getMessage());
        }
    }

    /**
     * Finds an entity by ID
     * 
     * @param id Entity ID
     * @return Optional containing the entity if found
     */
    @Override
    public Optional<CardStatus> findById(Long id) {
        try {
            return cardStatusService.findById(id);
        } catch (Exception e) {
            view.showError("Ошибка при поиске card_status по ID " + id + ": " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Updates an existing entity
     * 
     * @param cardStatus Entity to update
     */
    @Override
    public void updateEntity(CardStatus cardStatus) {
        cardStatusService.update(cardStatus);
        view.showMessage("Card Status updated: " + cardStatus);
    }

}
