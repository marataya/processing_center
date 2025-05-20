package org.bank.processing_center.controller;

import org.bank.processing_center.model.AcquiringBank;
import org.bank.processing_center.service.AcquiringBankService;
import org.bank.processing_center.view.ConsoleView;

import java.util.List;

public class AcquiringBankController implements Controller<AcquiringBank, Long> {

    private final AcquiringBankService acquiringBankService;
    private final ConsoleView view;

    public AcquiringBankController(AcquiringBankService acquiringBankService, ConsoleView view) {
        this.acquiringBankService = acquiringBankService;
        this.view = view;
    }

    @Override
    public void createTable() {
        acquiringBankService.createTable();
    }

    @Override
    public void dropTable() {
        acquiringBankService.dropTable();
    }

    @Override
    public void clearTable() {
        acquiringBankService.clearTable();
    }

    @Override
    public AcquiringBank addEntity(AcquiringBank acquiringBank) {
        try {
            AcquiringBank savedAcquiringBank = acquiringBankService.save(acquiringBank);
            view.showMessage("Acquiring Bank added: " + acquiringBank);
            return savedAcquiringBank;
        } catch (Exception e) {
            view.showError("Error adding acquiring bank: " + e.getMessage());
            return null;
        }
    }

    @Override
    public AcquiringBank updateEntity(AcquiringBank acquiringBank) {
        try {
            AcquiringBank updatedAcquiringBank = acquiringBankService.update(acquiringBank);
            acquiringBankService.update(updatedAcquiringBank);
            view.showMessage("Acquiring Bank updated: " + updatedAcquiringBank);
            return updatedAcquiringBank;
        } catch (Exception e) {
            view.showError("Error updating acquiring bank: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteEntity(Long id) {
        acquiringBankService.delete(id);
    }

    @Override
    public AcquiringBank findById(Long id) {
        try {
            AcquiringBank acquiringBank = acquiringBankService.findById(id);
            view.showMessage(acquiringBank.toString());
            return acquiringBank;
        } catch (Exception e) {
            view.showError("ERROR: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<AcquiringBank> getAllEntities() {
        try {
            List<AcquiringBank> accounts = acquiringBankService.findAll();
            view.showList(accounts, "Accounts List:");
            return accounts;
        } catch (Exception e) {
            view.showError("Ошибка при получении списка acquiring_bank: " + e.getMessage());
            return List.of();
        }
    }
}