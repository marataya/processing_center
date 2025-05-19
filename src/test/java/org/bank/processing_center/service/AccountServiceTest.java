package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Integrates Mockito with JUnit 5
class AccountServiceTest {

    @Mock // Create a mock instance of the Dao
    private Dao<Account, Long> accountDao;

    @InjectMocks // Inject the mock Dao into the AccountService instance
    private AccountService accountService;

    private Account testAccount;
    private final Long TEST_ACCOUNT_ID = 1L;
    private final String TEST_ACCOUNT_NUMBER = "1234567890";
    private final BigDecimal INITIAL_BALANCE = new BigDecimal("1000.00");

    @BeforeEach
    void setUp() {
        // Initialize a standard test account before each test
        testAccount = new Account();
        testAccount.setId(TEST_ACCOUNT_ID);
        testAccount.setAccountNumber(TEST_ACCOUNT_NUMBER);
        testAccount.setBalance(INITIAL_BALANCE);
        // Set other required fields if Account has @NotNull constraints and they are relevant for the tests
        // e.g., testAccount.setCurrency(new Currency());
        // e.account.setIssuingBank(new IssuingBank());
    }

    @Test
    void testConstructor() {
        // Verify that the Dao is correctly injected
        assertNotNull(accountService);
        // We can't directly access the private accountDao field in the service,
        // but @InjectMocks ensures it's set if the constructor signature matches.
        // The other tests will implicitly verify this by checking if dao methods are called.
    }

    @Test
    void testCreateTable() {
        // Call the service method
        accountService.createTable();

        // Verify that the corresponding method on the mocked DAO was called exactly once
        verify(accountDao, times(1)).createTable();
    }

    @Test
    void testDropTable() {
        accountService.dropTable();
        verify(accountDao, times(1)).dropTable();
    }

    @Test
    void testClearTable() {
        accountService.clearTable();
        verify(accountDao, times(1)).clearTable();
    }

    @Test
    void testSave() {
        Account accountToSave = new Account(); // Use a different instance for the test
        accountToSave.setAccountNumber("NEW_ACC");
        accountToSave.setBalance(BigDecimal.ZERO);

        accountService.save(accountToSave);

        // Verify that the save method on the DAO was called with the correct account object
        verify(accountDao, times(1)).save(accountToSave);
    }

    @Test
    void testDelete() {
        accountService.delete(TEST_ACCOUNT_ID);
        verify(accountDao, times(1)).delete(TEST_ACCOUNT_ID);
    }

    @Test
    void testFindAll() {
        // Prepare the mock response
        List<Account> expectedAccounts = Arrays.asList(testAccount, new Account());
        when(accountDao.findAll()).thenReturn(expectedAccounts);

        // Call the service method
        List<Account> actualAccounts = accountService.findAll();

        // Verify the DAO method was called and the correct list is returned
        verify(accountDao, times(1)).findAll();
        assertEquals(expectedAccounts, actualAccounts);
    }

    @Test
    void testFindById_found() {
        // Prepare the mock response for finding the account
        when(accountDao.findById(TEST_ACCOUNT_ID)).thenReturn(testAccount);

        // Call the service method
        Account foundAccount = accountService.findById(TEST_ACCOUNT_ID);

        // Verify the DAO method was called and the account is present
        verify(accountDao, times(1)).findById(TEST_ACCOUNT_ID);
        assertNotNull(foundAccount);
        assertEquals(testAccount, foundAccount);
    }

    @Test
    void testFindById_notFound() {
        // Prepare the mock response for not finding the account
        when(accountDao.findById(TEST_ACCOUNT_ID)).thenReturn(null);

        // Call the service method
        Account foundAccount = accountService.findById(TEST_ACCOUNT_ID);

        // Verify the DAO method was called and the account is not present
        verify(accountDao, times(1)).findById(TEST_ACCOUNT_ID);
        assertNotNull(foundAccount);

    }

    @Test
    void testUpdate() {
        Account accountToUpdate = new Account(); // Use a different instance for the test
        accountToUpdate.setId(TEST_ACCOUNT_ID);
        accountToUpdate.setAccountNumber("UPDATED_ACC");
        accountToUpdate.setBalance(new BigDecimal("500.00"));

        accountService.update(accountToUpdate);

        // Verify that the update method on the DAO was called with the correct account object
        verify(accountDao, times(1)).update(accountToUpdate);
    }

    // --- Tests for updateBalance method ---

    @Test
    void testUpdateBalance_success_positiveAmount() {
        BigDecimal amountToAdd = new BigDecimal("500.00");
        BigDecimal expectedNewBalance = INITIAL_BALANCE.add(amountToAdd);

        // Mock the DAO to return the test account
        when(accountDao.findById(TEST_ACCOUNT_ID)).thenReturn(testAccount);

        // Call the service method
        boolean result = accountService.updateBalance(TEST_ACCOUNT_ID, amountToAdd.doubleValue());

        // Verify the interactions and result
        assertTrue(result, "updateBalance should return true on success");
        verify(accountDao, times(1)).findById(TEST_ACCOUNT_ID);
        // Verify that setBalance was called on the *specific* testAccount instance returned by the mock
        assertEquals(expectedNewBalance, testAccount.getBalance(), "Account balance should be updated");
        verify(accountDao, times(1)).update(testAccount); // Verify update was called with the modified account
        // Note: We don't test System.out.println directly in unit tests, but focus on logic and interactions.
    }

