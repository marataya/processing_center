package org.bank.processing_center.dao.jdbc;

import org.bank.processing_center.configuration.JDBCConfig;
import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.IssuingBank;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IssuingBankJDBCDaoImpl implements Dao<IssuingBank, Long> {

    @Override
    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS issuing_bank (\
                id BIGINT PRIMARY KEY,\
                bic VARCHAR(9),\
                bin VARCHAR(5),\
                abbreviated_name VARCHAR(255)\
                )""";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица IssuingBank создана (или уже существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы IssuingBank: " + e.getMessage());
        }
    }

    @Override
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS issuing_bank CASCADE";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица IssuingBank удалена (если существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы IssuingBank: " + e.getMessage());
        }
    }

    @Override
    public void clearTable() {
        String sql = "DELETE FROM issuing_bank";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица IssuingBank очищена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы IssuingBank: " + e.getMessage());
        }
    }

    @Override
    public IssuingBank save(IssuingBank issuingBank) {
        String sql = "INSERT INTO issuing_bank (id, bic, bin, abbreviated_name) VALUES (?, ?, ?, ?)";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, issuingBank.getId());
            preparedStatement.setString(2, issuingBank.getBic());
            preparedStatement.setString(3, issuingBank.getBin());
            preparedStatement.setString(4, issuingBank.getAbbreviatedName());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("IssuingBank добавлен: " + issuingBank);
                return issuingBank;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении IssuingBank: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM issuing_bank WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("IssuingBank с id " + id + " удален.");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении IssuingBank: " + e.getMessage());
        }
    }

    @Override
    public List<IssuingBank> findAll() {
        List<IssuingBank> issuingBanks = new ArrayList<>();
        String sql = "SELECT id, bic, bin, abbreviated_name FROM issuing_bank";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                IssuingBank issuingBank = new IssuingBank(
                        resultSet.getLong("id"),
                        resultSet.getString("bic"),
                        resultSet.getString("bin"),
                        resultSet.getString("abbreviated_name")
                );
                issuingBanks.add(issuingBank);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех IssuingBank: " + e.getMessage());
        }
        return issuingBanks;
    }

    @Override
    public Optional<IssuingBank> findById(Long id) {
        String sql = "SELECT id, bic, bin, abbreviated_name FROM issuing_bank WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                IssuingBank issuingBank = new IssuingBank(
                        resultSet.getLong("id"),
                        resultSet.getString("bic"),
                        resultSet.getString("bin"),
                        resultSet.getString("abbreviated_name")
                );
                return Optional.of(issuingBank);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении IssuingBank по id: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public IssuingBank update(IssuingBank issuingBank) {
        String sql = "UPDATE issuing_bank SET bic = ?, bin = ?, abbreviated_name = ? WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, issuingBank.getBic());
            preparedStatement.setString(2, issuingBank.getBin());
            preparedStatement.setString(3, issuingBank.getAbbreviatedName());
            preparedStatement.setLong(4, issuingBank.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("IssuingBank обновлен: " + issuingBank);
                return issuingBank;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении IssuingBank: " + e.getMessage());
        }
        return null;
    }
}