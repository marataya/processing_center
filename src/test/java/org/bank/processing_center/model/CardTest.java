// /home/nemo/dev/ayaibergenov_jan1_processingcenter/src/test/java/org/bank/processing_center/model/CardTest.java
package org.bank.processing_center.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

// Assuming Account, CardStatus, PaymentSystem have at least default constructors and setId/getId
// For Account, ensure it has fields that might be needed if Card's constructor uses them.
// (Account.java provided has @NotNull on its fields, which is good for testAccount setup)

class CardTest {

    private Validator validator;
    private Account testAccount;
    private CardStatus testCardStatus;
    private PaymentSystem testPaymentSystem;
    private Timestamp testTimestampNow;
    private Timestamp testTimestampFuture;


    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        testAccount = new Account(); // Assuming Account has a NoArgsConstructor
        testAccount.setId(1L);
        // For Account to be valid for its own @NotNull constraints if used directly:
        // testAccount.setAccountNumber("VALID_ACC_NUM");
        // testAccount.setBalance(BigDecimal.TEN);
        // testAccount.setCurrency(new Currency()); // Assuming Currency exists and can be valid
        // testAccount.setIssuingBank(new IssuingBank()); // Assuming IssuingBank exists

        testCardStatus = new CardStatus(); // Assuming CardStatus has a NoArgsConstructor
        testCardStatus.setId(1L);

        testPaymentSystem = new PaymentSystem(); // Assuming PaymentSystem has a NoArgsConstructor
        testPaymentSystem.setId(1L);

