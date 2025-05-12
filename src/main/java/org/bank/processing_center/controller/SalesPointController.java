package org.bank.processing_center.controller;

import org.bank.processing_center.model.SalesPoint;
import org.bank.processing_center.service.SalesPointService;
import org.bank.processing_center.view.ConsoleView;

import java.util.List;
import java.util.Optional;

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

   public void addEntity(SalesPoint salesPoint) {
      salesPointService.save(salesPoint);
      view.showMessage("SalesPoint added: " + salesPoint);
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

   public void updateEntity(SalesPoint salesPoint) {
      try {
         salesPointService.update(salesPoint);
         view.showMessage("Sales Point updated: " + salesPoint);
      } catch (Exception e) {
         view.showError("Error updating sales point: " + e.getMessage());
      }
   }

   public void deleteEntity(Long id) {
      salesPointService.delete(id);
      view.showMessage("SalesPoint with ID " + id + " deleted.");
   }

   public Optional<SalesPoint> findById(Long id) {
      Optional<SalesPoint> salesPoint = salesPointService.findById(id);
      if (salesPoint.isPresent()) {
         view.showMessage("SalesPoint found: " + salesPoint.get());
      } else {
         view.showMessage("SalesPoint with ID " + id + " not found.");
      }
      return salesPoint;
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