package org.bank.processing_center;

import org.bank.processing_center.configuration.HibernateConfig;
import org.bank.processing_center.configuration.HikariCPDataSource;
import org.bank.processing_center.controller.ApplicationController;

/**
 * Main application class
 */
public class Main {

    public static void main(String[] args) {

        String daoType = "jdbc";

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Application shutting down, closing resources...");
            HikariCPDataSource.closeDataSource();
        }));

        if (!"hibernate".equalsIgnoreCase(daoType) && !"jdbc".equalsIgnoreCase(daoType))
            throw new IllegalArgumentException("Invalid daoType. Must be 'hibernate' or 'jdbc'.");
        try {
            ApplicationController appController = new ApplicationController(daoType);
            appController.run();
        } catch (Exception e) {
            System.err.println("Application encountered a critical error.");
            e.printStackTrace();
        } finally {
            if ("hibernate".equalsIgnoreCase(daoType)) {
                HibernateConfig.shutdown();
            } else if ("jdbc".equalsIgnoreCase(daoType)) {
                // based on your commented-out code.
                // If your JDBCConfig handles this differently, adjust accordingly.
                HikariCPDataSource.closeDataSource();
                System.out.println("JDBC Connection Pool (HikariCP) has been closed.");
            }
        }
    }
}
