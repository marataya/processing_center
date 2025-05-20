package org.bank.processing_center.controller;

import org.bank.processing_center.model.IssuingBank;
import org.bank.processing_center.service.IssuingBankService;
import org.bank.processing_center.view.ConsoleView;

import java.util.List;

public class IssuingBankController implements Controller<IssuingBank, Long> {

    private final IssuingBankService issuingBankService;
    private final ConsoleView view;

    public IssuingBankController(IssuingBankService issuingBankService, ConsoleView view) {
        this.issuingBankService = issuingBankService;
        this.view = view;
    }

    public void createTable() {
        view.showMessage("Creating IssuingBank table...");
        issuingBankService.createTable();
        view.showMessage("IssuingBank table created.");
    }

    public IssuingBank addEntity(IssuingBank issuingBank) {

        try {
            IssuingBank savedIssuingBank = issuingBankService.save(issuingBank);
            view.showMessage("Adding Issuing bank: " + issuingBank);
            view.showMessage("Issuing bank added successfully.");
            return savedIssuingBank;
        } catch (Exception e) {
            view.showError("Error adding Issuing bank: " + e.getMessage());
            return null;
        }
    }

    public List<IssuingBank> getAllEntities() {
        view.showMessage("Getting all Issuing banks...");
        List<IssuingBank> issuingBanks = issuingBankService.findAll();
        view.showList(issuingBanks, "Issuing Banks List:");
        view.showMessage("Found " + issuingBanks.size() + " IssuingBanks.");
        return issuingBanks;
    }

    public void deleteEntity(Long id) {
        view.showMessage("Deleting IssuingBank with ID: " + id);
        issuingBankService.delete(id);
        view.showMessage("IssuingBank with ID " + id + " deleted successfully.");
    }

    public IssuingBank findById(Long id) {
        view.showMessage("Finding IssuingBank with ID: " + id);
        IssuingBank issuingBank = issuingBankService.findById(id);
        view.showMessage("Find IssuingBank operation completed.");
        return issuingBank;
    }

    @Override
    public void dropTable() {
        issuingBankService.dropTable();
        view.showMessage("IssuingBank table dropped.");
    }

    @Override
    public void clearTable() {
        issuingBankService.clearTable();
        view.showMessage("IssuingBank table cleared.");
    }

    @Override
    public IssuingBank updateEntity(IssuingBank entity) {
        try {
            IssuingBank updatedIssuingBank = issuingBankService.update(entity);
            view.showMessage("Updating issuing bank: " + entity);
            return updatedIssuingBank;
        } catch (Exception e) {
            view.showError("Error updating issuing bank: " + e.getMessage());
            return null;
        }
    }
}