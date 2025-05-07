package org.bank.processing_center.controller;

import org.bank.processing_center.model.Card;
import org.bank.processing_center.service.CardService;
import org.bank.processing_center.view.ConsoleView;

import java.util.List;
import java.util.Optional;

/**
 * Controller for Card-related operations
 */
public class CardController {

    private final CardService cardService;
    private final ConsoleView view;

    public CardController(CardService cardService, ConsoleView view) {
        this.cardService = cardService;
        this.view = view;
    }

    /**
     * Creates the card table
     */
    public void createCardTable() {
        try {
            cardService.createTable();
            view.showMessage("Таблица card создана успешно.");
        } catch (Exception e) {
            view.showError("Ошибка при создании таблицы card: " + e.getMessage());
        }
    }

    /**
     * Adds a new card
     * @param card Card to add
     */
    public void addCard(Card card) {
        try {
            cardService.save(card);
            view.showMessage("Карта добавлена: " + card);
        } catch (Exception e) {
            view.showError("Ошибка при добавлении карты в card: " + e.getMessage());
        }
    }

    /**
     * Retrieves and displays all cards
     */
    public List<Card> getAllCards() {
        try {
            List<Card> cards = cardService.findAll();
            view.showCardList(cards);
            return cards;
        } catch (Exception e) {
            view.showError("Ошибка при получении списка из card: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Updates a card
     * @param card Card with updated information
     */
    public void updateCard(Card card) {
        try {
            cardService.update(card);
            view.showMessage("Карта обновлена: " + card);
        } catch (Exception e) {
            view.showError("Ошибка при обновлении card: " + e.getMessage());
        }
    }

    /**
     * Finds a card by ID
     * @param id Card ID
     * @return The found card or null
     */
    public Card findCardById(Long id) {
        try {
            Optional<Card> cardOpt = cardService.findById(id);
            if (cardOpt.isPresent()) {
                Card card = cardOpt.get();
                view.showCardDetails(card);
                return card;
            } else {
                view.showMessage("Карта с ID " + id + " не найдена.");
                return null;
            }
        } catch (Exception e) {
            view.showError("Ошибка при поиске карты в card: " + e.getMessage());
            return null;
        }
    }

    /**
     * Clears the card table
     */
    public void clearCardTable() {
        try {
            cardService.clearTable();
            view.showMessage("Таблица карт очищена.");
        } catch (Exception e) {
            view.showError("Ошибка при очистке таблицы card: " + e.getMessage());
        }
    }

    /**
     * Drops the card table
     */
    public void dropCardTable() {
        try {
            cardService.dropTable();
            view.showMessage("Таблица card удалена.");
        } catch (Exception e) {
            view.showError("Ошибка при удалении таблицы card: " + e.getMessage());
        }
    }
}
