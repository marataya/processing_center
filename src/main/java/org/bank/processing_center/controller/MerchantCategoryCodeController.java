package org.bank.processing_center.controller;

import org.bank.processing_center.model.MerchantCategoryCode;
import org.bank.processing_center.service.MerchantCategoryCodeService;
import org.bank.processing_center.view.ConsoleView;

import java.util.List;

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
    public MerchantCategoryCode addEntity(MerchantCategoryCode merchantCategoryCode) {
        try {
            MerchantCategoryCode savedMerchantCategoryCode = merchantCategoryCodeService.save(merchantCategoryCode);
            view.showMessage("MerchantCategoryCode added: " + merchantCategoryCode);
            return savedMerchantCategoryCode;
        } catch (Exception e) {
            view.showError("Ошибка при получении списка mcc: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteEntity(Long id) {
        merchantCategoryCodeService.delete(id);
        view.showMessage("MerchantCategoryCode with ID " + id + " deleted.");
    }


    @Override
    public List<MerchantCategoryCode> getAllEntities() {
        List<MerchantCategoryCode> mcc = merchantCategoryCodeService.findAll();
        view.showList(mcc, "Merchant Categories List:");
        return mcc;
    }

    @Override
    public MerchantCategoryCode findById(Long id) {
        MerchantCategoryCode mcc = merchantCategoryCodeService.findById(id);
        if (mcc != null) {
            view.showMessage("Merchant Category Code found: " + mcc.toString());
        } else {
            view.showMessage("Merchant Category Code with ID " + id + " not found.");
        }
        return mcc;
    }

    @Override
    public MerchantCategoryCode updateEntity(MerchantCategoryCode entity) {
        try {
            MerchantCategoryCode updatedMerchantCategoryCode = merchantCategoryCodeService.update(entity);
            view.showMessage("Merchant Category Code updated: " + entity);
            return updatedMerchantCategoryCode;
        } catch (Exception e) {
            view.showError("Ошибка при получении списка mcc: " + e.getMessage());
            return null;
        }
    }

}