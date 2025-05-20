package org.bank.processing_center.controller;

import org.bank.processing_center.model.Account;
import org.bank.processing_center.service.AccountService;
import org.bank.processing_center.view.ConsoleView;

import java.util.List;

/**
 * Controller for Account-related operations
 */
public class AccountController implements Controller<Account, Long> {

    private final AccountService accountService;
    private final ConsoleView view;

    public AccountController(AccountService accountService, ConsoleView view) {
        this.accountService = accountService;
        this.view = view;
    }

    /**
     * Creates the account table
     */
    @Override
    public void createTable() {
        try {
            accountService.createTable();
            view.showMessage("Таблица account создана успешно.");
        } catch (Exception e) {
            view.showError("Ошибка при создании таблицы account: " + e.getMessage());
        }
    }

    /**
     * Adds a new account
     * 
     * @param account Account to add
     */
    @Override
    public Account addEntity(Account account) {
        try {
            Account savedAccount = accountService.save(account);
            if (savedAccount != null) {
                view.showMessage("Счет добавлен: " + savedAccount);
            } else {
                view.showMessage("Счет не был добавлен.");
            }
            return savedAccount;
        } catch (Exception e) {
            view.showError("Ошибка при добавлении счета: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves and displays all accounts
     */
    @Override
    public List<Account> getAllEntities() {
        try {
            List<Account> accounts = accountService.findAll();
            return accounts;
        } catch (Exception e) {
            view.showError("Ошибка при получении списка account: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Updates an account
     * 
     * @param account Account with updated information
     */
    @Override
    public Account updateEntity(Account account) {
        try {
            Account updatedAccount = accountService.update(account);
            if (updatedAccount != null) {
                view.showMessage("Счет обновлен: " + updatedAccount);
            } else {
                view.showMessage("Счет не был обновлен.");
            }
            return updatedAccount;
        } catch (Exception e) {
            view.showError("Error updating account: " + e.getMessage());
            return null;
        }
    }

    /**
     * Clears the account table
     */
    @Override
    public void clearTable() {
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
    @Override
    public void dropTable() {
        try {
            accountService.dropTable();
            view.showMessage("Таблица account удалена.");
        } catch (Exception e) {
            view.showError("Ошибка при удалении таблицы account: " + e.getMessage());
        }
    }

    /**
     * Deletes an account by ID
     * 
     * @param id Account ID
     */
    @Override
    public void deleteEntity(Long id) {
        accountService.delete(id);
    }

    @Override
    public Account findById(Long id) {
        try {
            Account account = accountService.findById(id);
            if (account != null) {
                view.showMessage(account.toString());
                return account;
            } else {
                view.showMessage("Счет с ID " + id + " не найден.");
                return null;
            }
        } catch (Exception e) {
            view.showError("Ошибка при поиске счета в Account: " + e.getMessage());
            return null;
        }
    }

}
