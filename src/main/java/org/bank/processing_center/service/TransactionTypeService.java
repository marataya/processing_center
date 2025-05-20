package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.TransactionType;

import java.util.List;

public class TransactionTypeService implements Service<TransactionType, Long> {

    private final Dao<TransactionType, Long> transactionTypeDao;

    public TransactionTypeService(Dao<TransactionType, Long> dao) {
        this.transactionTypeDao = dao;
    }

    @Override
    public void createTable() {
        transactionTypeDao.createTable();
    }

    @Override
    public void dropTable() {
        transactionTypeDao.dropTable();
    }

    @Override
    public void clearTable() {
        transactionTypeDao.clearTable();
    }

    @Override
    public TransactionType save(TransactionType entity) {
        return transactionTypeDao.save(entity);
    }

    @Override
    public void delete(Long id) {
        transactionTypeDao.delete(id);
    }

    @Override
    public List<TransactionType> findAll() {
        return transactionTypeDao.findAll();
    }

    @Override
    public TransactionType findById(Long id) {
        return transactionTypeDao.findById(id);
    }

    @Override
    public TransactionType update(TransactionType entity) {
        return transactionTypeDao.update(entity);
    }
}