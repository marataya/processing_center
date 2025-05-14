package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.dao.factory.DaoFactory;
import org.bank.processing_center.model.Transaction;

import java.util.List;
import java.util.Optional;

public class TransactionService implements Service<Transaction, Long> {

    private final Dao<Transaction, Long> transactionDao;

    public TransactionService(String daoType) {
        DaoFactory daoFactory = DaoFactory.getInstance(daoType);
        this.transactionDao = daoFactory.getTransactionDao();
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
    public void save(Transaction entity) {
        transactionDao.save(entity);
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
    public Optional<Transaction> findById(Long id) {
        return transactionDao.findById(id);
    }

    @Override
    public void update(Transaction entity) {
        transactionDao.update(entity);
    }
}