package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.dao.factory.DaoFactory;
import org.bank.processing_center.model.TransactionType;

import java.util.List;

public class TransactionTypeService implements Service<TransactionType, Long> {

    private final Dao<TransactionType, Long> transactionTypeDao;

    public TransactionTypeService(String daoType) {
        DaoFactory daoFactory = DaoFactory.getInstance(daoType);
        this.transactionTypeDao = daoFactory.getTransactionTypeDao(); // Assuming getTransactionTypeDao() exists in DaoFactory
    }

    // TODO: Implement Service interface methods

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
    public void save(TransactionType entity) {
        transactionTypeDao.save(entity);
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
    public java.util.Optional<TransactionType> findById(Long id) {
        return transactionTypeDao.findById(id);
    }

    @Override
    public void update(TransactionType entity) {
        transactionTypeDao.update(entity);
    }
}