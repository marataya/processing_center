package org.bank.processing_center.controller;

import org.bank.processing_center.model.TransactionType;
import org.bank.processing_center.service.TransactionTypeService;
import org.bank.processing_center.view.ConsoleView;

import java.util.List;

public class TransactionTypeController implements Controller<TransactionType, Long> {

    private final TransactionTypeService transactionTypeService;
    private final ConsoleView view;

    public TransactionTypeController(TransactionTypeService transactionTypeService, ConsoleView view) {
        this.transactionTypeService = transactionTypeService;
        this.view = view;
    }

    @Override
    public void createTable() {
        view.showMessage("Creating transaction types table...");
        transactionTypeService.createTable();
        view.showMessage("Transaction types table created.");
    }

    @Override
    public TransactionType addEntity(TransactionType transactionType) {
        try {
            TransactionType savedTransactionType = transactionTypeService.save(transactionType);
            view.showMessage("Transaction type added.");
            return savedTransactionType;
        } catch (Exception e) {
            view.showError("Error adding transaction type: " + e.getMessage());
            return null;
        }
    }

    @Override
    public TransactionType findById(Long id) {
        TransactionType transactionType = transactionTypeService.findById(id);
        view.showMessage("Transaction type found: " + transactionType.toString());
        return transactionType;
    }

    @Override
    public TransactionType updateEntity(TransactionType transactionType) {
        try {
            TransactionType updatedTransactionType = transactionTypeService.update(transactionType);
            view.showMessage("Updating transaction type: " + transactionType);
            return updatedTransactionType;
        } catch (Exception e) {
            view.showError("Error updating transaction type: " + e.getMessage());
            return null;
        }
    }

    public void deleteTransactionType(Long id) {
        view.showMessage("Deleting transaction type with ID: " + id);
        transactionTypeService.delete(id);
        view.showMessage("Transaction type deleted.");
    }

    @Override
    public void deleteEntity(Long id) {
        deleteTransactionType(id);
    }

    @Override
    public void dropTable() {
        view.showMessage("Dropping transaction types table...");
        transactionTypeService.dropTable();
        view.showMessage("Transaction types table dropped.");
    }

    @Override
    public void clearTable() {
        view.showMessage("Clearing transaction types table...");
        transactionTypeService.clearTable();
        view.showMessage("Transaction types table cleared.");
    }
    
    @Override
    public List<TransactionType> getAllEntities() {
        try {
            List<TransactionType> ttypes = transactionTypeService.findAll();
            view.showList(ttypes, "Transaction Types List:");
            return ttypes;
        } catch (Exception e) {
            view.showError("Ошибка при получении списка transaction_type: " + e.getMessage());
            return List.of();
        }
    }

}