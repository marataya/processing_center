package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.IssuingBank;

import java.util.List;
import java.util.Optional;

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
    public void save(IssuingBank entity) {
        issuingBankDao.save(entity);
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
    public Optional<IssuingBank> findById(Long id) {
        return issuingBankDao.findById(id);
    }

    @Override
    public void update(IssuingBank entity) {
        issuingBankDao.update(entity);
    }


}