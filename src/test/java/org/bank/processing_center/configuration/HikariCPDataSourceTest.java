package org.bank.processing_center.configuration;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class HikariCPDataSourceTest {

    @Test
    public void testGetConnection() {
        try (Connection connection = HikariCPDataSource.getConnection()) {
            assertNotNull(connection);
            assertFalse(connection.isClosed());

            // Test that we can execute a simple query
            try (Statement statement = connection.createStatement()) {
                assertTrue(statement.execute("SELECT 1"));
            }
        } catch (SQLException e) {
            fail("Should be able to get a connection: " + e.getMessage());
        }
    }

    @Test
    public void testMultipleConnections() {
        // Test that we can get multiple connections from the pool
        try (
                Connection connection1 = HikariCPDataSource.getConnection();
                Connection connection2 = HikariCPDataSource.getConnection();
                Connection connection3 = HikariCPDataSource.getConnection()
        ) {
            assertNotNull(connection1);
            assertNotNull(connection2);
            assertNotNull(connection3);

            assertFalse(connection1.isClosed());
            assertFalse(connection2.isClosed());
            assertFalse(connection3.isClosed());

            // Connections should be different objects
            assertNotSame(connection1, connection2);
            assertNotSame(connection1, connection3);
            assertNotSame(connection2, connection3);
        } catch (SQLException e) {
            fail("Should be able to get multiple connections: " + e.getMessage());
        }
    }

    @Test
    public void testConnectionAutoClose() {
        Connection connection = null;
        try {
            connection = HikariCPDataSource.getConnection();
            assertNotNull(connection);
            assertFalse(connection.isClosed());
        } catch (SQLException e) {
            fail("Should be able to get a connection: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    assertTrue(connection.isClosed(), "Connection should be closed");
                } catch (SQLException e) {
                    fail("Should be able to close connection: " + e.getMessage());
                }
            }
        }
    }

    @Test
    public void testGetPoolStats() {
        String stats = HikariCPDataSource.getPoolStats();
        assertNotNull(stats);
        assertTrue(stats.contains("HikariCP Stats"));
    }
}
