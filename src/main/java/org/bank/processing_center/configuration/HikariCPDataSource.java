package org.bank.processing_center.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * HikariCP connection pool configuration and management
 */
public class HikariCPDataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;

    // Default database connection properties (used if properties file cannot be loaded)
    private static final String DEFAULT_DB_URL = "jdbc:postgresql://localhost:5432/processing_center";
    private static final String DEFAULT_DB_USERNAME = "postgres";
    private static final String DEFAULT_DB_PASSWORD = "pass";

    static {
        try {
            // Load properties from file
            Properties props = loadProperties();

            // Basic configuration
            config.setJdbcUrl(props.getProperty("db.url", DEFAULT_DB_URL));
            config.setUsername(props.getProperty("db.username", DEFAULT_DB_USERNAME));
            config.setPassword(props.getProperty("db.password", DEFAULT_DB_PASSWORD));

            // Connection pool settings
            config.setMaximumPoolSize(Integer.parseInt(props.getProperty("hikari.maximumPoolSize", "13")));
            config.setMinimumIdle(Integer.parseInt(props.getProperty("hikari.minimumIdle", "5")));
            config.setIdleTimeout(Long.parseLong(props.getProperty("hikari.idleTimeout", "30000")));
            config.setMaxLifetime(Long.parseLong(props.getProperty("hikari.maxLifetime", "1800000")));
            config.setConnectionTimeout(Long.parseLong(props.getProperty("hikari.connectionTimeout", "30000")));

            // Connection test query
            config.setConnectionTestQuery("SELECT 1");

            // Pool name for easier identification in monitoring
            config.setPoolName(props.getProperty("hikari.poolName", "ProcessingCenterHikariCP"));

            // Create the data source with our configuration
            dataSource = new HikariDataSource(config);

            System.out.println("HikariCP connection pool initialized successfully.");
        } catch (Exception e) {
            System.err.println("Error initializing HikariCP: " + e.getMessage());
            e.printStackTrace();

            // Fallback to default configuration if there's an error
            initializeDefaultDataSource();
        }
    }

    /**
     * Load database properties from file
     * @return Properties object containing database configuration
     */
    private static Properties loadProperties() {
        Properties props = new Properties();

        try (InputStream input = HikariCPDataSource.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                System.out.println("Unable to find database.properties, using default settings");
                return props;
            }

            props.load(input);
            System.out.println("Database properties loaded successfully");
        } catch (IOException e) {
            System.err.println("Error loading database properties: " + e.getMessage());
        }

        return props;
    }

    /**
     * Initialize data source with default settings if properties cannot be loaded
     */
    private static void initializeDefaultDataSource() {
        try {
            config = new HikariConfig();
            config.setJdbcUrl(DEFAULT_DB_URL);
            config.setUsername(DEFAULT_DB_USERNAME);
            config.setPassword(DEFAULT_DB_PASSWORD);
            config.setMaximumPoolSize(13);
            config.setMinimumIdle(5);
            config.setConnectionTestQuery("SELECT 1");
            config.setPoolName("ProcessingCenterHikariCP-Default");

            dataSource = new HikariDataSource(config);
            System.out.println("HikariCP initialized with default settings");
        } catch (Exception e) {
            System.err.println("Fatal error: Could not initialize database connection pool with default settings");
            throw new RuntimeException("Database connection initialization failed", e);
        }
    }

    /**
     * Get a connection from the pool
     * @return Database connection
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("Data source is not initialized");
        }
        return dataSource.getConnection();
    }

    /**
     * Close the data source and all connections
     */
    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("HikariCP connection pool closed");
        }
    }

    /**
     * Get connection pool statistics for monitoring
     * @return String containing pool statistics
     */
    public static String getPoolStats() {
        if (dataSource == null) {
            return "Connection pool not initialized";
        }

        return String.format(
                "HikariCP Stats: Active=%d, Idle=%d, Waiting=%d, Total=%d",
                dataSource.getHikariPoolMXBean().getActiveConnections(),
                dataSource.getHikariPoolMXBean().getIdleConnections(),
                dataSource.getHikariPoolMXBean().getThreadsAwaitingConnection(),
                dataSource.getHikariPoolMXBean().getTotalConnections()
        );
    }

    // Private constructor to prevent instantiation
    private HikariCPDataSource() {}
}
