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
    public void addEntity(AcquiringBank acquiringBank) {
        acquiringBankService.save(acquiringBank);
    }

    @Override
    public void updateEntity(AcquiringBank acquiringBank) {
        try {
            acquiringBankService.update(acquiringBank);
            view.showMessage("Acquiring Bank updated: " + acquiringBank);
        } catch (Exception e) {
            view.showError("Error updating acquiring bank: " + e.getMessage());
        }
    }

    @Override
    public void deleteEntity(Long id) {
        acquiringBankService.delete(id);
    }

    @Override
    public AcquiringBank findById(Long id) {
        return acquiringBankService.findById(id);
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