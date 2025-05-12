package org.bank.processing_center.controller;

import java.util.List;
import java.util.Optional;

import org.bank.processing_center.model.IssuingBank;
import org.bank.processing_center.service.IssuingBankService;
import org.bank.processing_center.view.ConsoleView;

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

    public void addEntity(IssuingBank issuingBank) {
        view.showMessage("Adding Issuing bank: " + issuingBank);
        issuingBankService.save(issuingBank);
        view.showMessage("Issuing bank added successfully.");
    }

    public List<IssuingBank> getAllEntities() {
        view.showMessage("Getting all Issuing banks...");
        List<IssuingBank> issuingBanks = issuingBankService.findAll();
        view.showList(issuingBanks, "Issuing Banks List:");
        view.showMessage("Found " + issuingBanks.size() + " IssuingBanks.");
        return issuingBanks;
    }

    public void updateIssuingBank(IssuingBank issuingBank) {
        view.showMessage("Updating IssuingBank: " + issuingBank);
        issuingBankService.update(issuingBank);
        view.showMessage("IssuingBank updated successfully.");
    }

    public void deleteEntity(Long id) {
        view.showMessage("Deleting IssuingBank with ID: " + id);
        issuingBankService.delete(id);
        view.showMessage("IssuingBank with ID " + id + " deleted successfully.");
    }

    public Optional<IssuingBank> findById(Long id) {
        view.showMessage("Finding IssuingBank with ID: " + id);
        Optional<IssuingBank> issuingBank = issuingBankService.findById(id);
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
    public void updateEntity(IssuingBank entity) {
        try {
            view.showMessage("Updating issuing bank: " + entity);
            issuingBankService.update(entity);
            view.showMessage("Issuing bank updated: " + entity);
        } catch (Exception e) {
            view.showError("Error updating issuing bank: " + e.getMessage());
        }
    }
}