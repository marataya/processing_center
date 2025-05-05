package org.bank.processing_center.controller;

import org.bank.processing_center.model.Account;
import org.bank.processing_center.model.Card;
import org.bank.processing_center.model.CardStatus;
import org.bank.processing_center.model.PaymentSystem;
import org.bank.processing_center.view.ConsoleView;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Main controller that coordinates the application flow
 */
public class ApplicationController {

    private final CardController cardController;
    private final AccountController accountController;
    private final CardStatusController cardStatusController;
    private final PaymentSystemController paymentSystemController;
    private final ConsoleView view;

    public ApplicationController() {
        ControllerFactory factory = ControllerFactory.getInstance();
        this.cardController = factory.getCardController();
        this.accountController = factory.getAccountController();
        this.cardStatusController = factory.getCardStatusController();
        this.paymentSystemController = factory.getPaymentSystemController();
        this.view = factory.getView();
    }

    /**
     * Runs the application according to the specified algorithm
     */
    public void run() {
        try {
            // Step 1: Create tables
            createTables();

            // Step 2: Add sample data
            addSampleData();

            // Step 3: Get all cards and display them
            List<Card> cards = cardController.getAllCards();

            // Step 4: Update two cards
            updateTwoCards(cards);

            // Step 5: Clear tables
            clearTables();

            // Step 6: Drop tables
//            dropTables();

            view.showMessage("Приложение завершило работу успешно.");
        } catch (Exception e) {
            view.showError("Ошибка при выполнении приложения: " + e.getMessage());
        }
    }

    private void createTables() {
        view.showMessage("=== Создание таблиц ===");
        cardStatusController.createCardStatusTable();
        paymentSystemController.createPaymentSystemTable();
        accountController.createAccountTable();
        cardController.createCardTable();
    }

    private void addSampleData() {
        view.showMessage("=== Добавление данных ===");

        // Add card statuses
        CardStatus activeStatus = new CardStatus(1L, "Active");
        CardStatus blockedStatus = new CardStatus(2L, "Blocked");
        cardStatusController.addCardStatus(activeStatus);
        cardStatusController.addCardStatus(blockedStatus);

        // Add payment systems
        PaymentSystem visa = new PaymentSystem(1L, "VISA");
        PaymentSystem mastercard = new PaymentSystem(2L, "MasterCard");
        paymentSystemController.addPaymentSystem(visa);
        paymentSystemController.addPaymentSystem(mastercard);

        // Add accounts
        Account account1 = new Account(1L, "40817810123456789012", 10000.0, 1L, 1L);
        Account account2 = new Account(2L, "40817810987654321098", 5000.0, 1L, 1L);
        accountController.addAccount(account1);
        accountController.addAccount(account2);

        // Add cards
        Card card1 = new Card(
                1L,
                "4111111111111111",
                LocalDate.now().plusYears(3),
                "IVAN IVANOV",
                1L, // Active status
                1L, // VISA
                1L, // Account 1
                Timestamp.valueOf(LocalDateTime.now().minusDays(10)),
                null
        );

        Card card2 = new Card(
                2L,
                "5500000000000004",
                LocalDate.now().plusYears(2),
                "PETR PETROV",
                1L, // Active status
                2L, // MasterCard
                1L, // Account 1
                Timestamp.valueOf(LocalDateTime.now().minusDays(5)),
                null
        );

        Card card3 = new Card(
                3L,
                "4222222222222222",
                LocalDate.now().plusYears(4),
                "SERGEY SERGEEV",
                1L, // Active status
                1L, // VISA
                2L, // Account 2
                Timestamp.valueOf(LocalDateTime.now().minusDays(3)),
                null
        );

        Card card4 = new Card(
                4L,
                "5555555555554444",
                LocalDate.now().plusYears(1),
                "ANNA PETROVA",
                2L, // Blocked status
                2L, // MasterCard
                2L, // Account 2
                Timestamp.valueOf(LocalDateTime.now().minusDays(1)),
                Timestamp.valueOf(LocalDateTime.now())
        );

        cardController.addCard(card1);
        cardController.addCard(card2);
        cardController.addCard(card3);
        cardController.addCard(card4);
    }

    private void updateTwoCards(List<Card> cards) {
        if (cards.size() >= 2) {
            view.showMessage("=== Обновление карт ===");

            // Update first card
            Card card1 = cards.get(0);
            card1.setHolderName(card1.getHolderName() + " UPDATED");
            card1.setSentToIssuingBank(Timestamp.valueOf(LocalDateTime.now()));
            cardController.updateCard(card1);

            // Update second card
            Card card2 = cards.get(1);
            card2.setCardStatusId(2L); // Change to blocked
            card2.setSentToIssuingBank(Timestamp.valueOf(LocalDateTime.now()));
            cardController.updateCard(card2);

            // Show updated cards
            cardController.getAllCards();
        } else {
            view.showError("Недостаточно карт для обновления");
        }
    }

    private void clearTables() {
        view.showMessage("=== Очистка таблиц ===");
        cardController.clearCardTable();
        accountController.clearAccountTable();
        paymentSystemController.clearPaymentSystemTable();
        cardStatusController.clearCardStatusTable();
    }

    private void dropTables() {
        view.showMessage("=== Удаление таблиц ===");
        cardController.dropCardTable();
        accountController.dropAccountTable();
        paymentSystemController.dropPaymentSystemTable();
        cardStatusController.dropCardStatusTable();
    }
}
