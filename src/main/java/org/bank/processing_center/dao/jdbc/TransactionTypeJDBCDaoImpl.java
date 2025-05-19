package org.bank.processing_center.dao.jdbc;

import org.bank.processing_center.configuration.JDBCConfig;
import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.TransactionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionTypeJDBCDaoImpl implements Dao<TransactionType, Long> {

    @Override
    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS transaction_type (
                id BIGINT PRIMARY KEY,
                type_name VARCHAR(255)
                )""";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица TransactionType создана (или уже существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы TransactionType: " + e.getMessage());
        }
    }

    @Override
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS transaction_type CASCADE";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица TransactionType удалена (если существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы TransactionType: " + e.getMessage());
        }
    }

    @Override
    public void clearTable() {
        String sql = "DELETE FROM transaction_type";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица TransactionType очищена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы TransactionType: " + e.getMessage());
        }
    }

    @Override
    public void save(TransactionType transactionType) {
        String sql = "INSERT INTO transaction_type (id, type_name) VALUES (?, ?)";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, transactionType.getId());
            preparedStatement.setString(2, transactionType.getTypeName());
            preparedStatement.executeUpdate();
            System.out.println("TransactionType добавлен: " + transactionType);
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении TransactionType: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM transaction_type WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("TransactionType с id " + id + " удален.");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении TransactionType: " + e.getMessage());
        }
    }

    @Override
    public List<TransactionType> findAll() {
        List<TransactionType> transactionTypes = new ArrayList<>();
        String sql = "SELECT id, type_name FROM transaction_type";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                TransactionType transactionType = new TransactionType();
                transactionType.setId(resultSet.getLong("id"));
                transactionType.setTypeName(resultSet.getString("type_name"));
                transactionTypes.add(transactionType);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех TransactionType: " + e.getMessage());
        }
        return transactionTypes;
    }

    @Override
    public TransactionType findById(Long id) {
        String sql = "SELECT id, type_name FROM transaction_type WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                TransactionType transactionType = new TransactionType();
                transactionType.setId(resultSet.getLong("id"));
                transactionType.setTypeName(resultSet.getString("type_name"));
                return transactionType;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении TransactionType по id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void update(TransactionType transactionType) {
        String sql = "UPDATE transaction_type SET type_name = ? WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, transactionType.getTypeName());
            preparedStatement.setLong(2, transactionType.getId());
            preparedStatement.executeUpdate();
            System.out.println("TransactionType обновлен: " + transactionType);
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении TransactionType: " + e.getMessage());
        }
    }
}