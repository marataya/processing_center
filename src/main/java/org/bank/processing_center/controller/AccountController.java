package org.bank.processing_center.controller;

import org.bank.processing_center.model.Account;
import org.bank.processing_center.service.AccountService;
import org.bank.processing_center.view.ConsoleView;

import java.util.List;

/**
 * Controller for Account-related operations
 */
public class AccountController {

    private final AccountService accountService;
    private final ConsoleView view;

    public AccountController(AccountService accountService, ConsoleView view) {
        this.accountService = accountService;
        this.view = view;
    }

    /**
     * Creates the account table
     */
    public void createAccountTable() {
        try {
            accountService.createTable();
            view.showMessage("Таблица account создана успешно.");
        } catch (Exception e) {
            view.showError("Ошибка при создании таблицы account: " + e.getMessage());
        }
    }

    /**
     * Adds a new account
     * @param account Account to add
     */
    public void addAccount(Account account) {
        try {
            accountService.save(account);
            view.showMessage("Счет добавлен: " + account);
        } catch (Exception e) {
            view.showError("Ошибка при добавлении счета: " + e.getMessage());
        }
    }

    /**
     * Retrieves and displays all accounts
     */
    public List<Account> getAllAccounts() {
        try {
            List<Account> accounts = accountService.findAll();
            view.showAccountList(accounts);
            return accounts;
        } catch (Exception e) {
            view.showError("Ошибка при получении списка account: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Updates an account
     * @param account Account with updated information
     */
    public void updateAccount(Account account) {
        try {
            accountService.update(account);
            view.showMessage("Счет обновлен: " + account);
        } catch (Exception e) {
            view.showError("Ошибка при обновлении счета в account: " + e.getMessage());
        }
    }

    /**
     * Clears the account table
     */
    public void clearAccountTable() {
        try {
            accountService.clearTable();
            view.showMessage("Таблица счетов очищена.");
        } catch (Exception e) {
            view.showError("Ошибка при очистке таблицы account: " + e.getMessage());
        }
    }

    /**
     * Drops the account table
     */
    public void dropAccountTable() {
        try {
            accountService.dropTable();
            view.showMessage("Таблица account удалена.");
        } catch (Exception e) {
            view.showError("Ошибка при удалении таблицы account: " + e.getMessage());
        }
    }
}
