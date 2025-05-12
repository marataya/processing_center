package org.bank.processing_center.controller;

import org.bank.processing_center.model.MerchantCategoryCode;
import org.bank.processing_center.service.MerchantCategoryCodeService;
import org.bank.processing_center.view.ConsoleView;

import java.util.List;
import java.util.Optional;

public class MerchantCategoryCodeController implements Controller<MerchantCategoryCode, Long> {

    private final MerchantCategoryCodeService merchantCategoryCodeService;
    private final ConsoleView view;

    public MerchantCategoryCodeController(MerchantCategoryCodeService merchantCategoryCodeService, ConsoleView view) {
        this.merchantCategoryCodeService = merchantCategoryCodeService;
        this.view = view;
    }

    @Override
    public void createTable() {
        merchantCategoryCodeService.createTable();
        view.showMessage("MerchantCategoryCode table created (if not exists).");
    }

    @Override
    public void dropTable() {
        merchantCategoryCodeService.dropTable();
        view.showMessage("MerchantCategoryCode table dropped.");
    }

    @Override
    public void clearTable() {
        merchantCategoryCodeService.clearTable();
        view.showMessage("MerchantCategoryCode table cleared.");
    }

    @Override
    public void addEntity(MerchantCategoryCode merchantCategoryCode) {
        merchantCategoryCodeService.save(merchantCategoryCode);
        view.showMessage("MerchantCategoryCode added: " + merchantCategoryCode);
    }

    @Override
    public void deleteEntity(Long id) {
        merchantCategoryCodeService.delete(id);
        view.showMessage("MerchantCategoryCode with ID " + id + " deleted.");
    }

    public void updateMerchantCategoryCode(MerchantCategoryCode merchantCategoryCode) {
        // This method seems redundant given updateEntity, might be a remnant.
        // Assuming updateEntity is the intended method to refactor.
        updateEntity(merchantCategoryCode);
    }


    @Override
    public List<MerchantCategoryCode> getAllEntities() {
        List<MerchantCategoryCode> mcc = merchantCategoryCodeService.findAll();
        view.showList(mcc, "Merchant Categories List:");
        return mcc;
    }

    @Override
    public Optional<MerchantCategoryCode> findById(Long id) {
        Optional<MerchantCategoryCode> mcc = merchantCategoryCodeService.findById(id);
        if (mcc.isPresent()) {
            view.showMessage("Merchant Category Code found: " + mcc.get());
        } else {
            view.showMessage("Merchant Category Code with ID " + id + " not found.");
        }
        return mcc;
    }

    @Override
    public void updateEntity(MerchantCategoryCode entity) {
        try {
            merchantCategoryCodeService.update(entity);
            view.showMessage("Merchant Category Code updated: " + entity);
        } catch (Exception e) {
            view.showError("Ошибка при получении списка mcc: " + e.getMessage());
        }
    }

}