    @Test
    void testUpdateBalance_success_negativeAmount() {
        // Subtracting a negative amount is adding a positive amount
        BigDecimal amountToSubtractNegative = new BigDecimal("-200.00");
        BigDecimal expectedNewBalance = INITIAL_BALANCE.add(amountToSubtractNegative); // This will be 1000 + (-200) = 800

        when(accountDao.findById(TEST_ACCOUNT_ID)).thenReturn(testAccount);

        boolean result = accountService.updateBalance(TEST_ACCOUNT_ID, amountToSubtractNegative.doubleValue());

        assertTrue(result, "updateBalance should return true when subtracting a negative amount (adding)");
        verify(accountDao, times(1)).findById(TEST_ACCOUNT_ID);
        assertEquals(expectedNewBalance, testAccount.getBalance(), "Account balance should be updated correctly with negative amount");
        verify(accountDao, times(1)).update(testAccount);
    }


    @Test
    void testUpdateBalance_success_subtractAmount_balanceRemainsPositive() {
        BigDecimal amountToSubtract = new BigDecimal("200.00");
        BigDecimal expectedNewBalance = INITIAL_BALANCE.subtract(amountToSubtract); // 1000 - 200 = 800

        when(accountDao.findById(TEST_ACCOUNT_ID)).thenReturn(testAccount);

        boolean result = accountService.updateBalance(TEST_ACCOUNT_ID, -amountToSubtract.doubleValue()); // Pass as negative double

        assertTrue(result, "updateBalance should return true when subtracting and balance remains positive");
        verify(accountDao, times(1)).findById(TEST_ACCOUNT_ID);
        assertEquals(expectedNewBalance, testAccount.getBalance(), "Account balance should be updated correctly after subtraction");
        verify(accountDao, times(1)).update(testAccount);
    }

    @Test
    void testUpdateBalance_success_subtractAmount_balanceBecomesZero() {
        BigDecimal amountToSubtract = INITIAL_BALANCE; // Subtract the full initial balance
        BigDecimal expectedNewBalance = BigDecimal.ZERO;

        when(accountDao.findById(TEST_ACCOUNT_ID)).thenReturn(testAccount);

        boolean result = accountService.updateBalance(TEST_ACCOUNT_ID, -amountToSubtract.doubleValue());

        assertTrue(result, "updateBalance should return true when subtracting and balance becomes zero");
        verify(accountDao, times(1)).findById(TEST_ACCOUNT_ID);

        assertEquals(0, expectedNewBalance.compareTo(testAccount.getBalance()),
                "Account balance should be numerically zero after subtracting full amount. Expected: " +
                        expectedNewBalance.toPlainString() + " Actual: " + testAccount.getBalance().toPlainString());

        verify(accountDao, times(1)).update(testAccount);
    }


    @Test
    void testUpdateBalance_insufficientFunds() {
        BigDecimal amountToSubtract = new BigDecimal("1500.00"); // More than initial balance
        BigDecimal wouldBeNewBalance = INITIAL_BALANCE.subtract(amountToSubtract); // 1000 - 1500 = -500

        when(accountDao.findById(TEST_ACCOUNT_ID)).thenReturn(testAccount);

        // Call the service method
        boolean result = accountService.updateBalance(TEST_ACCOUNT_ID, -amountToSubtract.doubleValue()); // Pass as negative double

        // Verify the interactions and result
        assertFalse(result, "updateBalance should return false on insufficient funds");
        verify(accountDao, times(1)).findById(TEST_ACCOUNT_ID);
        // Verify that setBalance was *not* called
        // We can't directly verify setBalance was NOT called on the mock object itself easily if it's a real object.
        // However, we can verify that the balance of the testAccount object was NOT changed.
        assertEquals(INITIAL_BALANCE, testAccount.getBalance(), "Account balance should NOT be changed on insufficient funds");
        // Verify that update was *not* called on the DAO
        verify(accountDao, never()).update(any(Account.class));
        // Note: We don't test System.out.println directly.
    }

    @Test
    void testUpdateBalance_accountNotFound() {
        Long nonExistentAccountId = 99L;
        BigDecimal amount = new BigDecimal("100.00");

        // Mock the DAO to return empty optional
        when(accountDao.findById(nonExistentAccountId)).thenReturn(null);

        // Call the service method
        boolean result = accountService.updateBalance(nonExistentAccountId, amount.doubleValue());

        // Verify the interactions and result
        assertFalse(result, "updateBalance should return false when account is not found");
        verify(accountDao, times(1)).findById(nonExistentAccountId);
        // Verify that update was *not* called on the DAO
        verify(accountDao, never()).update(any(Account.class));
        // Note: We don't test System.out.println directly.
    }
}