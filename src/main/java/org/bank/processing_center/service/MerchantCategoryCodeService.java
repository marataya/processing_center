package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.dao.factory.DaoFactory;
import org.bank.processing_center.model.MerchantCategoryCode;

import java.util.List;
import java.util.Optional;

public class MerchantCategoryCodeService implements Service<MerchantCategoryCode, Long> {

    private final Dao<MerchantCategoryCode, Long> merchantCategoryCodeDao;

    public MerchantCategoryCodeService(String daoType) {
        merchantCategoryCodeDao = DaoFactory.getInstance(daoType).getMerchantCategoryCodeDao();
    }

    @Override
    public void createTable() {
        merchantCategoryCodeDao.createTable();
    }

    @Override
    public void dropTable() {
        merchantCategoryCodeDao.dropTable();
    }

    @Override
    public void clearTable() {
        merchantCategoryCodeDao.clearTable();
    }

    @Override
    public void save(MerchantCategoryCode entity) {
        merchantCategoryCodeDao.save(entity);
    }

    @Override
    public void delete(Long id) {
        merchantCategoryCodeDao.delete(id);
    }

    @Override
    public Optional<MerchantCategoryCode> findById(Long id) {
        return merchantCategoryCodeDao.findById(id);
    }

    @Override
    public List<MerchantCategoryCode> findAll() {
        return merchantCategoryCodeDao.findAll();
    }

    @Override
    public void update(MerchantCategoryCode entity) {
        merchantCategoryCodeDao.update(entity);
    }

}