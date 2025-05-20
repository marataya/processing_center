package org.bank.processing_center.controller;

import org.bank.processing_center.model.Transaction;
import org.bank.processing_center.service.TransactionService;
import org.bank.processing_center.view.ConsoleView;

import java.util.List;

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
    public Transaction addEntity(Transaction transaction) {
        try {
            Transaction savedTransaction = transactionService.save(transaction);
            view.showMessage("Транзакция добавлена: " + transaction);
            return savedTransaction;
        } catch (Exception e) {
            view.showError("Ошибка при добавлении транзакции: " + e.getMessage());
            return null;
        }
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
    public Transaction updateEntity(Transaction transaction) {
        try {
            Transaction updatedTransaction = transactionService.update(transaction);
            view.showMessage("Транзакция обновлена: " + transaction);
            return updatedTransaction;
        } catch (Exception e) {
            view.showError("Ошибка при обновлении транзакции: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteEntity(Long id) {
        transactionService.delete(id);
    }

    @Override
    public Transaction findById(Long id) {
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
        } catch (Exception e) {
            view.showError("Ошибка при получении списка транзакций: " + e.getMessage());
            return List.of();
        }
    }

}