package org.bank.processing_center;

import org.bank.processing_center.configuration.HikariCPDataSource;
import org.bank.processing_center.controller.ApplicationController;

/**
 * Main application class
 */
public class Main {
    public static void main(String[] args) {
        // Add shutdown hook to ensure connection pool is closed properly
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Application shutting down, closing resources...");
            HikariCPDataSource.closeDataSource();
        }));

        try {
            // Initialize application controller
            ApplicationController appController = new ApplicationController("jdbc");

            // Run the application
            appController.run();

        } catch (Exception e) {
            System.err.println("Критическая ошибка при выполнении приложения: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
