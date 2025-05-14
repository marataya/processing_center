package org.bank.processing_center.dao.jdbc;

import org.bank.processing_center.configuration.JDBCConfig;
import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.AcquiringBank;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AcquiringBankJDBCDaoImpl implements Dao<AcquiringBank, Long> {

    @Override
    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS acquiring_bank (
                    id BIGINT PRIMARY KEY,
                    bic VARCHAR(9),
                    abbreviated_name VARCHAR(255)
                )""";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица acquiring_bank создана (или уже существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы acquiring_bank: " + e.getMessage());
        }
    }

    @Override
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS acquiring_bank CASCADE";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица acquiring_bank удалена (если существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы acquiring_bank: " + e.getMessage());
        }
    }

    @Override
    public void clearTable() {
        String sql = "DELETE FROM acquiring_bank";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица acquiring_bank очищена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы acquiring_bank: " + e.getMessage());
        }
    }

    @Override
    public void save(AcquiringBank acquiringBank) {
        String sql = "INSERT INTO acquiring_bank (id, bic, abbreviated_name) VALUES (?, ?, ?)";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, acquiringBank.getId());
            preparedStatement.setString(2, acquiringBank.getBic());
            preparedStatement.setString(3, acquiringBank.getAbbreviatedName());
            preparedStatement.executeUpdate();
            System.out.println("AcquiringBank добавлен: " + acquiringBank);
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении AcquiringBank: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM acquiring_bank WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("AcquiringBank с id " + id + " удален.");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении AcquiringBank: " + e.getMessage());
        }
    }

    @Override
    public List<AcquiringBank> findAll() {
        List<AcquiringBank> acquiringBanks = new ArrayList<>();
        String sql = "SELECT id, bic, abbreviated_name FROM acquiring_bank";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                AcquiringBank acquiringBank = new AcquiringBank();
                acquiringBank.setId(resultSet.getLong("id"));
                acquiringBank.setBic(resultSet.getString("bic"));
                acquiringBank.setAbbreviatedName(resultSet.getString("abbreviated_name"));
                acquiringBanks.add(acquiringBank);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех AcquiringBank: " + e.getMessage());
        }
        return acquiringBanks;
    }

    @Override
    public Optional<AcquiringBank> findById(Long id) {
        String sql = "SELECT id, bic, abbreviated_name FROM acquiring_bank WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                AcquiringBank acquiringBank = new AcquiringBank();
                acquiringBank.setId(resultSet.getLong("id"));
                acquiringBank.setBic(resultSet.getString("bic"));
                acquiringBank.setAbbreviatedName(resultSet.getString("abbreviated_name"));
                return Optional.of(acquiringBank);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении AcquiringBank по id: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void update(AcquiringBank acquiringBank) {
        String sql = "UPDATE acquiring_bank SET bic = ?, abbreviated_name = ? WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, acquiringBank.getBic());
            preparedStatement.setString(2, acquiringBank.getAbbreviatedName());
            preparedStatement.setLong(3, acquiringBank.getId());
            preparedStatement.executeUpdate();
            System.out.println("AcquiringBank обновлен: " + acquiringBank);
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении AcquiringBank: " + e.getMessage());
        }
    }
}