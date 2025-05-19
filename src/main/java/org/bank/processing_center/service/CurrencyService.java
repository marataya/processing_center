package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
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
    public void save(Currency entity) {
        currencyDao.save(entity);
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
        return currencyDao.findById(id);
    }

    @Override
    public void update(Currency entity) {
        currencyDao.update(entity);
    }
}