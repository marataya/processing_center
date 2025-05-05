package org.bank.processing_center.controller;

import org.bank.processing_center.model.PaymentSystem;
import org.bank.processing_center.service.PaymentSystemService;
import org.bank.processing_center.view.ConsoleView;

import java.util.List;

/**
 * Controller for PaymentSystem-related operations
 */
public class PaymentSystemController {

    private final PaymentSystemService paymentSystemService;
    private final ConsoleView view;

    public PaymentSystemController(PaymentSystemService paymentSystemService, ConsoleView view) {
        this.paymentSystemService = paymentSystemService;
        this.view = view;
    }

    /**
     * Creates the payment system table
     */
    public void createPaymentSystemTable() {
        try {
            paymentSystemService.createTable();
            view.showMessage("Таблица платежных систем создана успешно.");
        } catch (Exception e) {
            view.showError("Ошибка при создании таблицы платежных систем: " + e.getMessage());
        }
    }

    /**
     * Adds a new payment system
     * @param paymentSystem PaymentSystem to add
     */
    public void addPaymentSystem(PaymentSystem paymentSystem) {
        try {
            paymentSystemService.save(paymentSystem);
            view.showMessage("Платежная система добавлена: " + paymentSystem);
        } catch (Exception e) {
            view.showError("Ошибка при добавлении платежной системы: " + e.getMessage());
        }
    }

    /**
     * Retrieves and displays all payment systems
     */
    public List<PaymentSystem> getAllPaymentSystems() {
        try {
            List<PaymentSystem> systems = paymentSystemService.findAll();
            view.showPaymentSystemList(systems);
            return systems;
        } catch (Exception e) {
            view.showError("Ошибка при получении списка платежных систем: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Clears the payment system table
     */
    public void clearPaymentSystemTable() {
        try {
            paymentSystemService.clearTable();
            view.showMessage("Таблица платежных систем очищена.");
        } catch (Exception e) {
            view.showError("Ошибка при очистке таблицы платежных систем: " + e.getMessage());
        }
    }

    /**
     * Drops the payment system table
     */
    public void dropPaymentSystemTable() {
        try {
            paymentSystemService.dropTable();
            view.showMessage("Таблица платежных систем удалена.");
        } catch (Exception e) {
            view.showError("Ошибка при удалении таблицы платежных систем: " + e.getMessage());
        }
    }
}
