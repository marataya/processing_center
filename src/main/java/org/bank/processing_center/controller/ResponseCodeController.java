package org.bank.processing_center.controller;

import org.bank.processing_center.model.ResponseCode;
import org.bank.processing_center.service.ResponseCodeService;
import org.bank.processing_center.view.ConsoleView;

import java.util.List;
import java.util.Optional;

public class ResponseCodeController implements Controller<ResponseCode, Long> {

    private final ResponseCodeService responseCodeService;
    private final ConsoleView view;

    public ResponseCodeController(ResponseCodeService responseCodeService, ConsoleView view) {
        this.responseCodeService = responseCodeService;
        this.view = view;
    }

    @Override
    public void createTable() { // Method to create the response code table
        responseCodeService.createTable();
    }

    public void addResponseCode(ResponseCode responseCode) { // Method to add a new response code
        view.showMessage("Adding Response Code: " + responseCode.getErrorCode() + " - " + responseCode.getErrorDescription());
        responseCodeService.save(responseCode);
    }

    @Override
    public void clearTable() {
        responseCodeService.clearTable();
    }

    @Override
    public void deleteEntity(Long id) { // Method to delete a response code by ID
        view.showMessage("Deleting Response Code with ID: " + id);
        responseCodeService.delete(id);
    }

    public List<ResponseCode> getAllEntities() { // Method to retrieve all response codes
        try {
            List<ResponseCode> responseCodes = responseCodeService.findAll();
            view.showList(responseCodes, "Response Codes List:");
            return responseCodes;
        } catch (Exception e) {
            view.showError("Ошибка при получении списка response_code: " + e.getMessage());
            return List.of();
        }
    }

    public void updateEntity(ResponseCode responseCode) { // Method to update a response code
        try {
            responseCodeService.update(responseCode);
            view.showMessage("Response Code updated: " + responseCode);
        } catch (Exception e) {
            view.showError("Error updating response code: " + e.getMessage());
        }
    }

    @Override
    public void dropTable() {
        responseCodeService.dropTable();
    }

    public Optional<ResponseCode> findById(Long id) {
        try {
            return responseCodeService.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error finding ResponseCode by ID", e);
        }
    }

    @Override
    public void addEntity(ResponseCode entity) {
        try {
            responseCodeService.save(entity);
            view.showMessage("Response code added: " + entity);
        } catch (Exception e) {
            view.showError("Error adding response code: " + e.getMessage());
        }
    }


}