package org.bank.processing_center.dao.jdbc;

import org.bank.processing_center.configuration.JDBCConfig;
import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyJDBCDaoImpl implements Dao<Currency, Long> {

    // Column names from Currency.java entity
    private static final String TABLE_NAME = "currency"; // As per @Table(name = "currencies")
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DIGITAL_CODE = "currency_digital_code"; // As per @Column(name = "currency_digital_code")
    private static final String COLUMN_LETTER_CODE = "currency_letter_code";  // As per @Column(name = "currency_letter_code")
    private static final String COLUMN_NAME = "currency_name";          // As per @Column(name = "currency_name")

    @Override
    public void createTable() {
        String sql = String.format("""
                CREATE TABLE IF NOT EXISTS %s (
                    %s BIGINT PRIMARY KEY,
                    %s VARCHAR(3) NOT NULL,
                    %s VARCHAR(3) NOT NULL,
                    %s VARCHAR(255) NOT NULL
                )""", TABLE_NAME, COLUMN_ID, COLUMN_DIGITAL_CODE, COLUMN_LETTER_CODE, COLUMN_NAME);
        // Added NOT NULL constraints as per Currency entity
        // Removed UNIQUE constraints from codes for now, as they might not be globally unique if you have different types of codes.
        // If they should be unique, add UNIQUE back.
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица " + TABLE_NAME + " создана (или уже существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы " + TABLE_NAME + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + " CASCADE";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица " + TABLE_NAME + " удалена (если существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы " + TABLE_NAME + ": " + e.getMessage());
        }
    }

    @Override
    public void clearTable() {
        String sql = "DELETE FROM " + TABLE_NAME;
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица " + TABLE_NAME + " очищена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы " + TABLE_NAME + ": " + e.getMessage());
        }
    }

    @Override
    public void save(Currency currency) {
        String sql = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)",
                TABLE_NAME, COLUMN_ID, COLUMN_DIGITAL_CODE, COLUMN_LETTER_CODE, COLUMN_NAME);
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, currency.getId());
            preparedStatement.setString(2, currency.getCurrencyDigitalCode());
            preparedStatement.setString(3, currency.getCurrencyLetterCode());
            preparedStatement.setString(4, currency.getCurrencyName());
            preparedStatement.executeUpdate();
            System.out.println("Currency добавлен: " + currency);
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении Currency: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, COLUMN_ID);
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Currency с id " + id + " удален.");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении Currency: " + e.getMessage());
        }
    }

    private Currency mapResultSetToCurrency(ResultSet resultSet) throws SQLException {
        Currency currency = new Currency();
        currency.setId(resultSet.getLong(COLUMN_ID));
        currency.setCurrencyDigitalCode(resultSet.getString(COLUMN_DIGITAL_CODE));
        currency.setCurrencyLetterCode(resultSet.getString(COLUMN_LETTER_CODE));
        currency.setCurrencyName(resultSet.getString(COLUMN_NAME));
        return currency;
    }

    @Override
    public List<Currency> findAll() {
        List<Currency> currencies = new ArrayList<>();
        String sql = String.format("SELECT %s, %s, %s, %s FROM %s",
                COLUMN_ID, COLUMN_DIGITAL_CODE, COLUMN_LETTER_CODE, COLUMN_NAME, TABLE_NAME);
        try (Connection connection = JDBCConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                currencies.add(mapResultSetToCurrency(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех Currency: " + e.getMessage());
            e.printStackTrace();
        }
        return currencies;
    }

    @Override
    public Optional<Currency> findById(Long id) {
        String sql = String.format("SELECT %s, %s, %s, %s FROM %s WHERE %s = ?",
                COLUMN_ID, COLUMN_DIGITAL_CODE, COLUMN_LETTER_CODE, COLUMN_NAME, TABLE_NAME, COLUMN_ID);
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapResultSetToCurrency(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении Currency по id: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(Currency currency) {
        String sql = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?",
                TABLE_NAME, COLUMN_DIGITAL_CODE, COLUMN_LETTER_CODE, COLUMN_NAME, COLUMN_ID);
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, currency.getCurrencyDigitalCode());
            preparedStatement.setString(2, currency.getCurrencyLetterCode());
            preparedStatement.setString(3, currency.getCurrencyName());
            preparedStatement.setLong(4, currency.getId());
            preparedStatement.executeUpdate();
            System.out.println("Currency обновлен: " + currency);
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении Currency: " + e.getMessage());
            e.printStackTrace();
        }
    }
}