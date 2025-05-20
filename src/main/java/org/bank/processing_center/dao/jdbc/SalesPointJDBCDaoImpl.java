package org.bank.processing_center.dao.jdbc;

import org.bank.processing_center.configuration.JDBCConfig;
import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.mapper.SalesPointMapper;
import org.bank.processing_center.model.AcquiringBank;
import org.bank.processing_center.model.SalesPoint;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalesPointJDBCDaoImpl implements Dao<SalesPoint, Long> {

    private SalesPointMapper salesPointMapper = new SalesPointMapper();

    @Override
    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS sales_point (
                id BIGINT PRIMARY KEY,
                pos_name VARCHAR(255),
                pos_address VARCHAR(255),
                pos_inn VARCHAR(12),
                acquiring_bank_id BIGINT,
                FOREIGN KEY (acquiring_bank_id) REFERENCES acquiring_bank(id)
                )"""; // Added acquiring_bank_id column definition explicitly
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица SalesPoint создана (или уже существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы SalesPoint: " + e.getMessage());
            e.printStackTrace(); // For better debugging
        }
    }

    @Override
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS sales_point CASCADE";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица SalesPoint удалена (если существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы SalesPoint: " + e.getMessage());
        }
    }

    @Override
    public void clearTable() {
        String sql = "DELETE FROM sales_point";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица SalesPoint очищена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы SalesPoint: " + e.getMessage());
        }
    }

    @Override
    public SalesPoint save(SalesPoint salesPoint) {
        String sql = "INSERT INTO sales_point (id, pos_name, pos_address, pos_inn, acquiring_bank_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, salesPoint.getId());
            preparedStatement.setString(2, salesPoint.getPosName());
            preparedStatement.setString(3, salesPoint.getPosAddress());
            preparedStatement.setString(4, salesPoint.getPosInn());
            if (salesPoint.getAcquiringBank() != null) {
                // Assuming AcquiringBank model has getId() or similar for its primary key
                preparedStatement.setLong(5, salesPoint.getAcquiringBank().getId());
            } else {
                preparedStatement.setNull(5, Types.BIGINT);
            }
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("SalesPoint добавлен: " + salesPoint);
                return salesPoint;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении SalesPoint: " + e.getMessage());
            e.printStackTrace(); // For better debugging
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM sales_point WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("SalesPoint с id " + id + " удален.");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении SalesPoint: " + e.getMessage());
        }
    }

    @Override
    public List<SalesPoint> findAll() {
        List<SalesPoint> salesPoints = new ArrayList<>();
        String sql = "SELECT id, pos_name, pos_address, pos_inn, acquiring_bank_id FROM sales_point";
        try (Connection connection = JDBCConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                SalesPoint salesPoint = salesPointMapper.mapResultSetToSalesPoint(resultSet);
                // The mapper should handle populating the AcquiringBank object if needed,
                // or at least its ID. For simplicity, if mapper doesn't fully populate,
                // you might need to adjust here or in the mapper.
                // Example if mapper only sets ID:
                Long acquiringBankId = resultSet.getLong("acquiring_bank_id");
                if (!resultSet.wasNull() && salesPoint.getAcquiringBank() == null) {
                    AcquiringBank ab = new AcquiringBank();
                    ab.setId(acquiringBankId);
                    salesPoint.setAcquiringBank(ab);
                }
                salesPoints.add(salesPoint);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех SalesPoint: " + e.getMessage());
            e.printStackTrace(); // For better debugging
        }
        return salesPoints;
    }

    @Override
    public SalesPoint findById(Long id) {
        String sql = "SELECT id, pos_name, pos_address, pos_inn, acquiring_bank_id FROM sales_point WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                SalesPoint salesPoint = salesPointMapper.mapResultSetToSalesPoint(resultSet);
                // Similar to findAll, ensure AcquiringBank is handled by mapper or here
                Long acquiringBankId = resultSet.getLong("acquiring_bank_id");
                if (!resultSet.wasNull() && salesPoint.getAcquiringBank() == null) {
                    AcquiringBank ab = new AcquiringBank();
                    ab.setId(acquiringBankId);
                    salesPoint.setAcquiringBank(ab);
                }
                return salesPoint;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении SalesPoint по id: " + e.getMessage());
            e.printStackTrace(); // For better debugging
        }
        return null;
    }

    @Override
    public SalesPoint update(SalesPoint salesPoint) {
        String sql = "UPDATE sales_point SET pos_name = ?, pos_address = ?, pos_inn = ?, acquiring_bank_id = ? WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, salesPoint.getPosName());
            preparedStatement.setString(2, salesPoint.getPosAddress());
            preparedStatement.setString(3, salesPoint.getPosInn());
            if (salesPoint.getAcquiringBank() != null) {
                preparedStatement.setLong(4, salesPoint.getId());
            } else {
                preparedStatement.setNull(4, Types.BIGINT);
            }
            preparedStatement.setLong(5, salesPoint.getId()); // Corrected parameter index for id
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("SalesPoint обновлен: " + salesPoint);
                return salesPoint;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении SalesPoint: " + e.getMessage());
            e.printStackTrace(); // For better debugging
        }
        return null;
    }
}