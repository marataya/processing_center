package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.Transaction;

import java.util.List;

public class TransactionService implements Service<Transaction, Long> {

    private final Dao<Transaction, Long> transactionDao;

    public TransactionService(Dao<Transaction, Long> dao) {
        this.transactionDao = dao;
    }

    @Override
    public void createTable() {
        transactionDao.createTable();
    }

    @Override
    public void dropTable() {
        transactionDao.dropTable();
    }

    @Override
    public void clearTable() {
        transactionDao.clearTable();
    }

    @Override
    public Transaction save(Transaction entity) {
        return transactionDao.save(entity);
    }

    @Override
    public void delete(Long id) {
        transactionDao.delete(id);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionDao.findAll();
    }

    @Override
    public Transaction findById(Long id) {
        return transactionDao.findById(id);
    }

    @Override
    public Transaction update(Transaction entity) {
        return transactionDao.update(entity);
    }
}