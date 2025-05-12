package org.bank.processing_center.controller;

import org.bank.processing_center.controller.factory.ControllerFactory;
import org.bank.processing_center.model.Account;
import org.bank.processing_center.model.Card;
import org.bank.processing_center.model.CardStatus;
import org.bank.processing_center.model.PaymentSystem;
import org.bank.processing_center.model.Currency;
import org.bank.processing_center.model.IssuingBank;
import org.bank.processing_center.view.ConsoleView;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * Main controller that coordinates the application flow
 */
public class ApplicationController {

    private final CardController cardController;
    private final AccountController accountController;
    private final CardStatusController cardStatusController;
    private final PaymentSystemController paymentSystemController;
    private final CurrencyController currencyController;
    private final IssuingBankController issuingBankController;
    private final ConsoleView view;

    public ApplicationController(String daoType) {
        ControllerFactory factory = ControllerFactory.getInstance(daoType);
        this.cardController = factory.getCardController();
        this.accountController = factory.getAccountController();
        this.cardStatusController = factory.getCardStatusController();
        this.currencyController = factory.getCurrencyController();
        this.issuingBankController = factory.getIssuingBankController();
        this.paymentSystemController = factory.getPaymentSystemController();
        this.view = factory.getView();
    }

    /**
     * Runs the application according to the specified algorithm
     */
    public void run() {
        view.showMessage("Starting Application Run...");
        try {
            // Step 1: Create tables
            view.showMessage("Step 1: Creating tables...");
            createTables();
            view.showMessage("Step 1: Tables created.");

            // Step 2: Add sample data
            view.showMessage("Step 2: Adding sample data...");
            addSampleData();
            view.showMessage("Step 2: Sample data added.");

            // Step 3: Get all cards and display them
            view.showMessage("Step 3: Getting all cards...");
            List<Card> cards = cardController.getAllEntities();
            view.showMessage("Step 3: Retrieved " + cards.size() + " cards.");
            view.showMessage("Step 3: Displaying card details:");
            for (Card card : cards) {
                view.showMessage(card.toString()); // Assuming Card has a meaningful toString()
            }

            // Step 4: Update two cards
            view.showMessage("Step 4: Updating two cards...");
            updateTwoCards(cards);
            view.showMessage("Step 4: Cards updated.");
            // Optionally, retrieve and display updated cards again
            view.showMessage("Step 4: Displaying updated card details:");
            List<Card> updatedCards = cardController.getAllEntities();
            for (Card card : updatedCards) {
                view.showMessage(card.toString());
            }

            // Step 5: Clear tables
            view.showMessage("Step 5: Clearing tables...");
            clearTables();
            view.showMessage("Step 5: Tables cleared.");

            // Step 6: Drop tables
            view.showMessage("Step 6: Dropping tables...");
            dropTables();
            view.showMessage("Step 6: Tables dropped.");

            view.showMessage("Приложение завершило работу успешно.");
        } catch (Exception e) {
            view.showError("Ошибка при выполнении приложения: " + e.getMessage());
        }
    }

    private void createTables() {
        cardStatusController.createTable();
        paymentSystemController.createTable();
        accountController.createTable();
        cardController.createTable();
    }

