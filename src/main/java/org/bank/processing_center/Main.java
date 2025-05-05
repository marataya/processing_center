package org.bank.processing_center;

import org.bank.processing_center.configuration.JDBCConfig;
import org.bank.processing_center.controller.ApplicationController;

/**
 * Main application class
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Initialize application controller
            ApplicationController appController = new ApplicationController();

            // Run the application
            appController.run();

            // Close database connection
            JDBCConfig.closeConnection();
        } catch (Exception e) {
            System.err.println("Критическая ошибка при выполнении приложения: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
