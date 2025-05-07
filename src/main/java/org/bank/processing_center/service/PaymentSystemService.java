package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.PaymentSystem;

import java.util.List;
import java.util.Optional;

public class PaymentSystemService implements Service<PaymentSystem, Long> {

    private final Dao<PaymentSystem, Long> paymentSystemDao;

    public PaymentSystemService(Dao<PaymentSystem, Long> paymentSystemDao) {
        this.paymentSystemDao = paymentSystemDao;
    }

    @Override
    public void createTable() {
        paymentSystemDao.createTable();
    }

    @Override
    public void dropTable() {
        paymentSystemDao.dropTable();
    }

    @Override
    public void clearTable() {
        paymentSystemDao.clearTable();
    }

    @Override
    public void save(PaymentSystem paymentSystem) {
        paymentSystemDao.save(paymentSystem);
    }

    @Override
    public void delete(Long id) {
        paymentSystemDao.delete(id);
    }

    @Override
    public List<PaymentSystem> findAll() {
        return paymentSystemDao.findAll();
    }

    @Override
    public Optional<PaymentSystem> findById(Long id) {
        return paymentSystemDao.findById(id);
    }

    @Override
    public void update(PaymentSystem paymentSystem) {
        paymentSystemDao.update(paymentSystem);
    }

    /**
     * Finds a payment system by name
     * @param name Payment system name to search for
     * @return Optional containing the payment system if found
     */
    public Optional<PaymentSystem> findByName(String name) {
        return paymentSystemDao.findAll().stream()
                .filter(system -> system.getPaymentSystemName().equalsIgnoreCase(name))
                .findFirst();
    }

    /**
     * Determines the payment system based on card number prefix
     * @param cardNumber Card number
     * @return Payment system name or "Unknown" if not recognized
     */
    public String determinePaymentSystemByCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            return "Unknown";
        }

        // Simple logic to determine payment system by first digit
        char firstDigit = cardNumber.charAt(0);

        if (firstDigit == '4') {
            return "VISA";
        } else if (firstDigit == '5') {
            return "MasterCard";
        } else if (firstDigit == '2') {
            return "MIR";
        } else {
            return "Unknown";
        }
    }
}
