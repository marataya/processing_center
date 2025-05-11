package org.bank.processing_center.util;

import org.bank.processing_center.configuration.JDBCConfig;
import org.bank.processing_center.model.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.bank.processing_center.dao.jdbc.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

/**
 * Utility class for initializing and creating database tables for a processing center application.
 * Supports initialization through JDBC or Hibernate based on the specified initialization type.
 * 
 * This class provides methods to:
 * - Initialize the database using a SQL script
 * - Create database tables using either JDBC or Hibernate approaches
 */
public class DatabaseInitializer {

    private final Connection connection;
    private String initializationType = "jdbc"; // Default to JDBC

    public DatabaseInitializer(String initializationType) {
        this.connection = JDBCConfig.getConnection();
        this.initializationType = initializationType;

    }

    public void initializeDatabase() {
        try {
            // Чтение SQL-скрипта из ресурсов
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("sql/create_processing_center_db.sql");
            if (inputStream == null) {
                System.err.println("Не удалось найти SQL-скрипт в ресурсах.");
                return;
            }

            String sqlScript = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n"));

            // Выполнение SQL-скрипта
            Statement statement = connection.createStatement();
            statement.execute(sqlScript);
            System.out.println("База данных успешно инициализирована.");

        } catch (Exception e) {
            System.err.println("Ошибка при инициализации базы данных (SQL script): " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void createAllTables() {
        try {
            if ("jdbc".equalsIgnoreCase(initializationType)) {
                // Создание таблиц в правильном порядке (JDBC)
                new CardStatusJDBCDaoImpl().createTable();
                new PaymentSystemJDBCDaoImpl().createTable();
                new CurrencyJDBCDaoImpl().createTable();
                new IssuingBankJDBCDaoImpl().createTable();
                new AccountJDBCDaoImpl().createTable();
                new CardJDBCDaoImpl().createTable();
            } else if ("hibernate".equalsIgnoreCase(initializationType)) {
                // Создание таблиц с помощью Hibernate
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder()
                        .configure("hibernate.cfg.xml");

                MetadataSources metadataSources = new MetadataSources(registryBuilder.build());
                metadataSources.addAnnotatedClass(Account.class);
                metadataSources.addAnnotatedClass(Card.class);
                metadataSources.addAnnotatedClass(CardStatus.class);
                metadataSources.addAnnotatedClass(Currency.class);
                metadataSources.addAnnotatedClass(IssuingBank.class);
                metadataSources.addAnnotatedClass(PaymentSystem.class);
            }

            System.out.println("Все таблицы успешно созданы.");
        } catch (Exception e) {
            System.err.println("Ошибка при создании таблиц: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
