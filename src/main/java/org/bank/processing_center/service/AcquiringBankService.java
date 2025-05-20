package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.helper.exception.DaoException;
import org.bank.processing_center.model.AcquiringBank;

import java.util.List;

public class AcquiringBankService implements Service<AcquiringBank, Long> {

    private final Dao<AcquiringBank, Long> acquiringBankDao;

    public AcquiringBankService(Dao<AcquiringBank, Long> dao) {
        this.acquiringBankDao = dao;
    }

    @Override
    public AcquiringBank findById(Long id) {
        return acquiringBankDao.findById(id).orElseThrow(() -> new DaoException("AcquiringBank with ID=" + id + " not found"));
    }

    @Override
    public List<AcquiringBank> findAll() {
        return acquiringBankDao.findAll();
    }

    @Override
    public AcquiringBank save(AcquiringBank entity) {
        return acquiringBankDao.save(entity);
    }

    @Override
    public AcquiringBank update(AcquiringBank entity) {
        return acquiringBankDao.update(entity);
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