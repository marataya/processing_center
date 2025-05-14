package org.bank.processing_center.controller;

import org.bank.processing_center.controller.factory.ControllerFactory;
import org.bank.processing_center.model.*;
import org.bank.processing_center.view.ConsoleView;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
    private final AcquiringBankController acquiringBankController;
    private final MerchantCategoryCodeController merchantCategoryCodeController;
    private final TransactionTypeController transactionTypeController;
    private final ResponseCodeController responseCodeController;
    private final TerminalController terminalController;
    private final TransactionController transactionController;
    private final SalesPointController salesPointController;
    private final ConsoleView view;

    public ApplicationController(String daoType) {
        ControllerFactory factory = ControllerFactory.getInstance(daoType);
        this.cardController = factory.getCardController();
        this.accountController = factory.getAccountController();
        this.cardStatusController = factory.getCardStatusController();
        this.currencyController = factory.getCurrencyController();
        this.issuingBankController = factory.getIssuingBankController();
        this.paymentSystemController = factory.getPaymentSystemController();
        this.acquiringBankController = factory.getAcquiringBankController();
        this.merchantCategoryCodeController = factory.getMerchantCategoryCodeController();
        this.transactionTypeController = factory.getTransactionTypeController();
        this.responseCodeController = factory.getResponseCodeController();
        this.terminalController = factory.getTerminalController();
        this.transactionController = factory.getTransactionController();
        this.salesPointController = factory.getSalesPointController();

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


            // Step 2: Clear tables
            view.showMessage("Step 1.5: Clearing tables to ensure a clean state for sample data...");
            clearTables();
            view.showMessage("Step 1.5: Tables cleared.");


            // Step 3: Add sample data
            view.showMessage("Step 2: Adding sample data...");
            addSampleData();
            view.showMessage("Step 2: Sample data added.");

            // Step 4: Get all cards and display them
            view.showMessage("Step 3: Getting all cards...");
            List<Card> cards = cardController.getAllEntities();
            view.showMessage("Step 3: Retrieved " + cards.size() + " cards.");
            view.showMessage("Step 3: Displaying card details:");
            for (Card card : cards) {
                view.showMessage(card.toString()); // Assuming Card has a meaningful toString()
            }

            // Step 5: Update two cards
            view.showMessage("Step 4: Updating two cards...");
            updateTwoCards(cards);
            view.showMessage("Step 4: Cards updated.");
            // Optionally, retrieve and display updated cards again
            view.showMessage("Step 4: Displaying updated card details:");
            List<Card> updatedCards = cardController.getAllEntities();
            for (Card card : updatedCards) {
                view.showMessage(card.toString());
            }

            // Step 6: Drop tables
            view.showMessage("Step 6: Dropping tables...");
            dropTables();
            view.showMessage("Step 6: Tables dropped.");

            view.showMessage("Приложение завершило работу успешно.");
        } catch (Exception e) {
            view.showError("Ошибка при выполнении приложения: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createTables() {
        // Independent / Lookup Tables (few or no dependencies)
        cardStatusController.createTable();
        paymentSystemController.createTable();
        currencyController.createTable();           // Create Currency first
        issuingBankController.createTable();        // Create IssuingBank first
        acquiringBankController.createTable();      // Assuming independent or few dependencies
        merchantCategoryCodeController.createTable(); // Assuming independent
        transactionTypeController.createTable();    // Assuming independent
        responseCodeController.createTable();       // Assuming independent

        // Tables with dependencies on the above
        accountController.createTable();            // Now Account can be created (depends on Currency, IssuingBank)
        salesPointController.createTable();         // Order based on its dependencies
        terminalController.createTable();           // Order based on its dependencies (e.g., might depend on SalesPoint)

        // Tables with potentially many dependencies
        cardController.createTable();               // Now Card can be created (depends on Account, CardStatus, PaymentSystem)
        transactionController.createTable();        // Likely depends on many others (Card, Terminal, etc.)
    }


    private void addSampleData() {
        // --- Add Currencies ---
        Currency usd = new Currency(1L, "840", "USD", "Доллар США");
        Currency eur = new Currency(2L, "978", "EUR", "Евро"); // Assuming 810 is RUB for display
        currencyController.addEntity(usd);
        currencyController.addEntity(eur);

        // --- Add Issuing Banks ---
        IssuingBank issuingBank1 = new IssuingBank(1L, "041234570", "12345", "ПАО банк-эмитент №1");
        IssuingBank issuingBank2 = new IssuingBank(2L, "016516515", "65432", "ПАО банк-эмитент №2");
        issuingBankController.addEntity(issuingBank1);
        issuingBankController.addEntity(issuingBank2);

        // --- Add Card Statuses ---
        CardStatus activeStatus = new CardStatus(1L, "Active");
        CardStatus blockedStatus = new CardStatus(2L, "Blocked");
        cardStatusController.addEntity(activeStatus);
        cardStatusController.addEntity(blockedStatus);

        // --- Add Payment Systems ---
        PaymentSystem visa = new PaymentSystem(1L, "VISA");
        PaymentSystem mastercard = new PaymentSystem(2L, "MasterCard");
        paymentSystemController.addEntity(visa);
        paymentSystemController.addEntity(mastercard);

        // --- Add Acquiring Banks ---
        AcquiringBank acquiringBank1 = new AcquiringBank(1L, "049876543", "Банк-эквайер Альфа");
        AcquiringBank acquiringBank2 = new AcquiringBank(2L, "041122334", "Банк-эквайер Бета");
        acquiringBankController.addEntity(acquiringBank1);
        acquiringBankController.addEntity(acquiringBank2);

        // --- Add Merchant Category Codes (MCC) ---
        MerchantCategoryCode mccGroceries = new MerchantCategoryCode(1L, "5411", "Grocery Stores, Supermarkets");
        MerchantCategoryCode mccRestaurants = new MerchantCategoryCode(2L, "5812", "Eating Places, Restaurants");
        merchantCategoryCodeController.addEntity(mccGroceries);
        merchantCategoryCodeController.addEntity(mccRestaurants);

        // --- Add Transaction Types ---
        TransactionType purchase = new TransactionType(1L, "Standard purchase transaction");
        TransactionType refund = new TransactionType(2L, "Refund transaction");
        transactionTypeController.addEntity(purchase);
        transactionTypeController.addEntity(refund);

        // --- Add Response Codes ---
        ResponseCode approved = new ResponseCode(1L, "00", "Approved or completed successfully", "OK");
        ResponseCode declined = new ResponseCode(2L, "05", "Do not honor / Declined", "CRITICAL");
        responseCodeController.addEntity(approved);
        responseCodeController.addEntity(declined);

        // --- Add Accounts ---
        // Assuming IDs are auto-generated or can be set. For sample data, we'll set them.
        Account account1 = new Account(1L, "40817810123456789012", new BigDecimal("10000.00"), usd, issuingBank1);
        Account account2 = new Account(2L, "40817810987654321098", new BigDecimal("5000.00"), eur, issuingBank2);
        accountController.addEntity(account1);
        accountController.addEntity(account2);

        // --- Add Sales Points ---
        // Assuming IDs are auto-generated or can be set.
        SalesPoint salesPoint1 = new SalesPoint(1L, "SuperMarket Central N1", "123 Main St, Cityville", "1234567890", acquiringBank1);
        SalesPoint salesPoint2 = new SalesPoint(2L, "Cafe Downtown N1", "456 Oak Ave, Townsville", "1234567891", acquiringBank2);
        salesPointController.addEntity(salesPoint1);
        salesPointController.addEntity(salesPoint2);

        // --- Add Terminals ---
        // Assuming IDs are auto-generated or can be set.
        Terminal terminal1 = new Terminal(1L, "000000001", mccGroceries, salesPoint1);
        Terminal terminal2 = new Terminal(2L, "000000002", mccRestaurants, salesPoint2);
        terminalController.addEntity(terminal1);
        terminalController.addEntity(terminal2);

        // --- Add Cards ---
        Random random = new Random();
        Card card1 = new Card(
                1L, "4111111111111111", LocalDate.now().plusYears(3), "IVAN IVANOV",
                activeStatus, visa, account1,
                Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusMonths(24)));
        Card card2 = new Card(
                2L, "5222222222222222", LocalDate.now().plusYears(random.nextInt(5) + 1), "PETR PETROV",
                blockedStatus, mastercard, account1, // Using blockedStatus and account1 for variety
                Timestamp.valueOf(LocalDateTime.now().minusDays(random.nextInt(365))),
                random.nextBoolean() ? Timestamp.valueOf(LocalDateTime.now().plusDays(random.nextInt(365))) : null);
        Card card3 = new Card(
                3L, "4333333333333333", LocalDate.now().plusYears(random.nextInt(5) + 1), "ANNA SIDOROVA",
                activeStatus, visa, account2,
                Timestamp.valueOf(LocalDateTime.now().minusDays(random.nextInt(365))),
                random.nextBoolean() ? Timestamp.valueOf(LocalDateTime.now().plusDays(random.nextInt(365))) : null);
        Card card4 = new Card(
                4L, "5444444444444444", LocalDate.now().plusYears(random.nextInt(5) + 1), "OLGA KOZLOVA",
                activeStatus, mastercard, account2,
                Timestamp.valueOf(LocalDateTime.now().minusDays(random.nextInt(365))), null);
        String luhnValidVisaNumber = "4444333322221111"; // This is a common test number that should pass Luhn
        Card card5 = new Card(
                5L, luhnValidVisaNumber, LocalDate.now().plusYears(2), "LUHN VALIDATOR",
                activeStatus, visa, account1, // Assign to existing account for simplicity
                Timestamp.valueOf(LocalDateTime.now().minusDays(10)), // createdAt
                null // sentToIssuingBank
        );


        cardController.addEntity(card1);
        cardController.addEntity(card2);
        cardController.addEntity(card3);
        cardController.addEntity(card4);
        cardController.addEntity(card5);

        // --- Add Transactions ---
        // Assuming IDs are auto-generated or can be set.
        Transaction transaction1 = new Transaction(1L, LocalDate.now(), new BigDecimal("120.50"), "Purchase at SuperMarket Central N1",
                account1, purchase, card1, terminal1, approved, null,
                LocalDateTime.now().minusHours(1), LocalDateTime.now()
        );

        Transaction transaction2 = new Transaction(2L, LocalDate.now().minusDays(1), new BigDecimal("50.00"), "Refund for previous purchase", account2, refund, card5, terminal2, approved, null, LocalDateTime.now().minusDays(1).minusHours(2), LocalDateTime.now().minusDays(1)
        );


        transactionController.addEntity(transaction1);
        transactionController.addEntity(transaction2);
    }


    private void updateTwoCards(List<Card> cards) {
        if (cards.size() >= 2) {
            // Update first card
            Card cardToUpdate1 = cards.get(0); // Use a different variable name
            cardToUpdate1.setHolderName(cardToUpdate1.getHolderName() + " UPDATED");
            cardToUpdate1.setSentToIssuingBank(Timestamp.valueOf(LocalDateTime.now()));
            cardController.updateEntity(cardToUpdate1);

            // Update second card
            Card cardToUpdate2 = cards.get(1); // Use a different variable name
            // Fetch the persisted blockedStatus to ensure it's a managed entity
            Optional<CardStatus> persistedBlockedStatus = cardStatusController.findById(2L);
            persistedBlockedStatus.ifPresentOrElse(cardToUpdate2::setCardStatus, () -> {
                view.showError("Blocked status (ID 2L) not found for update. Using new instance.");
                cardToUpdate2.setCardStatus(new CardStatus(2L, "Blocked")); // Fallback, less ideal
            });

            cardToUpdate2.setSentToIssuingBank(Timestamp.valueOf(LocalDateTime.now()));
            cardController.updateEntity(cardToUpdate2);
        } else {
            view.showError("Недостаточно карт для обновления");
        }
    }

    private void clearTables() {
        transactionController.clearTable();
        cardController.clearTable();
        terminalController.clearTable();
        salesPointController.clearTable();
        accountController.clearTable();
        responseCodeController.clearTable();
        transactionTypeController.clearTable();
        merchantCategoryCodeController.clearTable();
        acquiringBankController.clearTable();
        issuingBankController.clearTable();
        currencyController.clearTable();
        paymentSystemController.clearTable();
        cardStatusController.clearTable();
    }

    private void dropTables() {
        transactionController.dropTable();
        cardController.dropTable();
        terminalController.dropTable();
        salesPointController.dropTable();
        accountController.dropTable();
        responseCodeController.dropTable();
        transactionTypeController.dropTable();
        merchantCategoryCodeController.dropTable();
        acquiringBankController.dropTable();
        issuingBankController.dropTable();
        currencyController.dropTable();
        paymentSystemController.dropTable();
        cardStatusController.dropTable();
    }
}
