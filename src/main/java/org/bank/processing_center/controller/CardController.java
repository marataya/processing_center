package org.bank.processing_center.controller;

import org.bank.processing_center.model.Card;
import org.bank.processing_center.service.CardService;
import org.bank.processing_center.view.ConsoleView;

import java.util.List;

/**
 * Controller for Card-related operations
 */
public class CardController implements Controller<Card, Long> {

    private final CardService cardService;
    private final ConsoleView view;

    public CardController(CardService cardService, ConsoleView view) {
        this.cardService = cardService;
        this.view = view;
    }

    /**
     * Creates the card table
     */
    @Override
    public void createTable() {
        try {
            cardService.createTable();
            view.showMessage("Таблица card создана успешно.");
        } catch (Exception e) {
            view.showError("Ошибка при создании таблицы card: " + e.getMessage());
        }
    }

    /**
     * Adds a new card
     * 
     * @param card Card to add
     */
    @Override
    public Card addEntity(Card card) {
        try {
            Card savedCard = cardService.save(card);
            view.showMessage("Карта добавлена: " + savedCard);
            return savedCard;
        } catch (IllegalArgumentException e) {
            view.showError("Ошибка валидации карты: " + e.getMessage());
            return null;
        } catch (Exception e) {
            view.showError("Ошибка при добавлении карты: " + e.getMessage());
            return null;
        }
    }

    /**
     * Saves a card and displays a success message
     *
     * @param card The card to be saved
     */
    public void saveCard(Card card) {
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
    @Override
    public List<Card> getAllEntities() {
        try {
            List<Card> cards = cardService.findAll();
            view.showList(cards, "Cards List:");
            return cards;
        } catch (Exception e) {
            view.showError("Ошибка при получении списка из card: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Updates a card
     * 
     * @param card Card with updated information
     */
    @Override
    public Card updateEntity(Card card) {
        try {
            Card updatedCard = cardService.update(card);
            view.showMessage("Карта обновлена: " + updatedCard);
            return updatedCard;
        } catch (Exception e) {
            view.showError("Error updating card: " + e.getMessage());
            return null;
        }
    }

    /**
     * Finds a card by ID
     * 
     * @param id Entity ID
     * @return The found card or null
     */
    @Override
    public Card findById(Long id) {
        try {
            Card card = cardService.findById(id);
            if (card != null) {
                view.showMessage(card.toString());
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
    @Override
    public void clearTable() {
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
    @Override
    public void dropTable() {
        try {
            cardService.dropTable();
            view.showMessage("Таблица card удалена.");
        } catch (Exception e) {
            view.showError("Ошибка при удалении таблицы card: " + e.getMessage());
        }
    }

    @Override
    public void deleteEntity(Long id) {
        try {
            cardService.delete(id);
            view.showMessage("Карта с ID " + id + " удалена.");
        } catch (Exception e) {
            view.showError("Ошибка при удалении карты с ID " + id + " из card: " + e.getMessage());
        }
    }
}
