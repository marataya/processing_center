package org.bank.processing_center.controller;

import org.bank.processing_center.model.Transaction;
import org.bank.processing_center.service.TransactionService;
import org.bank.processing_center.view.ConsoleView;

import java.util.List;
import java.util.Optional;

public class TransactionController implements Controller<Transaction, Long> {

    private final TransactionService transactionService;
    private final ConsoleView view;

    public TransactionController(TransactionService transactionService, ConsoleView view) {
        this.transactionService = transactionService;
        this.view = view;
    }

    @Override
    public void createTable() {
        transactionService.createTable();
    }

    @Override
    public void addEntity(Transaction transaction) {
        transactionService.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = transactionService.findAll();
        if (transactions.isEmpty()) {
            view.showMessage("No transactions found.");
        } else {
            view.showList(transactions, "Transactions List:");
        }
        return transactions;
    }

    @Override
    public void updateEntity(Transaction transaction) {
        transactionService.update(transaction);
        view.showMessage("Транзакция обновлена: " + transaction);
    }

    @Override
    public void deleteEntity(Long id) {
        transactionService.delete(id);
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return transactionService.findById(id);
    }

    @Override
    public void dropTable() {
        try {
            transactionService.dropTable();
            view.showMessage("Таблица Transaction удалена.");
        } catch (Exception e) {
            view.showError("Ошибка при удалении таблицы Transaction: " + e.getMessage());
        }
    }

    @Override
    public void clearTable() {
        transactionService.clearTable();
    }

    @Override
    public List<Transaction> getAllEntities() {
        try {
            List<Transaction> transactions = transactionService.findAll();
            view.showList(transactions, "Transactions List:");
            return transactions;
        }
        catch (Exception e) {
            view.showError("Ошибка при получении списка транзакций: " + e.getMessage());
            return List.of();
        }
    }

}