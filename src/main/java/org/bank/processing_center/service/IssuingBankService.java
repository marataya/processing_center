package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.helper.exception.DaoException;
import org.bank.processing_center.model.IssuingBank;

import java.util.List;

public class IssuingBankService implements Service<IssuingBank, Long> {

    private final Dao<IssuingBank, Long> issuingBankDao;

    public IssuingBankService(Dao<IssuingBank, Long> dao) {
        issuingBankDao = dao;
    }

    @Override
    public void createTable() {
        issuingBankDao.createTable();
    }

    @Override
    public void dropTable() {
        issuingBankDao.dropTable();
    }

    @Override
    public void clearTable() {
        issuingBankDao.clearTable();
    }

    @Override
    public IssuingBank save(IssuingBank entity) {
        return issuingBankDao.save(entity);
    }

    @Override
    public void delete(Long id) {
        issuingBankDao.delete(id);
    }

    @Override
    public List<IssuingBank> findAll() {
        return issuingBankDao.findAll();
    }

    @Override
    public IssuingBank findById(Long id) {
        return issuingBankDao.findById(id).orElseThrow(() -> new DaoException("IssuingBank " + id + " not found"));
    }

    @Override
    public IssuingBank update(IssuingBank entity) {
        return issuingBankDao.update(entity);
    }


}