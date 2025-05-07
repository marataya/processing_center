package org.bank.processing_center.configuration;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * JDBC configuration and connection management
 */
public class JDBCConfig {

    private static Connection connection;

    /**
     * Get a database connection
     * @return Database connection
     */
    public static Connection getConnection() {
        try {
            // Get connection from HikariCP pool
            connection = HikariCPDataSource.getConnection();
            return connection;
        } catch (SQLException e) {
            System.err.println("Error getting database connection: " + e.getMessage());
            throw new RuntimeException("Failed to get database connection", e);
        }
    }

    /**
     * Close the database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        } finally {
            // Close the data source when the application shuts down
            HikariCPDataSource.closeDataSource();
        }
    }
}