    private void addSampleData() {
        addSampleCurrencies();
        addSampleIssuingBanks();

        // Add card statuses
        CardStatus activeStatus = new CardStatus(Long.valueOf(1L), "Active");
        CardStatus blockedStatus = new CardStatus(Long.valueOf(2L), "Blocked");
        cardStatusController.addEntity(activeStatus);
        cardStatusController.addEntity(blockedStatus);

        // Add payment systems
        PaymentSystem visa = new PaymentSystem(1L, "VISA");
        PaymentSystem mastercard = new PaymentSystem(2L, "MasterCard");
        paymentSystemController.addEntity(visa);
        paymentSystemController.addEntity(mastercard);

        // Add accounts
        Account account1 = new Account(null, "40817810123456789012", new BigDecimal("10000.00"),
                new Currency(1L, "840", "USD", "810", "Доллар США"),
                new IssuingBank(null, "041234570", "12345", "ПАО банк-эмитент №1"));
        Account account2 = new Account(null, "40817810987654321098", new BigDecimal("5000.00"),
                new Currency(1L, "840", "USD", "810", "Доллар США"),
                new IssuingBank(null, "016516515", "65432", "ПАО банк-эмитент №2"));
        accountController.addEntity(account1);
        accountController.addEntity(account2);

        // Add cards
        Random random = new Random();

        Card card1 = new Card(
                1L,
                "4111111111111111",
                LocalDate.now().plusYears(3),
                "IVAN IVANOV",
                new CardStatus(), // Active status
                new PaymentSystem(1L, "VISA"), // VISA
                account1,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now().plusMonths(24)));
        Card card2 = new Card(
                2L,
                "5222222222222222",
                LocalDate.now().plusYears(random.nextInt(5) + 1),
                "PETR PETROV",
                random.nextBoolean() ? activeStatus : blockedStatus,
                random.nextBoolean() ? visa : mastercard,
                random.nextBoolean() ? account1 : account2,
                Timestamp.valueOf(LocalDateTime.now().minusDays(random.nextInt(365))),
                random.nextBoolean() ? Timestamp.valueOf(LocalDateTime.now().plusDays(random.nextInt(365))) : null);
        Card card3 = new Card(
                3L,
                "4333333333333333",
                LocalDate.now().plusYears(random.nextInt(5) + 1),
                "ANNA SIDOROVA",
                random.nextBoolean() ? activeStatus : blockedStatus,
                random.nextBoolean() ? visa : mastercard,
                random.nextBoolean() ? account1 : account2,
                Timestamp.valueOf(LocalDateTime.now().minusDays(random.nextInt(365))),
                random.nextBoolean() ? Timestamp.valueOf(LocalDateTime.now().plusDays(random.nextInt(365))) : null);
        Card card4 = new Card(
                4L,
                "5444444444444444",
                LocalDate.now().plusYears(random.nextInt(5) + 1),
                "OLGA KOZLOVA",
                activeStatus,
                mastercard,
                account2, Timestamp.valueOf(LocalDateTime.now().minusDays(random.nextInt(365))), null);
        cardController.addEntity(card1);
        cardController.addEntity(card2);
        cardController.addEntity(card3);

        cardController.addEntity(card1);
        cardController.addEntity(card2);
        cardController.addEntity(card3);
        cardController.addEntity(card4);
    }

    private void addSampleCurrencies() {
        Currency usd = new Currency(null, "840", "USD", "810", "Доллар США");
        currencyController.addEntity(usd);
    }

    private void addSampleIssuingBanks() {
        IssuingBank bankA = new IssuingBank(null, "016516515", "65432", "ПАО банк-эмитент №2");
        issuingBankController.addEntity(bankA);
    }

    private void updateTwoCards(List<Card> cards) {
        if (cards.size() >= 2) {
            // Update first card
            Card card1 = cards.get(0);
            card1.setHolderName(card1.getHolderName() + " UPDATED");
            card1.setSentToIssuingBank(Timestamp.valueOf(LocalDateTime.now()));
            cardController.updateEntity(card1);

            // Update second card
            Card card2 = cards.get(1);
            card2.setCardStatus(new CardStatus(2L, "Blocked")); // Change to blocked
            card2.setSentToIssuingBank(Timestamp.valueOf(LocalDateTime.now()));
            cardController.updateEntity(card2); // This update happens here
        } else {
            view.showError("Недостаточно карт для обновления");
        }
    }

    private void clearTables() {
        cardStatusController.clearTable();
        paymentSystemController.clearTable();
        accountController.clearTable();
        cardController.clearTable();
        currencyController.clearTable();
        issuingBankController.clearTable();
    }

    private void dropTables() {
        cardController.dropTable();
        accountController.dropTable();
        currencyController.dropTable();
        issuingBankController.dropTable();
        paymentSystemController.dropTable();
    }
}
