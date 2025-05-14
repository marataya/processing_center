package org.bank.processing_center.controller;

import org.bank.processing_center.model.PaymentSystem;
import org.bank.processing_center.service.PaymentSystemService;
import org.bank.processing_center.view.ConsoleView;

import java.util.List;
import java.util.Optional;

/**
 * Controller for PaymentSystem-related operations
 */
public class PaymentSystemController implements Controller<PaymentSystem, Long> {

    private final PaymentSystemService paymentSystemService;

    private final ConsoleView view;
    
    public PaymentSystemController(PaymentSystemService paymentSystemService, ConsoleView view) {
        this.paymentSystemService = paymentSystemService;
        this.view = view;
    }

    /**
     * Creates the payment system table
     */    
    public void createTable() {
        try {
            paymentSystemService.createTable();
            view.showMessage("Таблица платежных систем создана успешно.");
        } catch (Exception e) {
            view.showError("Ошибка при создании таблицы payment_system: " + e.getMessage());
        }
    }

    /**
     * Adds a new payment system
     * @param entity PaymentSystem to add
     */    
    @Override
    public void addEntity(PaymentSystem entity) {
        try {
            paymentSystemService.save(entity);
            view.showMessage("Платежная система добавлена: " + entity);
        } catch (Exception e) {
            view.showError("Ошибка при добавлении payment_system: " + e.getMessage());
        }
    }

    /**
     * Retrieves and displays all payment systems
     */    
    @Override    
    public List<PaymentSystem> getAllEntities() {
        try {
            List<PaymentSystem> systems = paymentSystemService.findAll();
            view.showList(systems, "Payment Systems List:");
            return systems;
        } catch (Exception e) {
            view.showError("Ошибка при получении списка payment_system: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Clears the payment system table
     */    
    public void clearTable() {
        try {
            paymentSystemService.clearTable();
            view.showMessage("Таблица payment_system очищена.");
        } catch (Exception e) {
            view.showError("Ошибка при очистке таблицы payment_system: " + e.getMessage());
        }
    }

    /**
     * Drops the payment system table 
     */
    @Override
    public void dropTable() {
        try {
            paymentSystemService.dropTable();
            view.showMessage("Таблица payment_system удалена.");
        } catch (Exception e) {
            view.showError("Ошибка при удалении таблицы payment_system: " + e.getMessage());
        }
    }

    /**
     * Deletes an entity by ID
     * @param id PaymentSystem ID
     */
    @Override
    public void deleteEntity(Long id) {
        paymentSystemService.delete(id);
    }

    /**
     * Finds an entity by ID
     * @param id Entity ID
     * @return Optional containing the entity if found
     * @Override
     */
    @Override
    public Optional<PaymentSystem> findById(Long id) {
        return paymentSystemService.findById(id);
    }

    @Override
    public void updateEntity(PaymentSystem entity) {
        try {
 paymentSystemService.update(entity);
            view.showMessage("Payment System updated: " + entity);
        } catch (Exception e) {
            view.showError("Error updating payment system: " + e.getMessage());
        }
    }
}
