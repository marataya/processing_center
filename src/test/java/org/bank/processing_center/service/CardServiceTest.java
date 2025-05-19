package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.Account;
import org.bank.processing_center.model.Card;
import org.bank.processing_center.model.CardStatus;
import org.bank.processing_center.model.PaymentSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CardServiceTest {

    @Mock
    private Dao<Card, Long> cardDao;

    private CardService cardService;

    // Helper stubs for related entities
    private CardStatus testCardStatus;
    private PaymentSystem testPaymentSystem;
    private Account testAccount;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cardService = new CardService(cardDao);

        // Initialize helper stubs
        testCardStatus = createStubCardStatus(1L, "Active");
        testPaymentSystem = createStubPaymentSystem(1L, "VISA");
        testAccount = createStubAccount(1L, "ACC123");
    }

    // --- Helper methods to create stubs for related entities ---
    private CardStatus createStubCardStatus(Long id, String name) {
        CardStatus status = new CardStatus();
        status.setId(id);
        status.setStatusName(name);
        return status;
    }

    private PaymentSystem createStubPaymentSystem(Long id, String name) {
        PaymentSystem ps = new PaymentSystem();
        ps.setId(id);
        ps.setPaymentSystemName(name);
        return ps;
    }

    private Account createStubAccount(Long id, String number) {
        Account acc = new Account();
        acc.setId(id);
        acc.setAccountNumber(number);
        // Other necessary fields for Account can be set if CardService logic depends on them
        return acc;
    }
    // --- End of helper methods ---

    @Test
    public void testCreateTable() {
        cardService.createTable();
        verify(cardDao, times(1)).createTable();
    }

    @Test
    public void testDropTable() {
        cardService.dropTable();
        verify(cardDao, times(1)).dropTable();
    }

    @Test
    public void testClearTable() {
        cardService.clearTable();
        verify(cardDao, times(1)).clearTable();
    }

    @Test
    public void testSaveValidCard() {
        Card card = new Card(
                null, // ID is null for new entity
                "4111111111111111", // Valid VISA card number
                LocalDate.now().plusYears(2),
                "John Doe",
                testCardStatus,
                testPaymentSystem,
                testAccount,
                Timestamp.valueOf(LocalDateTime.now()),
                null
        );

        cardService.save(card);
        verify(cardDao, times(1)).save(card);
    }

    @Test
    public void testSaveInvalidCard_InvalidNumber() {
        Card card = new Card(
                null,
                "1234567890123456", // Invalid card number
                LocalDate.now().plusYears(2),
                "John Doe",
                testCardStatus,
                testPaymentSystem,
                testAccount,
                Timestamp.valueOf(LocalDateTime.now()),
                null
        );

        cardService.save(card);
        verify(cardDao, never()).save(card);
    }

    @Test
    public void testSaveInvalidCard_Expired() {
        Card card = new Card(
                null,
                "4111111111111111",
                LocalDate.now().minusYears(1), // Expired
                "John Doe",
                testCardStatus,
                testPaymentSystem,
                testAccount,
                Timestamp.valueOf(LocalDateTime.now()),
                null
        );

        cardService.save(card);
        verify(cardDao, never()).save(card);
    }


    @Test
    public void testDelete() {
        Long id = 1L;
        cardService.delete(id);
        verify(cardDao, times(1)).delete(id);
    }

    @Test
    public void testFindAll() {
        CardStatus otherStatus = createStubCardStatus(2L, "Blocked");
        PaymentSystem otherPs = createStubPaymentSystem(2L, "MasterCard");
        Account otherAccount = createStubAccount(2L, "ACC456");

        List<Card> expectedCards = Arrays.asList(
                new Card(1L, "4111111111111111", LocalDate.now(), "John Doe", testCardStatus, testPaymentSystem, testAccount, null, null),
                new Card(2L, "5555555555554444", LocalDate.now(), "Jane Smith", otherStatus, otherPs, otherAccount, null, null)
        );

        when(cardDao.findAll()).thenReturn(expectedCards);

        List<Card> actualCards = cardService.findAll();
        assertEquals(expectedCards, actualCards);
        verify(cardDao, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Card expectedCard = new Card(id, "4111111111111111", LocalDate.now(), "John Doe", testCardStatus, testPaymentSystem, testAccount, null, null);

        when(cardDao.findById(id)).thenReturn(Optional.of(expectedCard));

        Optional<Card> actualCard = cardService.findById(id);
        assertTrue(actualCard.isPresent());
        assertEquals(expectedCard, actualCard.get());
        verify(cardDao, times(1)).findById(id);
    }

    @Test
    public void testUpdateValidCard() {
        Card card = new Card(
                1L, // ID is present for update
                "4111111111111111", // Valid VISA card number
                LocalDate.now().plusYears(2),
                "John Doe Updated",
                testCardStatus,
                testPaymentSystem,
                testAccount,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()) // Assuming sentToIssuingBank is set on update
        );

        cardService.update(card);
        verify(cardDao, times(1)).update(card);
    }

    @Test
    public void testUpdateInvalidCard_InvalidNumber() {
        Card card = new Card(
                1L,
                "1234567890123456", // Invalid card number
                LocalDate.now().plusYears(2),
                "John Doe Updated",
                testCardStatus,
                testPaymentSystem,
                testAccount,
                Timestamp.valueOf(LocalDateTime.now()),
                null
        );

        cardService.update(card);
        verify(cardDao, never()).update(card);
    }

    @Test
    public void testUpdateInvalidCard_Expired() {
        Card card = new Card(
                1L,
                "4111111111111111",
                LocalDate.now().minusYears(1), // Expired
                "John Doe Updated",
                testCardStatus,
                testPaymentSystem,
                testAccount,
                Timestamp.valueOf(LocalDateTime.now()),
                null
        );

        cardService.update(card);
        verify(cardDao, never()).update(card);
    }

    @Test
    public void testIsCardExpired() {
        // Expired card
        Card expiredCard = new Card(
                1L, "4111111111111111", LocalDate.now().minusYears(1), "John Doe",
                testCardStatus, testPaymentSystem, testAccount, null, null
        );

        // Valid card
        Card validCard = new Card(
                2L, "4111111111111111", LocalDate.now().plusYears(1), "Jane Smith",
                testCardStatus, testPaymentSystem, testAccount, null, null
        );

        assertTrue(cardService.isCardExpired(expiredCard));
        assertFalse(cardService.isCardExpired(validCard));
    }

    @Test
    public void testMaskCardNumber() {
        String cardNumber = "4111111111111111";
        String maskedNumber = cardService.maskCardNumber(cardNumber);
        assertEquals("**** **** **** 1111", maskedNumber);
    }

    @Test
    public void testIsValidCardNumber() {
        // Valid card numbers
        assertTrue(cardService.isValidCardNumber("4111111111111111")); // Visa
        assertTrue(cardService.isValidCardNumber("5555555555554444")); // Mastercard
        assertTrue(cardService.isValidCardNumber("378282246310005")); // American Express
        assertTrue(cardService.isValidCardNumber("6011111111111117")); // Discover

        // Invalid card numbers
        assertFalse(cardService.isValidCardNumber("1234567890123456"));
        assertFalse(cardService.isValidCardNumber("4111111111111112")); // Fails Luhn
        assertFalse(cardService.isValidCardNumber(null));
        assertFalse(cardService.isValidCardNumber(""));
        assertFalse(cardService.isValidCardNumber("12345"));
    }

    @Test
    public void testValidateCard() {
        // Valid card
        Card validCard = new Card(
                1L, "4111111111111111", LocalDate.now().plusYears(2), "John Doe",
                testCardStatus, testPaymentSystem, testAccount, null, null
        );

        // Card with invalid number
        Card invalidNumberCard = new Card(
                2L, "1234567890123456", LocalDate.now().plusYears(2), "Jane Smith",
                testCardStatus, testPaymentSystem, testAccount, null, null
        );

        // Expired card
        Card expiredCard = new Card(
                3L, "4111111111111111", LocalDate.now().minusYears(1), "Bob Johnson",
                testCardStatus, testPaymentSystem, testAccount, null, null
        );

        // Card with no holder name
        Card noHolderCard = new Card(
                4L, "4111111111111111", LocalDate.now().plusYears(2), "", // Empty holder name
                testCardStatus, testPaymentSystem, testAccount, null, null
        );

        assertTrue(cardService.validateCard(validCard));
        assertFalse(cardService.validateCard(invalidNumberCard));
        assertFalse(cardService.validateCard(expiredCard));
        assertFalse(cardService.validateCard(noHolderCard));
        assertFalse(cardService.validateCard(null)); // Test with null card
    }
}