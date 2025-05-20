package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.helper.exception.DaoException;
import org.bank.processing_center.model.Currency;

import java.util.List;

public class CurrencyService implements Service<Currency, Long> {

    private final Dao<Currency, Long> currencyDao;

    public CurrencyService(Dao<Currency, Long> dao) {
        currencyDao = dao;
    }

    @Override
    public void createTable() {
        currencyDao.createTable();
    }

    @Override
    public void dropTable() {
        currencyDao.dropTable();
    }

    @Override
    public void clearTable() {
        currencyDao.clearTable();
    }

    @Override
    public Currency save(Currency entity) {
        return currencyDao.save(entity);
    }

    @Override
    public void delete(Long id) {
        currencyDao.delete(id);
    }

    @Override
    public List<Currency> findAll() {
        return currencyDao.findAll();
    }

    @Override
    public Currency findById(Long id) {
        return currencyDao.findById(id).orElseThrow(() -> new DaoException("Currency " + id + " not found"));
    }

    @Override
    public Currency update(Currency entity) {
        return currencyDao.update(entity);
    }
}