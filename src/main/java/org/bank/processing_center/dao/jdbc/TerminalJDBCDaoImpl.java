package org.bank.processing_center.dao.jdbc;

import org.bank.processing_center.configuration.JDBCConfig;
import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.Terminal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TerminalJDBCDaoImpl implements Dao<Terminal, Long> {

    @Override
    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS terminal (
                    id BIGINT PRIMARY KEY,
                    terminal_id VARCHAR(50),
                    sales_point_id BIGINT,
                    FOREIGN KEY (sales_point_id) REFERENCES sales_point(id)
                )""";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица Terminal создана (или уже существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы Terminal: " + e.getMessage());
        }
    }

    @Override
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS terminal CASCADE";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица Terminal удалена (если существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы Terminal: " + e.getMessage());
        }
    }

    @Override
    public void clearTable() {
        String sql = "DELETE FROM terminal";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица Terminal очищена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы Terminal: " + e.getMessage());
        }
    }

    @Override
    public void save(Terminal terminal) {
        String sql = "INSERT INTO terminal (id, terminal_id, sales_point_id) VALUES (?, ?, ?)";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, terminal.getId());
            preparedStatement.setString(2, terminal.getTerminalId());
            if (terminal.getPos() != null) {
                preparedStatement.setLong(3, terminal.getPos().getId());
            } else {
                preparedStatement.setNull(3, Types.BIGINT);
            }
            preparedStatement.executeUpdate();
            System.out.println("Terminal добавлен: " + terminal);
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении Terminal: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM terminal WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Terminal с id " + id + " удален.");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении Terminal: " + e.getMessage());
        }
    }

    @Override
    public List<Terminal> findAll() {
        List<Terminal> terminals = new ArrayList<>();
        String sql = "SELECT id, terminal_id, sales_point_id FROM terminal";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Terminal terminal = new Terminal();
                terminal.setId(resultSet.getLong("id"));
                terminal.setTerminalId(resultSet.getString("terminal_id"));

                Long salesPointId = resultSet.getLong("sales_point_id");
                if (!resultSet.wasNull()) {
                    // Create a dummy SalesPoint with just the ID
                    org.bank.processing_center.model.SalesPoint salesPoint = new org.bank.processing_center.model.SalesPoint();
                    salesPoint.setId(salesPointId);
                    terminal.setPos(salesPoint);
                }

                terminals.add(terminal);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех Terminal: " + e.getMessage());
        }
        return terminals;
    }

    @Override
    public Optional<Terminal> findById(Long id) {
        String sql = "SELECT id, terminal_id, sales_point_id FROM terminal WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Terminal terminal = new Terminal();
                terminal.setId(resultSet.getLong("id"));
                terminal.setTerminalId(resultSet.getString("terminal_id"));

                Long salesPointId = resultSet.getLong("sales_point_id");
                if (!resultSet.wasNull()) {
                    // Create a dummy SalesPoint with just the ID
                    org.bank.processing_center.model.SalesPoint salesPoint = new org.bank.processing_center.model.SalesPoint();
                    salesPoint.setId(salesPointId);
                    terminal.setPos(salesPoint);
                }

                return Optional.of(terminal);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении Terminal по id: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void update(Terminal terminal) {
        String sql = "UPDATE terminal SET terminal_id = ?, sales_point_id = ? WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, terminal.getTerminalId());
            if (terminal.getPos() != null) {
                preparedStatement.setLong(2, terminal.getPos().getId());
            } else {
                preparedStatement.setNull(2, Types.BIGINT);
            }
            preparedStatement.setLong(3, terminal.getId());
            preparedStatement.executeUpdate();
            System.out.println("Terminal обновлен: " + terminal);
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении Terminal: " + e.getMessage());
        }
    }
}