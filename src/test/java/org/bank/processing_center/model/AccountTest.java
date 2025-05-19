// /home/nemo/dev/ayaibergenov_jan1_processingcenter/src/test/java/org/bank/processing_center/model/AccountTest.java
package org.bank.processing_center.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Set;

// Assuming Currency and IssuingBank classes exist and have necessary methods (e.g., setId, getId, setCode)
// For simplicity, we'll assume they have default constructors and setters.

// Example stub for Currency (if not available or to simplify):
// class Currency {
//     private Long id;
//     private String code;
//     private String name;
//     public Currency() {}
//     public Long getId() { return id; }
//     public void setId(Long id) { this.id = id; }
//     public String getCode() { return code; }
//     public void setCode(String code) { this.code = code; }
//     public String getName() { return name; }
//     public void setName(String name) { this.name = name; }
//     // equals and hashCode if needed for direct comparison in tests
// }

// Example stub for IssuingBank (if not available or to simplify):
// class IssuingBank {
//     private Long id;
//     private String name;
//     public IssuingBank() {}
//     public Long getId() { return id; }
//     public void setId(Long id) { this.id = id; }
//     public String getName() { return name; }
//     public void setName(String name) { this.name = name; }
//     // equals and hashCode if needed
// }


class AccountTest {
    private Validator validator;
    private Currency testCurrency;
    private IssuingBank testIssuingBank;

    @BeforeEach
    void setUp() {
        // Setup Bean Validation validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        // Create valid instances of related entities for tests
        testCurrency = new Currency();
        testCurrency.setId(1L);
        testCurrency.setCurrencyLetterCode("USD");

        testIssuingBank = new IssuingBank();
        testIssuingBank.setId(1L);
        testIssuingBank.setAbbreviatedName("Test Bank");
    }
    @Test
    void testDefaultConstructor() {
        Account account = new Account();
        assertNull(account.getId(), "ID should be null for default constructor");
        assertNull(account.getAccountNumber(), "Account number should be null for default constructor");
        assertNull(account.getBalance(), "Balance should be null for default constructor");
        assertNull(account.getCurrency(), "Currency should be null for default constructor");
        assertNull(account.getIssuingBank(), "IssuingBank should be null for default constructor");
    }

    @Test
    void testAllArgsConstructor() {
        Long id = 1L;
        String accountNumber = "1234567890";
        BigDecimal balance = new BigDecimal("1000.50");
        Currency currency = new Currency();
        currency.setId(10L);
        currency.setCurrencyLetterCode("USD");
        IssuingBank issuingBank = new IssuingBank();
        issuingBank.setId(20L);

        Account account = new Account(id, accountNumber, balance, currency, issuingBank);

        assertEquals(id, account.getId(), "ID should match constructor argument");
        assertEquals(accountNumber, account.getAccountNumber(), "Account number should match constructor argument");
        assertEquals(0, balance.compareTo(account.getBalance()), "Balance should match constructor argument");
        assertEquals(currency, account.getCurrency(), "Currency should match constructor argument");
        assertEquals(issuingBank, account.getIssuingBank(), "IssuingBank should match constructor argument");
    }


    @Test
    void testGettersAndSetters() {
        Account account = new Account();

        Long id = 1L;
        String accountNumber = "0987654321";
        BigDecimal balance = new BigDecimal("250.75");

        Currency currency = new Currency();
        currency.setId(11L);
        currency.setCurrencyLetterCode("EUR");

        IssuingBank issuingBank = new IssuingBank();
        issuingBank.setId(21L);
        // issuingBank.setName("Test Bank"); // If IssuingBank has a name

        account.setId(id);
        account.setAccountNumber(accountNumber);
        account.setBalance(balance);
        account.setCurrency(currency);
        account.setIssuingBank(issuingBank);

        assertEquals(id, account.getId(), "Getter for ID should return set value");
        assertEquals(accountNumber, account.getAccountNumber(), "Getter for AccountNumber should return set value");
        assertEquals(0, balance.compareTo(account.getBalance()), "Getter for Balance should return set value");
        assertEquals(currency, account.getCurrency(), "Getter for Currency should return set value");
        assertEquals(issuingBank, account.getIssuingBank(), "Getter for IssuingBank should return set value");
    }

