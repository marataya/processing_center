// src/test/java/org/bank/processing_center/model/AccountTest.java
package org.bank.processing_center.model;

import org.junit.jupiter.api.Test; // JUnit 5 Test annotation
import static org.junit.jupiter.api.Assertions.*; // JUnit 5 assertions

import java.math.BigDecimal;

class AccountTest {

    @Test
    void testDefaultConstructor() {
        Account account = new Account();
        assertNull(account.getId(), "ID should be null for default constructor");
        assertNull(account.getAccountNumber(), "Account number should be null for default constructor");
        assertNull(account.getOwnerName(), "Owner name should be null for default constructor");
        assertNull(account.getBalance(), "Balance should be null for default constructor");
        assertFalse(account.isActive(), "Account should be inactive by default");
    }

    @Test
    void testParameterizedConstructorAndGetters() {
        Long id = 1L;
        String accNum = "ACC001";
        String owner = "Alice Wonderland";
        BigDecimal balance = new BigDecimal("1234.56");
        boolean isActive = true;

        Account account = new Account(id, accNum, owner, balance, isActive);

        assertEquals(id, account.getId(), "ID should match constructor argument");
        assertEquals(accNum, account.getAccountNumber(), "Account number should match constructor argument");
        assertEquals(owner, account.getOwnerName(), "Owner name should match constructor argument");
        assertNotNull(account.getBalance(), "Balance should not be null");
        assertEquals(0, balance.compareTo(account.getBalance()), "Balance should match constructor argument");
        assertEquals(isActive, account.isActive(), "Active status should match constructor argument");
    }

    @Test
    void testSetters() {
        Account account = new Account();

        Long id = 2L;
        account.setId(id);
        assertEquals(id, account.getId(), "Setter for ID failed");

        String accNum = "ACC002";
        account.setAccountNumber(accNum);
        assertEquals(accNum, account.getAccountNumber(), "Setter for AccountNumber failed");

        String owner = "Bob The Builder";
        account.setOwnerName(owner);
        assertEquals(owner, account.getOwnerName(), "Setter for OwnerName failed");

        BigDecimal balance = new BigDecimal("500.75");
        account.setBalance(balance);
        assertNotNull(account.getBalance());
        assertEquals(0, balance.compareTo(account.getBalance()), "Setter for Balance failed");

        account.setActive(true);
        assertTrue(account.isActive(), "Setter for Active failed");
    }

    @Test
    void testEqualsAndHashCode() {
        Account account1 = new Account(1L, "ACC001", "Alice", new BigDecimal("100.00"), true);
        Account account2 = new Account(1L, "ACC001", "Alice", new BigDecimal("100.00"), true); // Same as account1
        Account account3 = new Account(2L, "ACC002", "Bob", new BigDecimal("200.00"), false);   // Different from account1
        Account account4 = new Account(1L, "ACC001", "Alice", new BigDecimal("100.01"), true); // Different balance

        // Reflexive
        assertEquals(account1, account1, "Equals should be reflexive");

        // Symmetric
        assertEquals(account1, account2, "Equals should be symmetric (1 vs 2)");
        assertEquals(account2, account1, "Equals should be symmetric (2 vs 1)");

        // Consistent hashCode
        assertEquals(account1.hashCode(), account2.hashCode(), "Hashcode should be consistent for equal objects");

        // Not equal to different object
        assertNotEquals(account1, account3, "Objects with different properties should not be equal");
        // Note: Hashcodes are not guaranteed to be different for non-equal objects, but usually are.
        // assertNotEquals(account1.hashCode(), account3.hashCode());

        // Not equal if a field differs
        assertNotEquals(account1, account4, "Objects with different balance should not be equal");

        // Not equal to null
        assertNotEquals(null, account1, "Object should not be equal to null");

        // Not equal to different type
        assertNotEquals("a string", account1, "Object should not be equal to an object of a different type");
    }

    @Test
    void testToString() {
        Account account = new Account(1L, "ACC001", "Alice", new BigDecimal("100.00"), true);
        String accountString = account.toString();

        assertTrue(accountString.contains("id=1"), "toString should contain id");
        assertTrue(accountString.contains("accountNumber='ACC001'"), "toString should contain accountNumber");
        assertTrue(accountString.contains("ownerName='Alice'"), "toString should contain ownerName");
        // BigDecimal.toString() can vary (e.g., "100.00" vs "100").
        // For more precise matching, you might compare specific parts or use a regex.
        assertTrue(accountString.contains("balance=" + new BigDecimal("100.00").toString()), "toString should contain balance");
        assertTrue(accountString.contains("active=true"), "toString should contain active status");
    }
}