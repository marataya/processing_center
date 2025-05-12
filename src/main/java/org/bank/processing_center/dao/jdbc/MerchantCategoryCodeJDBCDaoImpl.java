package org.bank.processing_center.dao.jdbc;

import org.bank.processing_center.configuration.JDBCConfig;
import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.MerchantCategoryCode;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MerchantCategoryCodeJDBCDaoImpl implements Dao<MerchantCategoryCode, Long> {

    @Override
    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS merchant_category_code (
                id BIGINT PRIMARY KEY,
                mcc VARCHAR(255) UNIQUE NOT NULL,
                description VARCHAR(255)
                )""";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица merchant_category_code создана (или уже существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы merchant_category_code: " + e.getMessage());
        }
    }

    @Override
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS merchant_category_code CASCADE";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица merchant_category_code удалена (если существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы merchant_category_code: " + e.getMessage());
        }
    }

    @Override
    public void clearTable() {
        String sql = "DELETE FROM merchant_category_code";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица merchant_category_code очищена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы merchant_category_code: " + e.getMessage());
        }
    }

    @Override
    public void save(MerchantCategoryCode mcc) {
        String sql = "INSERT INTO merchant_category_code (id, mcc, mcc_name) VALUES (?, ?, ?)";
        try (Connection connection = JDBCConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, mcc.getId());
            preparedStatement.setString(2, mcc.getMcc());
            preparedStatement.setString(3, mcc.getMccName());
            preparedStatement.executeUpdate();
            System.out.println("MerchantCategoryCode добавлен: " + mcc);
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении MerchantCategoryCode: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM merchant_category_code WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("MerchantCategoryCode с id " + id + " удален.");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении MerchantCategoryCode: " + e.getMessage());
        }
    }

    @Override
    public List<MerchantCategoryCode> findAll() {
        List<MerchantCategoryCode> mccs = new ArrayList<>();
        String sql = "SELECT id, mcc, mcc_name FROM merchant_category_code";
        try (Connection connection = JDBCConfig.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                MerchantCategoryCode mcc = new MerchantCategoryCode();
                mcc.setId(resultSet.getLong("id"));
                mcc.setMcc(resultSet.getString("mcc"));
                mcc.setMccName(resultSet.getString("mcc_name"));
                mccs.add(mcc);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех MerchantCategoryCode: " + e.getMessage());
        }
        return mccs;
    }

    @Override
    public Optional<MerchantCategoryCode> findById(Long id) {
        String sql = "SELECT id, mcc, mcc_name FROM merchant_category_code WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                MerchantCategoryCode mcc = new MerchantCategoryCode();
                mcc.setId(resultSet.getLong("id"));
                mcc.setMcc(resultSet.getString("mcc"));
                mcc.setMccName(resultSet.getString("mcc_name"));
                return Optional.of(mcc);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении MerchantCategoryCode по id: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void update(MerchantCategoryCode mcc) {
        String sql = "UPDATE merchant_category_code SET mcc = ?, mcc_name = ? WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, mcc.getMcc());
            preparedStatement.setString(2, mcc.getMccName());
            preparedStatement.setLong(3, mcc.getId()); // Assuming getId() is correct for the primary key
            preparedStatement.executeUpdate();
            System.out.println("MerchantCategoryCode обновлен: " + mcc);
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении MerchantCategoryCode: " + e.getMessage());
        }
    }
}