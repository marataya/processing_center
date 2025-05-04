package org.bank.processing_center.util;

import org.bank.processing_center.dao.jdbc.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseInitializer {

    private final Connection connection;

    public DatabaseInitializer() {
        this.connection = JDBCConfig.getConnection();
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
            System.err.println("Ошибка при инициализации базы данных: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void createAllTables() {
        try {
            // Создание таблиц в правильном порядке
            new CardStatusJDBCDaoImpl().createTable();
            new PaymentSystemJDBCDaoImpl().createTable();
            new CurrencyJDBCDaoImpl().createTable();
            new IssuingBankJDBCDaoImpl().createTable();
            new AccountJDBCDaoImpl().createTable();
            new CardJDBCDaoImpl().createTable();

            System.out.println("Все таблицы успешно созданы.");
        } catch (Exception e) {
            System.err.println("Ошибка при создании таблиц: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
