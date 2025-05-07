package org.bank.processing_center.controller;

import org.bank.processing_center.model.CardStatus;
import org.bank.processing_center.service.CardStatusService;
import org.bank.processing_center.view.ConsoleView;

import java.util.List;

/**
 * Controller for CardStatus-related operations
 */
public class CardStatusController {

    private final CardStatusService cardStatusService;
    private final ConsoleView view;

    public CardStatusController(CardStatusService cardStatusService, ConsoleView view) {
        this.cardStatusService = cardStatusService;
        this.view = view;
    }

    /**
     * Creates the card status table
     */
    public void createCardStatusTable() {
        try {
            cardStatusService.createTable();
            view.showMessage("Таблица статусов карт создана успешно.");
        } catch (Exception e) {
            view.showError("Ошибка при создании таблицы card_status: " + e.getMessage());
        }
    }

    /**
     * Adds a new card status
     * @param cardStatus CardStatus to add
     */
    public void addCardStatus(CardStatus cardStatus) {
        try {
            cardStatusService.save(cardStatus);
            view.showMessage("Статус карты добавлен: " + cardStatus);
        } catch (Exception e) {
            view.showError("Ошибка при добавлении card_status: " + e.getMessage());
        }
    }

    /**
     * Retrieves and displays all card statuses
     */
    public List<CardStatus> getAllCardStatuses() {
        try {
            List<CardStatus> statuses = cardStatusService.findAll();
            view.showCardStatusList(statuses);
            return statuses;
        } catch (Exception e) {
            view.showError("Ошибка при получении списка card_status: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Clears the card status table
     */
    public void clearCardStatusTable() {
        try {
            cardStatusService.clearTable();
            view.showMessage("Таблица card_status очищена.");
        } catch (Exception e) {
            view.showError("Ошибка при очистке таблицы статусов карт: " + e.getMessage());
        }
    }

    /**
     * Drops the card status table
     */
    public void dropCardStatusTable() {
        try {
            cardStatusService.dropTable();
            view.showMessage("Таблица статусов карт удалена.");
        } catch (Exception e) {
            view.showError("Ошибка при удалении таблицы card_status: " + e.getMessage());
        }
    }
}