    @Test
    void testEqualsAndHashCode() {
        Currency usd = new Currency();
        usd.setId(1L);
        usd.setCurrencyLetterCode("USD");

        IssuingBank bank1 = new IssuingBank();
        bank1.setId(100L);

        IssuingBank bank2 = new IssuingBank();
        bank2.setId(101L);

        // Account.equals is based SOLELY on non-null ID.
        Account account1 = new Account(1L, "ACC123", new BigDecimal("100.00"), usd, bank1);
        Account account2 = new Account(1L, "ACCXYZ", new BigDecimal("200.00"), usd, bank2); // Same ID, different other fields
        Account account3 = new Account(2L, "ACC456", new BigDecimal("300.00"), usd, bank1); // Different ID

        // Reflexivity
        assertEquals(account1, account1, "Account should be equal to itself.");
        // HashCode contract: if equals is true, hashCodes must be equal.
        // The current hashCode() returns getClass().hashCode() (or proxy's class hashcode)
        // which will be the same for all instances of Account.
        assertEquals(account1.hashCode(), account1.hashCode(), "HashCode should be consistent for the same object.");

        // Symmetry
        assertEquals(account1, account2, "account1 and account2 should be equal (same ID).");
        assertEquals(account2, account1, "Symmetry: account2 and account1 should be equal (same ID).");
        assertEquals(account1.hashCode(), account2.hashCode(), "HashCodes should be equal for equal objects (same ID).");

        // Transitivity
        Account account1_clone = new Account(1L, "ACC_CLONE", new BigDecimal("50.00"), usd, bank1); // Same ID
        assertEquals(account1, account1_clone, "Account and its clone (same ID) should be equal.");
        assertEquals(account2, account1_clone, "Account2 and clone of Account1 (same ID) should be equal.");

        // Inequality
        assertNotEquals(account1, account3, "Accounts with different IDs should not be equal.");
        // Hashcodes might be the same even if objects are not equal, which is fine.
        // In this specific hashCode impl, they will be the same.
        assertEquals(account1.hashCode(), account3.hashCode(), "HashCodes can be the same for non-equal objects with this specific hashCode impl.");


        // Inequality with null and different type
        assertNotEquals(null, account1, "Account should not be equal to null.");
        assertNotEquals(account1, new Object(), "Account should not be equal to an object of a different type.");

        // Test with null ID
        // According to Account.equals(): `return getId() != null && Objects.equals(getId(), account.getId());`
        // If `this.getId()` is null, it returns false.
        Account accNoId1 = new Account(null, "NO_ID_ACC", BigDecimal.TEN, usd, bank1);
        Account accNoId2 = new Account(null, "NO_ID_ACC", BigDecimal.TEN, usd, bank1); // Same fields, but ID is null
        Account accNoId3 = new Account(null, "NO_ID_ACC_DIFF", BigDecimal.TEN, usd, bank1);

        assertNotEquals(accNoId1, accNoId2, "Accounts with null IDs are not equal by current equals implementation.");
        // Hashcodes will be the same (getClass().hashCode())
        assertEquals(accNoId1.hashCode(), accNoId2.hashCode());

        assertNotEquals(accNoId1, accNoId3, "Accounts with null IDs are not equal.");

        Account accWithId = new Account(5L, "ID_ACC", BigDecimal.ONE, usd, bank1);
        assertNotEquals(accNoId1, accWithId, "Account with null ID is not equal to account with non-null ID.");
        assertNotEquals(accWithId, accNoId1, "Account with non-null ID is not equal to account with null ID.");
    }

    @Test
    void testToString() {
        Currency currency = new Currency();
        currency.setId(30L);
        currency.setCurrencyLetterCode("GBP");
        // currency.setName("British Pound"); // Name is not used in Account.toString()

        IssuingBank issuingBank = new IssuingBank();
        issuingBank.setId(40L);
        // issuingBank.setName("Test GBP Bank"); // Name is not used in Account.toString()

        Account account = new Account(77L, "ACC_TO_STRING", new BigDecimal("555.55"), currency, issuingBank);

        String accountString = account.toString();
        // Expected: "Account{id=77, accountNumber='ACC_TO_STRING', balance=555.55, currency=30, issuingBank=40}"

        assertNotNull(accountString, "toString() should not return null.");
        assertTrue(accountString.contains("Account{"), "toString() should start with class name.");
        assertTrue(accountString.contains("id=77"), "toString() should contain the id.");
        assertTrue(accountString.contains("accountNumber='ACC_TO_STRING'"), "toString() should contain the account number.");
        assertTrue(accountString.contains("balance=555.55"), "toString() should contain the balance.");
        assertTrue(accountString.contains("currency=30"), "toString() should contain the currency ID.");
        assertTrue(accountString.contains("issuingBank=40"), "toString() should contain the issuingBank ID.");
        assertTrue(accountString.endsWith("}"), "toString() should end with '}'.");

        Account accountNoCurrencyOrBank = new Account(88L, "ACC_NO_REFS", BigDecimal.ONE, null, null);
        String noRefsString = accountNoCurrencyOrBank.toString();
        // Expected: "Account{id=88, accountNumber='ACC_NO_REFS', balance=1, currency=null, issuingBank=null}"
        assertTrue(noRefsString.contains("currency=null"), "toString() should handle null currency gracefully.");
        assertTrue(noRefsString.contains("issuingBank=null"), "toString() should handle null issuingBank gracefully.");
    }

    // --- Validation Specific Tests ---

