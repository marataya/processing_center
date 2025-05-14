//package org.bank.processing_center.dao.hibernate;
//
//import org.bank.processing_center.model.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.sql.SQLException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class TransactionHibernateDaoImplTest {
//
//    private TransactionHibernateDaoImpl transactionDao;
//    private CardHibernateDaoImpl cardDao;
//    private AccountHibernateDaoImpl accountDao;
//
//    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//    @BeforeEach
//    public void setUp() {
//        transactionDao = new TransactionHibernateDaoImpl();
//        cardDao = new CardHibernateDaoImpl();
//        accountDao = new AccountHibernateDaoImpl();
//
//        // Ensure tables exist
//        try {
//            transactionDao.createTable();
//            cardDao.createTable();
//            accountDao.createTable();
//        } catch (Exception e) {
//            // Tables might already exist, that's fine
//        }
//    }
//
//    @Test
//    public void testSaveAndFindById() throws ParseException {
//        // Create test data
//        Account account = createTestAccount(1L);
//        Card card = createTestCard(1L, account);
//        Terminal terminal = createTestTerminal(1L);
//        TransactionType transactionType = createTestTransactionType(1L);
//        ResponseCode responseCode = createTestResponseCode(1L);
//
//        // Create a transaction
//        Transaction transaction = new Transaction();
//        transaction.setId(1L);
//        transaction.setTransactionDate(dateFormat.parse("2023-01-15"));
//        transaction.setSum(100.0);
//        transaction.setTransactionName("Purchase");
//        transaction.setAccount(account);
//        transaction.setCard(card);
//        transaction.setTerminal(terminal);
//        transaction.setTransactionType(transactionType);
//        transaction.setResponseCode(responseCode);
//        transaction.setAuthorizationCode("123456");
//
//        // Save the transaction
//        transactionDao.save(transaction);
//
//        // Find the transaction by ID
//        Optional<Transaction> foundTransaction = transactionDao.findById(1L);
//
//        // Verify the transaction was found
//        assertTrue(foundTransaction.isPresent());
//        assertEquals("Purchase", foundTransaction.get().getTransactionName());
//        assertEquals(100.0, foundTransaction.get().getSum());
//    }
//
//    @Test
//    public void testFindByCardId() throws SQLException, ParseException {
//        // Create test data
//        Account account = createTestAccount(2L);
//        Card card = createTestCard(2L, account);
//        Terminal terminal = createTestTerminal(2L);
//        TransactionType transactionType = createTestTransactionType(2L);
//        ResponseCode responseCode = createTestResponseCode(2L);
//
//        // Create transactions for the same card
//        Transaction transaction1 = new Transaction();
//        transaction1.setId(2L);
//        transaction1.setTransactionDate(dateFormat.parse("2023-02-15"));
//        transaction1.setSum(200.0);
//        transaction1.setTransactionName("Purchase 1");
//        transaction1.setAccount(account);
//        transaction1.setCard(card);
//        transaction1.setTerminal(terminal);
//        transaction1.setTransactionType(transactionType);
//        transaction1.setResponseCode(responseCode);
//        transaction1.setAuthorizationCode("234567");
//
//        Transaction transaction2 = new Transaction();
//        transaction2.setId(3L);
//        transaction2.setTransactionDate(dateFormat.parse("2023-02-16"));
//        transaction2.setSum(300.0);
//        transaction2.setTransactionName("Purchase 2");
//        transaction2.setAccount(account);
//        transaction2.setCard(card);
//        transaction2.setTerminal(terminal);
//        transaction2.setTransactionType(transactionType);
//        transaction2.setResponseCode(responseCode);
//        transaction2.setAuthorizationCode("345678");
//
//        // Save the transactions
//        transactionDao.save(transaction1);
//        transactionDao.save(transaction2);
//
//        // Find transactions by card ID
//        List<Transaction> transactions = transactionDao.findByCardId(2L);
//
//        // Verify transactions were found
//        assertEquals(2, transactions.size());
//    }
//
//    @Test
//    public void testFindByAccountId() throws SQLException, ParseException {
//        // Create test data
//        Account account = createTestAccount(3L);
//        Card card1 = createTestCard(3L, account);
//        Card card2 = createTestCard(4L, account);
//        Terminal terminal = createTestTerminal(3L);
//        TransactionType transactionType = createTestTransactionType(3L);
//        ResponseCode responseCode = createTestResponseCode(3L);
//
//        // Create transactions for different cards but same account
//        Transaction transaction1 = new Transaction();
//        transaction1.setId(4L);
//        transaction1.setTransactionDate(dateFormat.parse("2023-03-15"));
//        transaction1.setSum(400.0);
//        transaction1.setTransactionName("Purchase 3");
//        transaction1.setAccount(account);
//        transaction1.setCard(card1);
//        transaction1.setTerminal(terminal);
//        transaction1.setTransactionType(transactionType);
//        transaction1.setResponseCode(responseCode);
//        transaction1.setAuthorizationCode("456789");
//
//        Transaction transaction2 = new Transaction();
//        transaction2.setId(5L);
//        transaction2.setTransactionDate(dateFormat.parse("2023-03-16"));
//        transaction2.setSum(500.0);
//        transaction2.setTransactionName("Purchase 4");
//        transaction2.setAccount(account);
//        transaction2.setCard(card2);
//        transaction2.setTerminal(terminal);
//        transaction2.setTransactionType(transactionType);
//        transaction2.setResponseCode(responseCode);
//        transaction2.setAuthorizationCode("567890");
//
//        // Save the transactions
//        transactionDao.save(transaction1);
//        transactionDao.save(transaction2);
//
//        // Find transactions by account ID
//        List<Transaction> transactions = transactionDao.findByAccountId(3L);
//
//        // Verify transactions were found
//        assertEquals(2, transactions.size());
//    }
//
//    @Test
//    public void testFindByDateRange() throws SQLException, ParseException {
//        // Create test data
//        Account account = createTestAccount(4L);
//        Card card = createTestCard(5L, account);
//        Terminal terminal = createTestTerminal(4L);
//        TransactionType transactionType = createTestTransactionType(4L);
//        ResponseCode responseCode = createTestResponseCode(4L);
//
//        // Create transactions with different dates
//        Transaction transaction1 = new Transaction();
//        transaction1.setId(6L);
//        transaction1.setTransactionDate(dateFormat.parse("2023-04-10"));
//        transaction1.setSum(600.0);
//        transaction1.setTransactionName("Purchase 5");
//        transaction1.setAccount(account);
//        transaction1.setCard(card);
//        transaction1.setTerminal(terminal);
//        transaction1.setTransactionType(transactionType);
//        transaction1.setResponseCode(responseCode);
//        transaction1.setAuthorizationCode("678901");
//
//        Transaction transaction2 = new Transaction();
//        transaction2.setId(7L);
//        transaction2.setTransactionDate(dateFormat.parse("2023-04-15"));
//        transaction2.setSum(700.0);
//        transaction2.setTransactionName("Purchase 6");
//        transaction2.setAccount(account);
//        transaction2.setCard(card);
//        transaction2.setTerminal(terminal);
//        transaction2.setTransactionType(transactionType);
//        transaction2.setResponseCode(responseCode);
//        transaction2.setAuthorizationCode("789012");
//
//        Transaction transaction3 = new Transaction();
//        transaction3.setId(8L);
//        transaction3.setTransactionDate(dateFormat.parse("2023-04-20"));
//        transaction3.setSum(800.0);
//        transaction3.setTransactionName("Purchase 7");
//        transaction3.setAccount(account);
//        transaction3.setCard(card);
//        transaction3.setTerminal(terminal);
//        transaction3.setTransactionType(transactionType);
//        transaction3.setResponseCode(responseCode);
//        transaction3.setAuthorizationCode("890123");
//
//        // Save the transactions
//        transactionDao.save(transaction1);
//        transactionDao.save(transaction2);
//        transactionDao.save(transaction3);
//
//        // Find transactions by date range
//        List<Transaction> transactions = transactionDao.findByDateRange(
//            dateFormat.parse("2023-04-12"),
//            dateFormat.parse("2023-04-18")
//        );
//
//        // Verify transactions were found
//        assertEquals(1, transactions.size());
//        assertEquals("Purchase 6", transactions.get(0).getTransactionName());
//    }
//
//    @Test
//    public void testUpdate() throws SQLException, ParseException {
//        // Create test data
//        Account account = createTestAccount(5L);
//        Card card = createTestCard(6L, account);
//        Terminal terminal = createTestTerminal(5L);
//        TransactionType transactionType = createTestTransactionType(5L);
//        ResponseCode responseCode = createTestResponseCode(5L);
//
//        // Create a transaction
//        Transaction transaction = new Transaction();
//        transaction.setId(9L);
//        transaction.setTransactionDate(dateFormat.parse("2023-05-15"));
//        transaction.setSum(900.0);
//        transaction.setTransactionName("Purchase 8");
//        transaction.setAccount(account);
//        transaction.setCard(card);
//        transaction.setTerminal(terminal);
//        transaction.setTransactionType(transactionType);
//        transaction.setResponseCode(responseCode);
//        transaction.setAuthorizationCode("901234");
//
//        // Save the transaction
//        transactionDao.save(transaction);
//
//        // Update the transaction
//        transaction.setSum(950.0);
//        transactionDao.update(transaction);
//
//        // Find the transaction by ID
//        Optional<Transaction> foundTransaction = transactionDao.findById(9L);
//
//        // Verify the transaction was updated
//        assertTrue(foundTransaction.isPresent());
//        assertEquals(950.0, foundTransaction.get().getSum());
//    }
//
//    @Test
//    public void testDelete() throws SQLException, ParseException {
//        // Create test data
//        Account account = createTestAccount(6L);
//        Card card = createTestCard(7L, account);
//        Terminal terminal = createTestTerminal(6L);
//        TransactionType transactionType = createTestTransactionType(6L);
//        ResponseCode responseCode = createTestResponseCode(6L);
//
//        // Create a transaction
//        Transaction transaction = new Transaction();
//        transaction.setId(10L);
//        transaction.setTransactionDate(dateFormat.parse("2023-06-15"));
//        transaction.setSum(1000.0);
//        transaction.setTransactionName("Purchase 9");
//        transaction.setAccount(account);
//        transaction.setCard(card);
//        transaction.setTerminal(terminal);
//        transaction.setTransactionType(transactionType);
//        transaction.setResponseCode(responseCode);
//        transaction.setAuthorizationCode("012345");
//
//        // Save the transaction
//        transactionDao.save(transaction);
//
//        // Delete the transaction
//        transactionDao.delete(transaction);
//
//        // Find the transaction by ID
//        Optional<Transaction> foundTransaction = transactionDao.findById(10L);
//
//        // Verify the transaction was deleted
//        assertFalse(foundTransaction.isPresent());
//    }
//
//    // Helper methods to create test data
//    private Account createTestAccount(Long id) {
//        Currency currency = new Currency();
//        currency.setId(id);
//        currency.setCurrencyDigitalCode("840");
//        currency.setCurrencyLetterCode("USD");
//        currency.setCurrencyName("US Dollar");
//
//        IssuingBank issuingBank = new IssuingBank();
//        issuingBank.setId(id);
//        issuingBank.setBic("TESTBIC" + id);
//        issuingBank.setBin("TEST" + id);
//        issuingBank.setAbbreviatedName("Test Bank " + id);
//
//        Account account = new Account();
//        account.setId(id);
//        account.setAccountNumber("ACC" + id);
//        account.setBalance(1000.0 * id);
//        account.setCurrency(currency);
//        account.setIssuingBank(issuingBank);
//
//        try {
//            accountDao.save(account);
//        } catch (SQLException e) {
//            fail("Failed to create test account: " + e.getMessage());
//        }
//
//        return account;
//    }
//
//    private Card createTestCard(Long id, Account account) {
//        CardStatus cardStatus = new CardStatus();
//        cardStatus.setId(id);
//        cardStatus.setCardStatusName("Active");
//
//        PaymentSystem paymentSystem = new PaymentSystem();
//        paymentSystem.setId(id);
//        paymentSystem.setPaymentSystemName("Test Payment System " + id);
//
//        Card card = new Card();
//        card.setId(id);
//        card.setCardNumber("CARD" + id);
//        card.setExpirationDate(new Date());
//        card.setHolderName("Test Holder " + id);
//        card.setCardStatus(cardStatus);
//        card.setPaymentSystem(paymentSystem);
//        card.setAccount(account);
//
//        try {
//            cardDao.save(card);
//        } catch (SQLException e) {
//            fail("Failed to create test card: " + e.getMessage());
//        }
//
//        return card;
//    }
//
//    private Terminal createTestTerminal(Long id) {
//        MerchantCategoryCode mcc = new MerchantCategoryCode();
//        mcc.setId(id);
//        mcc.setMcc("MCC" + id);
//        mcc.setMccName("Test MCC " + id);
//
//        AcquiringBank acquiringBank = new AcquiringBank();
//        acquiringBank.setId(id);
//        acquiringBank.setBic("ACQBIC" + id);
//        acquiringBank.setAbbreviatedName("Test Acquiring Bank " + id);
//
//        SalesPoint salesPoint = new SalesPoint();
//        salesPoint.setId(id);
//        salesPoint.setPosName("Test POS " + id);
//        salesPoint.setPosAddress("Test Address " + id);
//        salesPoint.setPosInn("INN" + id);
//        salesPoint.setAcquiringBank(acquiringBank);
//
//        Terminal terminal = new Terminal();
//        terminal.setId(id);
//        terminal.setTerminalId("TERM" + id);
//        terminal.setMerchantCategoryCode(mcc);
//        terminal.setSalesPoint(salesPoint);
//
//        return terminal;
//    }
//
//    private TransactionType createTestTransactionType(Long id) {
//        TransactionType transactionType = new TransactionType();
//        transactionType.setId(id);
//        transactionType.setTransactionTypeName("Test Transaction Type " + id);
//        transactionType.setOperator(id % 2 == 0 ? "+" : "-");
//
//        return transactionType;
//    }
//
//    private ResponseCode createTestResponseCode(Long id) {
//        ResponseCode responseCode = new ResponseCode();
//        responseCode.setId(id);
//        responseCode.setErrorCode("E" + id);
//        responseCode.setErrorDescription("Test Error " + id);
//        responseCode.setErrorLevel("Level " + id);
//
//        return responseCode;
//    }
//}
