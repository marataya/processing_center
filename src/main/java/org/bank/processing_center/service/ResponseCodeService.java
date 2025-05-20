package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.helper.exception.DaoException;
import org.bank.processing_center.model.ResponseCode;

import java.util.List;

public class ResponseCodeService implements Service<ResponseCode, Long> {

    private final Dao<ResponseCode, Long> responseCodeDao;

    public ResponseCodeService(Dao<ResponseCode, Long> dao) {
        this.responseCodeDao = dao;
    }

    // Implement methods from Service interface here
    @Override
    public void createTable() {
        responseCodeDao.createTable();
    }

    @Override
    public void dropTable() {
        responseCodeDao.dropTable();
    }

    @Override
    public void clearTable() {
        responseCodeDao.clearTable();
    }

    @Override
    public ResponseCode save(ResponseCode entity) {
        return responseCodeDao.save(entity);
    }

    @Override
    public void delete(Long id) {
        responseCodeDao.delete(id);
    }

    @Override
    public List<ResponseCode> findAll() {
        return responseCodeDao.findAll();
    }

    @Override
    public ResponseCode findById(Long id) {
        return responseCodeDao.findById(id).orElseThrow(() -> new DaoException("Error finding ResponseCode by ID=" + id));
    }

    @Override
    public ResponseCode update(ResponseCode entity) {
        return responseCodeDao.update(entity);
    }
}