    @Test
    void testValidation_accountNumber_NotNull() {
        Account account = new Account(1L, null, BigDecimal.TEN, testCurrency, testIssuingBank);
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        assertFalse(violations.isEmpty(), "Account number should not be null");
        assertEquals(1, violations.size(), "Should have exactly one violation for null account number");
        assertEquals("accountNumber", violations.iterator().next().getPropertyPath().toString());
        // Depending on the exact message in @NotNull, you could check the message too
        // assertTrue(violations.iterator().next().getMessage().contains("cannot be null"));
    }

    @Test
    void testValidation_accountNumber_NotBlank() {
        Account account = new Account(1L, "", BigDecimal.TEN, testCurrency, testIssuingBank);
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        assertFalse(violations.isEmpty(), "Account number should not be blank (empty string)");
        assertEquals(1, violations.size(), "Should have exactly one violation for blank account number");
        assertEquals("accountNumber", violations.iterator().next().getPropertyPath().toString());

        account.setAccountNumber("   "); // Test whitespace
        violations = validator.validate(account);
        assertFalse(violations.isEmpty(), "Account number should not be blank (whitespace)");
        assertEquals(1, violations.size(), "Should have exactly one violation for blank account number");
        assertEquals("accountNumber", violations.iterator().next().getPropertyPath().toString());
    }


    @Test
    void testValidation_accountNumber_Size() {
        String longAccountNumber = "A".repeat(51); // Exceeds max size 50
        Account account = new Account(1L, longAccountNumber, BigDecimal.TEN, testCurrency, testIssuingBank);
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        assertFalse(violations.isEmpty(), "Account number should not exceed 50 characters");
        assertEquals(1, violations.size(), "Should have exactly one violation for account number size");
        assertEquals("accountNumber", violations.iterator().next().getPropertyPath().toString());

        String validAccountNumber = "A".repeat(50); // Exactly max size 50
        account.setAccountNumber(validAccountNumber);
        violations = validator.validate(account);
        assertTrue(violations.isEmpty(), "Account number of exactly 50 characters should be valid");
    }

    @Test
    void testValidation_balance_NotNull() {
        Account account = new Account(1L, "ACC123", null, testCurrency, testIssuingBank);
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        assertFalse(violations.isEmpty(), "Balance should not be null");
        assertEquals(1, violations.size(), "Should have exactly one violation for null balance");
        assertEquals("balance", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testValidation_balance_DecimalMin() {
        Account account = new Account(1L, "ACC123", new BigDecimal("-0.01"), testCurrency, testIssuingBank);
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        assertFalse(violations.isEmpty(), "Balance should not be negative");
        assertEquals(1, violations.size(), "Should have exactly one violation for negative balance");
        assertEquals("balance", violations.iterator().next().getPropertyPath().toString());

        account.setBalance(BigDecimal.ZERO); // Test minimum allowed value
        violations = validator.validate(account);
        assertTrue(violations.isEmpty(), "Balance of 0.00 should be valid");

        account.setBalance(new BigDecimal("0.00")); // Test minimum allowed value explicitly
        violations = validator.validate(account);
        assertTrue(violations.isEmpty(), "Balance of 0.00 should be valid");
    }

    @Test
    void testValidation_balance_Digits() {
        // Assuming @Digits(integer = 15, fraction = 4)
        Account account = new Account(1L, "ACC123", new BigDecimal("1234567890123456.00"), testCurrency, testIssuingBank); // 16 integer digits
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        assertFalse(violations.isEmpty(), "Balance should not exceed 15 integer digits");
        assertEquals(1, violations.size(), "Should have exactly one violation for too many integer digits");
        assertEquals("balance", violations.iterator().next().getPropertyPath().toString());

        account.setBalance(new BigDecimal("1.23456")); // 5 fraction digits
        violations = validator.validate(account);
        assertFalse(violations.isEmpty(), "Balance should not exceed 4 fraction digits");
        assertEquals(1, violations.size(), "Should have exactly one violation for too many fraction digits");
        assertEquals("balance", violations.iterator().next().getPropertyPath().toString());

        account.setBalance(new BigDecimal("123456789012345.1234")); // Valid digits
        violations = validator.validate(account);
        assertTrue(violations.isEmpty(), "Balance with valid digits should be valid");
    }


    @Test
    void testValidation_currency_NotNull() {
        Account account = new Account(1L, "ACC123", BigDecimal.TEN, null, testIssuingBank);
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        assertFalse(violations.isEmpty(), "Currency should not be null");
        assertEquals(1, violations.size(), "Should have exactly one violation for null currency");
        assertEquals("currency", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testValidation_issuingBank_NotNull() {
        Account account = new Account(1L, "ACC123", BigDecimal.TEN, testCurrency, null);
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        assertFalse(violations.isEmpty(), "Issuing bank should not be null");
        assertEquals(1, violations.size(), "Should have exactly one violation for null issuing bank");
        assertEquals("issuingBank", violations.iterator().next().getPropertyPath().toString());
    }
}