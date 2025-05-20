package org.bank.processing_center.dao.jdbc;

import org.bank.processing_center.configuration.JDBCConfig;
import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.MerchantCategoryCode;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MerchantCategoryCodeJDBCDaoImpl implements Dao<MerchantCategoryCode, Long> {

    // Define actual column names as they should be in your 'merchant_category_code' table
    private static final String TABLE_NAME = "merchant_category_code";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_MCC_CODE = "mcc_code"; // For the 4-digit code like "5411"
    private static final String COLUMN_MCC_DESCRIPTION = "mcc_description"; // For the descriptive text

    @Override
    public void createTable() {
        String sql = String.format("""
                CREATE TABLE IF NOT EXISTS %s (
                    %s BIGINT PRIMARY KEY,
                    %s VARCHAR(4) UNIQUE NOT NULL,
                    %s VARCHAR(255) NOT NULL
                )""", TABLE_NAME, COLUMN_ID, COLUMN_MCC_CODE, COLUMN_MCC_DESCRIPTION);
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
    public MerchantCategoryCode save(MerchantCategoryCode mcc) {
        // Corrected INSERT statement to use COLUMN_MCC_DESCRIPTION instead of a non-existent "mcc_name"
        String sql = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)",
                TABLE_NAME, COLUMN_ID, COLUMN_MCC_CODE, COLUMN_MCC_DESCRIPTION);
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, mcc.getId());
            preparedStatement.setString(2, mcc.getMcc()); // Assuming model field is 'code'
            preparedStatement.setString(3, mcc.getMccName()); // Assuming model field is 'description'
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("MerchantCategoryCode добавлен: " + mcc);
                return mcc;
            }
        } catch (SQLException e) {
            // This is where your error "ERROR: column "mcc_name" of relation "merchant_category_code" does not exist" originates
            System.err.println("Ошибка при добавлении MerchantCategoryCode: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        String sql = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, COLUMN_ID);
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("MerchantCategoryCode с id " + id + " удален.");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении MerchantCategoryCode: " + e.getMessage());
        }
    }

    private MerchantCategoryCode mapResultSetToMcc(ResultSet resultSet) throws SQLException {
        MerchantCategoryCode mcc = new MerchantCategoryCode();
        mcc.setId(resultSet.getLong(COLUMN_ID));
        mcc.setMcc(resultSet.getString(COLUMN_MCC_CODE));
        mcc.setMccName(resultSet.getString(COLUMN_MCC_DESCRIPTION));
        return mcc;
    }

    @Override
    public List<MerchantCategoryCode> findAll() {
        List<MerchantCategoryCode> mccs = new ArrayList<>();
        String sql = String.format("SELECT %s, %s, %s FROM %s",
                COLUMN_ID, COLUMN_MCC_CODE, COLUMN_MCC_DESCRIPTION, TABLE_NAME);
        try (Connection connection = JDBCConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                mccs.add(mapResultSetToMcc(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех MerchantCategoryCode: " + e.getMessage());
            e.printStackTrace();
        }
        return mccs;
    }

    @Override
    public Optional<MerchantCategoryCode> findById(Long id) {
        String sql = String.format("SELECT %s, %s, %s FROM %s WHERE %s = ?",
                COLUMN_ID, COLUMN_MCC_CODE, COLUMN_MCC_DESCRIPTION, TABLE_NAME, COLUMN_ID);
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapResultSetToMcc(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении MerchantCategoryCode по id: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public MerchantCategoryCode update(MerchantCategoryCode mcc) {
        // Corrected UPDATE statement
        String sql = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?",
                TABLE_NAME, COLUMN_MCC_CODE, COLUMN_MCC_DESCRIPTION, COLUMN_ID);
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, mcc.getMcc());
            preparedStatement.setString(2, mcc.getMccName());
            preparedStatement.setLong(3, mcc.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("MerchantCategoryCode обновлен: " + mcc);
                return mcc;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении MerchantCategoryCode: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}