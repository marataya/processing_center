package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.dao.factory.DaoFactory;
import org.bank.processing_center.model.AcquiringBank;

import java.util.List;
import java.util.Optional;

public class AcquiringBankService implements Service<AcquiringBank, Long> {

    private final Dao<AcquiringBank, Long> acquiringBankDao;

    public AcquiringBankService(String daoType) {
        this.acquiringBankDao = DaoFactory.getInstance(daoType).getAcquiringBankDao();
    }

    @Override
    public Optional<AcquiringBank> findById(Long id) {
        return acquiringBankDao.findById(id);
    }

    @Override
    public List<AcquiringBank> findAll() {
        return acquiringBankDao.findAll();
    }

    @Override
    public void save(AcquiringBank entity) {
        acquiringBankDao.save(entity);
    }

    @Override
    public void update(AcquiringBank entity) {
        acquiringBankDao.update(entity);
    }

    @Override
    public void delete(Long id) {
        acquiringBankDao.delete(id);
    }

    @Override
    public void dropTable() {
        acquiringBankDao.dropTable();
    }

    @Override
    public void clearTable() {
        acquiringBankDao.clearTable();
    }

    @Override
    public void createTable() {
        acquiringBankDao.createTable();
    }
}