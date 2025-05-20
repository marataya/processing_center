package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.SalesPoint;

import java.util.List;

public class SalesPointService implements Service<SalesPoint, Long> {

   private final Dao<SalesPoint, Long> salesPointDao;

   public SalesPointService(Dao<SalesPoint, Long> dao) {
      this.salesPointDao = dao;
   }

   @Override
   public void createTable() {
      salesPointDao.createTable();
   }

   @Override
   public void dropTable() {
      salesPointDao.dropTable();
   }

   @Override
   public void clearTable() {
      salesPointDao.clearTable();
   }

   @Override
   public SalesPoint save(SalesPoint entity) {
      return salesPointDao.save(entity);
   }

   @Override
   public void delete(Long id) {
      salesPointDao.delete(id);
   }

   @Override
   public List<SalesPoint> findAll() {
      return salesPointDao.findAll();
   }

   @Override
   public SalesPoint findById(Long id) {
      return salesPointDao.findById(id);
   }

   @Override
   public SalesPoint update(SalesPoint entity) {
      return salesPointDao.update(entity);
   }

}