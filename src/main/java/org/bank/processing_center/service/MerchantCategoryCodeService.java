package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.helper.exception.DaoException;
import org.bank.processing_center.model.MerchantCategoryCode;

import java.util.List;

public class MerchantCategoryCodeService implements Service<MerchantCategoryCode, Long> {

    private final Dao<MerchantCategoryCode, Long> merchantCategoryCodeDao;

    public MerchantCategoryCodeService(Dao<MerchantCategoryCode, Long> dao) {
        merchantCategoryCodeDao = dao;
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
    public MerchantCategoryCode save(MerchantCategoryCode entity) {
        return merchantCategoryCodeDao.save(entity);
    }

    @Override
    public void delete(Long id) {
        merchantCategoryCodeDao.delete(id);
    }

    @Override
    public MerchantCategoryCode findById(Long id) {
        return merchantCategoryCodeDao.findById(id).orElseThrow(() -> new DaoException("Merchant Category Code with ID " + id + " not found"));
    }

    @Override
    public List<MerchantCategoryCode> findAll() {
        return merchantCategoryCodeDao.findAll();
    }

    @Override
    public MerchantCategoryCode update(MerchantCategoryCode entity) {
        return merchantCategoryCodeDao.update(entity);
    }

}