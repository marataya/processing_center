package org.bank.processing_center.dao.jdbc;

import org.bank.processing_center.configuration.JDBCConfig;
import org.bank.processing_center.dao.base.Dao;
import org.bank.processing_center.model.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyJDBCDaoImpl implements Dao<Currency, Long> {

    private final Connection connection;

    public CurrencyJDBCDaoImpl() {
        this.connection = JDBCConfig.getConnection();
    }

    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS currency (" +
                "id BIGINT PRIMARY KEY," +
                "currency_digital_code VARCHAR(3)," +
                "currency_letter_code VARCHAR(3)," +
                "currency_name VARCHAR(255)" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица Currency создана (или уже существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы Currency: " + e.getMessage());
        }
    }

    @Override
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS currency";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица Currency удалена (если существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы Currency: " + e.getMessage());
        }
    }

    @Override
    public void clearTable() {
        String sql = "DELETE FROM currency";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица Currency очищена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы Currency: " + e.getMessage());
        }
    }

    @Override
    public void save(Currency currency) {
        String sql = "INSERT INTO currency (id, currency_digital_code, currency_letter_code, currency_name) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, currency.getId());
            preparedStatement.setString(2, currency.getCurrencyDigitalCode());
            preparedStatement.setString(3, currency.getCurrencyLetterCode());
            preparedStatement.setString(4, currency.getCurrencyName());
            preparedStatement.executeUpdate();
            System.out.println("Currency добавлена: " + currency);
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении Currency: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM currency WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Currency с id " + id + " удалена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении Currency: " + e.getMessage());
        }
    }

    @Override
    public List<Currency> findAll() {
        List<Currency> currencies = new ArrayList<>();
        String sql = "SELECT id, currency_digital_code, currency_letter_code, currency_name FROM currency";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Currency currency = new Currency(
                        resultSet.getLong("id"),
                        resultSet.getString("currency_digital_code"),
                        resultSet.getString("currency_letter_code"),
                        resultSet.getString("currency_name")
                );
                currencies.add(currency);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех Currency: " + e.getMessage());
        }
        return currencies;
    }

    @Override
    public Optional<Currency> findById(Long id) {
        String sql = "SELECT id, currency_digital_code, currency_letter_code, currency_name FROM currency WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Currency currency = new Currency(
                        resultSet.getLong("id"),
                        resultSet.getString("currency_digital_code"),
                        resultSet.getString("currency_letter_code"),
                        resultSet.getString("currency_name")
                );
                return Optional.of(currency);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении Currency по id: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void update(Currency currency) {
        String sql = "UPDATE currency SET currency_digital_code = ?, currency_letter_code = ?, currency_name = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, currency.getCurrencyDigitalCode());
            preparedStatement.setString(2, currency.getCurrencyLetterCode());
            preparedStatement.setString(3, currency.getCurrencyName());
            preparedStatement.setLong(4, currency.getId());
            preparedStatement.executeUpdate();
            System.out.println("Currency обновлена: " + currency);
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении Currency: " + e.getMessage());
        }
    }
}