        testTimestampNow = Timestamp.from(Instant.now());
        testTimestampFuture = Timestamp.valueOf(LocalDate.now().plusDays(1).atStartOfDay());
    }

    private Card createValidCardForBeanValidation() {
        Card card = new Card();
        // Only set fields that have Bean Validation annotations or are part of @AllArgsConstructor
        card.setId(1L);
        card.setCardNumber("1234567890123456"); // Valid for @Size(min=16, max=16)

        // Fields without Bean Validation annotations can be null or have any value for bean validation purposes
        card.setExpirationDate(LocalDate.now().plusYears(1)); // No bean validation on this
        card.setHolderName("John Doe"); // No bean validation on this
        card.setAccount(testAccount); // No @NotNull bean validation
        card.setCardStatus(testCardStatus); // No @NotNull bean validation
        card.setPaymentSystem(testPaymentSystem); // No @NotNull bean validation
        card.setReceivedFromIssuingBank(testTimestampNow); // No @NotNull bean validation
        card.setSentToIssuingBank(testTimestampNow); // No @NotNull bean validation
        return card;
    }

    private Card createFullyPopulatedCard() {
        return new Card(
                1L,
                "1234567890123456",
                LocalDate.now().plusYears(1),
                "John Doe",
                testCardStatus,
                testPaymentSystem,
                testAccount,
                Timestamp.valueOf(LocalDate.now().minusDays(1).atStartOfDay()),
                Timestamp.valueOf(LocalDate.now().atStartOfDay())
        );
    }


    @Test
    void testDefaultConstructor() {
        Card card = new Card();
        assertNull(card.getId());
        assertNull(card.getCardNumber());
        assertNull(card.getExpirationDate());
        assertNull(card.getHolderName());
        assertNull(card.getAccount());
        assertNull(card.getCardStatus());
        assertNull(card.getPaymentSystem());
        assertNull(card.getReceivedFromIssuingBank());
        assertNull(card.getSentToIssuingBank());

        // CardNumber is the only field with a direct Bean Validation constraint (@Size)
        // @Size allows null, so a default card (all nulls) will have NO bean validation violations.
        Set<ConstraintViolation<Card>> violations = validator.validate(card);
        assertTrue(violations.isEmpty(), "Default card should have no bean violations as @Size allows null. Violations: " + violations);
    }

    @Test
    void testAllArgsConstructorAndGetters() {
        LocalDate expiry = LocalDate.now().plusYears(2);
        Timestamp received = Timestamp.from(Instant.now().minusSeconds(3600));
        Timestamp sent = Timestamp.from(Instant.now());

        Card card = new Card(
                1L,
                "1111222233334444",
                expiry,
                "Jane Doe",
                testCardStatus,
                testPaymentSystem,
                testAccount,
                received,
                sent
        );

        assertEquals(1L, card.getId());
        assertEquals("1111222233334444", card.getCardNumber());
        assertEquals(expiry, card.getExpirationDate());
        assertEquals("Jane Doe", card.getHolderName());
        assertEquals(testAccount, card.getAccount());
        assertEquals(testCardStatus, card.getCardStatus());
        assertEquals(testPaymentSystem, card.getPaymentSystem());
        assertEquals(received, card.getReceivedFromIssuingBank());
        assertEquals(sent, card.getSentToIssuingBank());

        // Check bean validation - only cardNumber @Size will be checked
        Set<ConstraintViolation<Card>> violations = validator.validate(card);
        assertTrue(violations.isEmpty(), "Valid card from AllArgsConstructor should have no bean violations. Violations: " + violations);
    }

    @Test
    void testSetters() {
        Card card = new Card();
        LocalDate expiry = LocalDate.of(2027, Month.OCTOBER, 1);
        Timestamp received = Timestamp.valueOf("2023-01-01 10:00:00");
        Timestamp sent = Timestamp.valueOf("2023-01-02 11:00:00");

        card.setId(2L);
        card.setCardNumber("5555666677778888");
        card.setExpirationDate(expiry);
        card.setHolderName("Peter Pan");
        card.setAccount(testAccount);
        card.setCardStatus(testCardStatus);
        card.setPaymentSystem(testPaymentSystem);
        card.setReceivedFromIssuingBank(received);
        card.setSentToIssuingBank(sent);

        assertEquals(2L, card.getId());
        assertEquals("5555666677778888", card.getCardNumber());
        assertEquals(expiry, card.getExpirationDate());
        assertEquals("Peter Pan", card.getHolderName());
        assertEquals(testAccount, card.getAccount());
        assertEquals(testCardStatus, card.getCardStatus());
        assertEquals(testPaymentSystem, card.getPaymentSystem());
        assertEquals(received, card.getReceivedFromIssuingBank());
        assertEquals(sent, card.getSentToIssuingBank());

        Set<ConstraintViolation<Card>> violations = validator.validate(card);
        assertTrue(violations.isEmpty(), "Valid card after setters should have no bean violations. Violations: " + violations);
    }

    @Test
    void testEqualsAndHashCode() {
        Card card1 = createFullyPopulatedCard();
        card1.setId(10L);

        Card card2 = createFullyPopulatedCard();
        card2.setId(10L); // Same ID
        card2.setCardNumber("9999888877776666"); // Different card number

        Card card3 = createFullyPopulatedCard();
        card3.setId(20L); // Different ID

        assertEquals(card1, card1);
        assertEquals(card1.hashCode(), card1.hashCode());

        assertEquals(card1, card2);
        assertEquals(card1.hashCode(), card2.hashCode());

        Card card1Clone = createFullyPopulatedCard();
        card1Clone.setId(10L);
        assertEquals(card1, card1Clone);
        assertEquals(card2, card1Clone);

        assertNotEquals(card1, card3);
        // HashCode is getClass().hashCode(), so it can be the same for non-equal objects
        assertEquals(card1.hashCode(), card3.hashCode());


        assertNotEquals(null, card1);
        assertNotEquals(card1, new Object());

        Card cardNoId1 = createFullyPopulatedCard();
        cardNoId1.setId(null);
        Card cardNoId2 = createFullyPopulatedCard();
        cardNoId2.setId(null);
        assertNotEquals(cardNoId1, cardNoId2, "Cards with null IDs should not be equal by current ID-based equals");
    }

    @Test
    void testToString() {
        Card card = createFullyPopulatedCard();
        String cardString = card.toString();

        assertNotNull(cardString);
        // Matching the exact string format from Card.java, including potential typos (extra apostrophes)
        String expected = "Card{" +
                "id=" + card.getId() +
                ", cardNumber='" + card.getCardNumber() + '\'' +
                ", expirationDate=" + card.getExpirationDate() +
                ", holderName=" + card.getHolderName() + '\'' + // Typo: missing opening quote for value
                ", cardStatus=" + (card.getCardStatus() != null ? card.getCardStatus().getId() : "null") + '\'' +
                ", paymentSystem=" + (card.getPaymentSystem() != null ? card.getPaymentSystem().getId() : "null") + '\'' +
                ", account=" + (card.getAccount() != null ? card.getAccount().getId() : "null") + '\'' +
                ", receivedFromIssuingBank=" + card.getReceivedFromIssuingBank() + '\'' +
                ", sentToIssuingBank=" + card.getSentToIssuingBank() + '\'' +
                '}';
        assertEquals(expected, cardString);
    }

    // --- Bean Validation Specific Tests ---

    private void assertHasBeanViolation(Card card, String propertyName, String messagePart) {
        Set<ConstraintViolation<Card>> violations = validator.validate(card);
        assertTrue(violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals(propertyName) &&
                                (messagePart == null || v.getMessage().contains(messagePart))),
                "Expected bean violation for " + propertyName + (messagePart != null ? " with message containing '" + messagePart + "'" : "") + ". Actual violations: " + violations);
    }

    private void assertNoBeanViolationForProperty(Card card, String propertyName) {
        Set<ConstraintViolation<Card>> violations = validator.validate(card);
        assertTrue(violations.stream().noneMatch(v -> v.getPropertyPath().toString().equals(propertyName)),
                "Expected no bean violation for " + propertyName + " but found: " + violations);
    }


    @Test
    void testBeanValidation_cardNumber_Size() {
        Card card = createValidCardForBeanValidation();

        card.setCardNumber("12345"); // Too short
        assertHasBeanViolation(card, "cardNumber", "Card number must be 16 digits");

        card.setCardNumber("12345678901234567"); // Too long
        assertHasBeanViolation(card, "cardNumber", "Card number must be 16 digits");

        card.setCardNumber("1234567890123456"); // Correct length
        assertNoBeanViolationForProperty(card, "cardNumber");

        // @Size allows null by default
        card.setCardNumber(null);
        assertNoBeanViolationForProperty(card, "cardNumber");
    }

    // No Bean Validation annotations for expirationDate, holderName, account, cardStatus, paymentSystem, timestamps in Card.java
    // So, tests for @NotNull on these using the validator would show no violations.

    @Test
    void testBeanValidation_relatedEntities_AreNotCheckedByBeanValidationForNull() {
        Card card = createValidCardForBeanValidation();
        card.setAccount(null);
        card.setCardStatus(null);
        card.setPaymentSystem(null);
        card.setReceivedFromIssuingBank(null);
        card.setSentToIssuingBank(null);
        card.setExpirationDate(null);
        card.setHolderName(null); // holderName also has no bean validation

        Set<ConstraintViolation<Card>> violations = validator.validate(card);
        assertTrue(violations.isEmpty(), "Expected no bean violations for null related entities/timestamps as they lack @NotNull. Violations: " + violations);
    }


    // --- Custom Method Tests ---
    @Test
    void testCustom_validateCardNumber() {
        Card card = new Card();
        card.setCardNumber("1234567890123456");
        assertTrue(card.validateCardNumber());

        card.setCardNumber("123456789012345"); // Too short
        assertFalse(card.validateCardNumber());

        card.setCardNumber("12345678901234567"); // Too long
        assertFalse(card.validateCardNumber());

        card.setCardNumber("1234-5678-9012-3456"); // With separators
        assertTrue(card.validateCardNumber()); // Cleaned length is 16

        card.setCardNumber("123456789012345A"); // Invalid char
        assertFalse(card.validateCardNumber()); // Cleaned length is 15

        card.setCardNumber(null);
        assertFalse(card.validateCardNumber());
    }

    @Test
    void testCustom_validateExpirationDate() {
        Card card = new Card();
        card.setExpirationDate(LocalDate.now().plusMonths(1));
        assertTrue(card.validateExpirationDate());

        card.setExpirationDate(LocalDate.now().minusMonths(1));
        assertFalse(card.validateExpirationDate());

        card.setExpirationDate(LocalDate.now()); // Today is not after today
        assertFalse(card.validateExpirationDate());

        card.setExpirationDate(null);
        assertFalse(card.validateExpirationDate());
    }

    @Test
    void testCustom_validateHolderName() {
        Card card = new Card();
        card.setHolderName("John Doe");
        assertTrue(card.validateHolderName());

        card.setHolderName("John Doe123"); // Contains digits
        assertFalse(card.validateHolderName());

        card.setHolderName("John_Doe"); // Contains underscore
        assertFalse(card.validateHolderName());

        card.setHolderName(""); // Empty
        assertFalse(card.validateHolderName()); // Matches "false" because it's not ^[a-zA-Z\s]+$

        card.setHolderName(null);
        assertFalse(card.validateHolderName());
    }

    @Test
    void testCustom_isCardExpired() {
        Card card = new Card(); // The argument 'card' to isCardExpired is not used, 'this' is used.
        Card cardArg = new Card(); // Dummy argument, not actually used by the method's logic

        card.setExpirationDate(LocalDate.now().minusDays(1));
        assertTrue(card.isCardExpired(cardArg));

        card.setExpirationDate(LocalDate.now().plusDays(1));
        assertFalse(card.isCardExpired(cardArg));

        card.setExpirationDate(LocalDate.now()); // Expiry today means not yet expired (isBefore check)
        assertFalse(card.isCardExpired(cardArg));
    }

    @Test
    void testCustom_maskCardNumber() {
        Card card = new Card(); // Instance needed to call the method
        assertEquals("**** **** **** 3456", card.maskCardNumber("1234567890123456"));
        assertEquals("**** **** **** 7890", card.maskCardNumber("1234567890")); // Assuming it works if > 4
        assertEquals("123", card.maskCardNumber("123"));
        assertNull(card.maskCardNumber(null));
        assertEquals("", card.maskCardNumber(""));
    }

    @Test
    void testCustom_isValidCardNumber_Luhn() {
        Card card = new Card(); // Instance needed to call the method
        assertTrue(card.isValidCardNumber("49927398716")); // Valid Visa
        assertTrue(card.isValidCardNumber("79927398713")); // Valid generic Luhn
        assertFalse(card.isValidCardNumber("49927398717")); // Invalid
        assertFalse(card.isValidCardNumber("1234567890123456")); // May or may not be Luhn valid
        assertFalse(card.isValidCardNumber(null));
        assertFalse(card.isValidCardNumber("123")); // Too short
        assertFalse(card.isValidCardNumber("123456789012345678901")); // Too long
        assertTrue(card.isValidCardNumber("1234-5678-9012-3452")); // Valid with hyphens
    }

    @Test
    void testCustom_validateCard_Valid() {
        Card card = new Card();
        card.setCardNumber("79927398713"); // Valid Luhn
        card.setExpirationDate(LocalDate.now().plusYears(1));
        card.setHolderName("Valid Name");
        // For validateCard, related objects are not checked for null
        // card.setAccount(testAccount);
        // card.setCardStatus(testCardStatus);
        // card.setPaymentSystem(testPaymentSystem);
        // card.setReceivedFromIssuingBank(testTimestampNow);
        // card.setSentToIssuingBank(testTimestampFuture);

        assertTrue(card.validateCard(card), "Card should be valid");
    }

    @Test
    void testCustom_validateCard_NullCard() {
        Card cardInstance = new Card();
        assertFalse(cardInstance.validateCard(null), "Null card should be invalid");
    }

    @Test
    void testCustom_validateCard_NullCardNumber() {
        Card card = createFullyPopulatedCard();
        card.setCardNumber(null);
        assertFalse(card.validateCard(card), "Card with null number should be invalid");
    }

    @Test
    void testCustom_validateCard_EmptyCardNumber() {
        Card card = createFullyPopulatedCard();
        card.setCardNumber("");
        assertFalse(card.validateCard(card), "Card with empty number should be invalid");
    }

    @Test
    void testCustom_validateCard_InvalidLuhnCardNumber() {
        Card card = createFullyPopulatedCard();
        card.setCardNumber("1234567890123456"); // Not necessarily Luhn valid
        // To ensure it fails Luhn, use a known invalid one if the above passes by chance
        if (card.isValidCardNumber("1234567890123456")) {
            card.setCardNumber("1234567890123450"); // Likely invalid by Luhn
        }
        assertFalse(card.validateCard(card), "Card with invalid Luhn number should be invalid");
    }

    @Test
    void testCustom_validateCard_NullExpirationDate() {
        Card card = createFullyPopulatedCard();
        card.setCardNumber("79927398713"); // Valid Luhn
        card.setExpirationDate(null);
        assertFalse(card.validateCard(card), "Card with null expiration date should be invalid");
    }

    @Test
    void testCustom_validateCard_ExpiredCard() {
        Card card = createFullyPopulatedCard();
        card.setCardNumber("79927398713"); // Valid Luhn
        card.setExpirationDate(LocalDate.now().minusDays(1));
        assertFalse(card.validateCard(card), "Expired card should be invalid");
    }

    @Test
    void testCustom_validateCard_NullHolderName() {
        Card card = createFullyPopulatedCard();
        card.setCardNumber("79927398713"); // Valid Luhn
        card.setHolderName(null);
        assertFalse(card.validateCard(card), "Card with null holder name should be invalid");
    }

    @Test
    void testCustom_validateCard_EmptyHolderName() {
        Card card = createFullyPopulatedCard();
        card.setCardNumber("79927398713"); // Valid Luhn
        card.setHolderName("");
        assertFalse(card.validateCard(card), "Card with empty holder name should be invalid");
    }
}