package org.bank.processing_center.dao.jdbc;

import org.bank.processing_center.configuration.JDBCConfig;
import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.ResponseCode;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResponseCodeJDBCDaoImpl implements Dao<ResponseCode, Long> {

    @Override
    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS response_codes (
                id BIGINT PRIMARY KEY,
                error_code VARCHAR(255) UNIQUE NOT NULL,
                error_description VARCHAR(255),
                error_level VARCHAR(255)
                )""";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица ResponseCode создана (или уже существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы ResponseCode: " + e.getMessage());
        }
    }

    @Override
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS response_codes CASCADE";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица ResponseCode удалена (если существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы ResponseCode: " + e.getMessage());
        }
    }

    @Override
    public void clearTable() {
        String sql = "DELETE FROM response_codes";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица ResponseCode очищена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы ResponseCode: " + e.getMessage());
        }
    }

    @Override
    public void save(ResponseCode responseCode) {
        String sql = "INSERT INTO response_codes (id, error_code, error_description, error_level) VALUES (?, ?, ?, ?)";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, responseCode.getId());
            preparedStatement.setString(2, responseCode.getErrorCode());
            preparedStatement.setString(3, responseCode.getErrorDescription());
            preparedStatement.setString(4, responseCode.getErrorLevel());
            preparedStatement.executeUpdate();
            System.out.println("ResponseCode добавлен: " + responseCode);
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении ResponseCode: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM response_codes WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("ResponseCode с id " + id + " удален.");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении ResponseCode: " + e.getMessage());
        }
    }

    @Override
    public List<ResponseCode> findAll() {
        List<ResponseCode> responseCodes = new ArrayList<>();
        String sql = "SELECT id, error_code, error_description, error_level FROM response_codes";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                ResponseCode responseCode = new ResponseCode();
                responseCode.setId(resultSet.getLong("id"));
                responseCode.setErrorCode(resultSet.getString("error_code"));
                responseCode.setErrorDescription(resultSet.getString("error_description"));
                responseCode.setErrorLevel(resultSet.getString("error_level"));
                responseCodes.add(responseCode);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех ResponseCode: " + e.getMessage());
        }
        return responseCodes;
    }

    @Override
    public Optional<ResponseCode> findById(Long id) {
        String sql = "SELECT id, error_code, error_description, error_level FROM response_codes WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ResponseCode responseCode = new ResponseCode();
                responseCode.setId(resultSet.getLong("id")); // Keep getId for entity id
                responseCode.setErrorCode(resultSet.getString("error_code"));
                responseCode.setErrorDescription(resultSet.getString("error_description"));
                return Optional.of(responseCode);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении ResponseCode по id: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void update(ResponseCode responseCode) {
        String sql = "UPDATE response_codes SET error_code = ?, error_description = ?, error_level = ? WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, responseCode.getErrorCode());
            preparedStatement.setString(2, responseCode.getErrorDescription());
            preparedStatement.setLong(3, responseCode.getId());
            preparedStatement.executeUpdate();
            System.out.println("ResponseCode обновлен: " + responseCode);
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении ResponseCode: " + e.getMessage());
        }
    }
}