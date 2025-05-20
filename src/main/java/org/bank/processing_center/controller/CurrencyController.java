package org.bank.processing_center.controller;

import org.bank.processing_center.model.Currency;
import org.bank.processing_center.service.CurrencyService;
import org.bank.processing_center.view.ConsoleView;

import java.util.List;

public class CurrencyController implements Controller<Currency, Long> {

   private final CurrencyService currencyService; // Service for Currency entity
   private final ConsoleView view; // View for console output

   // Constructor for CurrencyController
   public CurrencyController(CurrencyService currencyService, ConsoleView view) {
      this.currencyService = currencyService;
      this.view = view;
   }

   // Method to create the currencies table
   @Override
   public void createTable() {
      view.showMessage("Attempting to create currency table...");
      currencyService.createTable();
      view.showMessage("Currency table created.");
   }

   // Method to drop the currencies table
   @Override
   public void dropTable() { // Method to drop the accounts table
      view.showMessage("Attempting to drop currency table...");
      currencyService.dropTable();
      view.showMessage("Currency table dropped.");
   }

   // Method to clear the currencies table
   @Override
   public void clearTable() {
      view.showMessage("Attempting to clear currency table...");
      currencyService.clearTable();
      view.showMessage("Currency table cleared.");
   }

   // Method to add a new currency
   @Override
   public Currency addEntity(Currency entity) {
      try {
         currencyService.save(entity);
         view.showMessage("Currency added successfully.");
         return entity;
      } catch (Exception e) {
         view.showError("Error adding currency: " + e.getMessage());
         return null;
      }
   }

   // Method to update an existing currency
   @Override
   public Currency updateEntity(Currency entity) {
      try {
         currencyService.update(entity);
         view.showMessage("Currency updated successfully.");
         return entity;
      } catch (Exception e) {
         view.showError("Error updating currency: " + e.getMessage());
         return null;
      }
   }

   @Override
   public void deleteEntity(Long id) {
      currencyService.delete(id);
   }

   @Override
   public Currency findById(Long id) {
      Currency currency = currencyService.findById(id);
      if (currency != null) {
         view.showMessage("Found Currency: " + currency.toString());
      } else {
         view.showMessage("Currency with ID " + id + " not found.");
      }
      return currency;
   }

   // Method to retrieve all currencies
   @Override
   public List<Currency> getAllEntities() { // Method to get all accounts
      try {
         List<Currency> currencies = currencyService.findAll();
         view.showList(currencies, "Terminals List:");
         return currencies;
      } catch (Exception e) {
         view.showError("Ошибка при получении списка currency: " + e.getMessage());
         return List.of();
      }
   }
}