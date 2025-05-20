package org.bank.processing_center.dao.jdbc;

import org.bank.processing_center.configuration.JDBCConfig;
import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.MerchantCategoryCode;
import org.bank.processing_center.model.SalesPoint;
import org.bank.processing_center.model.Terminal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TerminalJDBCDaoImpl implements Dao<Terminal, Long> {

    @Override
    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS terminal (
                    id BIGINT PRIMARY KEY,
                    terminal_id VARCHAR(9) UNIQUE NOT NULL,
                    mcc_id BIGINT NOT NULL,      -- Define the column for the foreign key
                    pos_id BIGINT NOT NULL,      -- Define the column for the foreign key
                    FOREIGN KEY (mcc_id) REFERENCES merchant_category_code(id),
                    FOREIGN KEY (pos_id) REFERENCES sales_point(id)
                )""";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица Terminal создана (или уже существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы Terminal: " + e.getMessage());
            e.printStackTrace();
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
    public Terminal save(Terminal terminal) {
        String sql = "INSERT INTO terminal (id, terminal_id, mcc_id, pos_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, terminal.getId());
            preparedStatement.setString(2, terminal.getTerminalId());

            if (terminal.getMcc() != null) {
                preparedStatement.setLong(3, terminal.getMcc().getId());
            } else {
                preparedStatement.setNull(3, Types.BIGINT);
            }

            if (terminal.getPos() != null) {
                preparedStatement.setLong(4, terminal.getPos().getId());
            } else {
                preparedStatement.setNull(4, Types.BIGINT);
            }
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Terminal добавлен: " + terminal);
                return terminal;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении Terminal: " + e.getMessage());
            e.printStackTrace(); // For better debugging
        }
        return null;
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
        // Corrected SQL to include mcc_id and use pos_id
        String sql = "SELECT id, terminal_id, mcc_id, pos_id FROM terminal";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Terminal terminal = new Terminal();
                terminal.setId(resultSet.getLong("id"));
                terminal.setTerminalId(resultSet.getString("terminal_id"));

                Long mccId = resultSet.getLong("mcc_id");
                if (!resultSet.wasNull()) {
                    MerchantCategoryCode mcc = new MerchantCategoryCode();
                    mcc.setId(mccId);
                    // Note: Full MCC details are not fetched here, only ID for relationship
                    terminal.setMcc(mcc);
                }

                Long salesPointId = resultSet.getLong("pos_id"); // Use pos_id
                if (!resultSet.wasNull()) {
                    SalesPoint salesPoint = new SalesPoint();
                    salesPoint.setId(salesPointId);
                    // Note: Full SalesPoint details are not fetched here, only ID for relationship
                    terminal.setPos(salesPoint);
                }
                terminals.add(terminal);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех Terminal: " + e.getMessage());
            e.printStackTrace(); // For better debugging
        }
        return terminals;
    }


    @Override
    public Terminal findById(Long id) {
        // Corrected SQL to include mcc_id and use pos_id
        String sql = "SELECT id, terminal_id, mcc_id, pos_id FROM terminal WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Terminal terminal = new Terminal();
                terminal.setId(resultSet.getLong("id"));
                terminal.setTerminalId(resultSet.getString("terminal_id"));

                Long mccId = resultSet.getLong("mcc_id");
                if (!resultSet.wasNull()) {
                    MerchantCategoryCode mcc = new MerchantCategoryCode();
                    mcc.setId(mccId);
                    terminal.setMcc(mcc);
                }

                Long salesPointId = resultSet.getLong("pos_id"); // Use pos_id
                if (!resultSet.wasNull()) {
                    SalesPoint salesPoint = new SalesPoint();
                    salesPoint.setId(salesPointId);
                    terminal.setPos(salesPoint);
                }
                return terminal;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении Terminal по id: " + e.getMessage());
            e.printStackTrace(); // For better debugging
        }
        return null;
    }

    @Override
    public Terminal update(Terminal terminal) {
        // Corrected SQL to include mcc_id and use pos_id
        String sql = "UPDATE terminal SET terminal_id = ?, mcc_id = ?, pos_id = ? WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, terminal.getTerminalId());

            if (terminal.getMcc() != null) {
                preparedStatement.setLong(2, terminal.getMcc().getId());
            } else {
                preparedStatement.setNull(2, Types.BIGINT);
            }

            if (terminal.getPos() != null) {
                preparedStatement.setLong(3, terminal.getPos().getId());
            } else {
                preparedStatement.setNull(3, Types.BIGINT);
            }
            preparedStatement.setLong(4, terminal.getId()); // WHERE id = ?
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Terminal обновлен: " + terminal);
                return terminal;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении Terminal: " + e.getMessage());
            e.printStackTrace(); // For better debugging
        }
        return null;
    }
}