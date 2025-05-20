package org.bank.processing_center.dao.jdbc;

import org.bank.processing_center.configuration.JDBCConfig;
import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.PaymentSystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentSystemJDBCDaoImpl implements Dao<PaymentSystem, Long> {

    @Override
    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS payment_system (\
                id BIGINT PRIMARY KEY,\
                payment_system_name VARCHAR(50)\
                )""";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица PaymentSystem создана (или уже существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы PaymentSystem: " + e.getMessage());
        }
    }

    @Override
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS payment_system CASCADE";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица PaymentSystem удалена (если существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы PaymentSystem: " + e.getMessage());
        }
    }

    @Override
    public void clearTable() {
        String sql = "DELETE FROM payment_system";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица PaymentSystem очищена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы PaymentSystem: " + e.getMessage());
        }
    }

    @Override
    public PaymentSystem save(PaymentSystem paymentSystem) {
        String sql = "INSERT INTO payment_system (id, payment_system_name) VALUES (?, ?)";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, paymentSystem.getId());
            preparedStatement.setString(2, paymentSystem.getPaymentSystemName());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("PaymentSystem добавлена: " + paymentSystem);
                return paymentSystem;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении PaymentSystem: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM payment_system WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("PaymentSystem с id " + id + " удалена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении PaymentSystem: " + e.getMessage());
        }
    }

    @Override
    public List<PaymentSystem> findAll() {
        List<PaymentSystem> paymentSystems = new ArrayList<>();
        String sql = "SELECT id, payment_system_name FROM payment_system";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                PaymentSystem paymentSystem = new PaymentSystem(
                        resultSet.getLong("id"),
                        resultSet.getString("payment_system_name")
                );
                paymentSystems.add(paymentSystem);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех PaymentSystem: " + e.getMessage());
        }
        return paymentSystems;
    }

    @Override
    public PaymentSystem findById(Long id) {
        String sql = "SELECT id, payment_system_name FROM payment_system WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                PaymentSystem paymentSystem = new PaymentSystem(
                        resultSet.getLong("id"),
                        resultSet.getString("payment_system_name")
                );
                return paymentSystem;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении PaymentSystem по id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public PaymentSystem update(PaymentSystem paymentSystem) {
        String sql = "UPDATE payment_system SET payment_system_name = ? WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, paymentSystem.getPaymentSystemName());
            preparedStatement.setLong(2, paymentSystem.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("PaymentSystem обновлена: " + paymentSystem);
                return paymentSystem;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении PaymentSystem: " + e.getMessage());
        }
        return null;
    }
}
