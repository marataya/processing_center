package org.bank.processing_center.controller;

import org.bank.processing_center.model.SalesPoint;
import org.bank.processing_center.service.SalesPointService;
import org.bank.processing_center.view.ConsoleView;

import java.util.List;

public class SalesPointController implements Controller<SalesPoint, Long> {

   private final SalesPointService salesPointService;
   private final ConsoleView view;

   public SalesPointController(SalesPointService salesPointService, ConsoleView view) {
      this.salesPointService = salesPointService;
      this.view = view;
   }

   public void createTable() {
      salesPointService.createTable();
      view.showMessage("SalesPoint table created.");
   }

   public SalesPoint addEntity(SalesPoint salesPoint) {
      try {
         SalesPoint savedSalesPoint = salesPointService.save(salesPoint);
         view.showMessage("SalesPoint added: " + salesPoint);
         return savedSalesPoint;
      } catch (Exception e) {
         view.showError("Ошибка при добавлении sales_point: " + e.getMessage());
         return null;
      }
   }

   public List<SalesPoint> getAllEntities() {
      try {
         List<SalesPoint> salesPoints = salesPointService.findAll();
         view.showList(salesPoints, "Sales Points List:");
         return salesPoints;
      } catch (Exception e) {
         view.showError("Ошибка при получении списка sales_point: " + e.getMessage());
         return List.of();
      }
   }

   public SalesPoint updateEntity(SalesPoint salesPoint) {
      try {
         SalesPoint updatedSalesPoint = salesPointService.update(salesPoint);
         view.showMessage("SalesPoint updated: " + salesPoint);
         return updatedSalesPoint;
      } catch (Exception e) {
         view.showError("Error updating sales point: " + e.getMessage());
         return null;
      }
   }

   public void deleteEntity(Long id) {
      salesPointService.delete(id);
      view.showMessage("SalesPoint with ID " + id + " deleted.");
   }

   public SalesPoint findById(Long id) {
       try {
           SalesPoint salesPoint = salesPointService.findById(id);
           view.showMessage("SalesPoint found: " + salesPoint.toString());
           return salesPoint;
       } catch (Exception e) {
           view.showError("ERROR: " + e.getMessage());
           return null;
       }
   }

   // Removed methods from Controller interface as they are not part of the
   // requested methods to be similar to AccountController

   @Override
   public void dropTable() {
      salesPointService.dropTable();
   }

   @Override
   public void clearTable() {
      salesPointService.clearTable();
   }

}