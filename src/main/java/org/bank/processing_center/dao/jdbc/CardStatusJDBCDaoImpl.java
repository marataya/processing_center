package org.bank.processing_center.dao.jdbc;

import org.bank.processing_center.configuration.JDBCConfig;
import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.CardStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardStatusJDBCDaoImpl implements Dao<CardStatus, Long> {

    @Override
    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS card_status (\
                id BIGINT PRIMARY KEY,\
                card_status_name VARCHAR(255)\
                )""";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица CardStatus создана (или уже существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы CardStatus: " + e.getMessage());
        }
    }

    @Override
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS card_status CASCADE";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица CardStatus удалена (если существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы CardStatus: " + e.getMessage());
        }
    }

    @Override
    public void clearTable() {
        String sql = "DELETE FROM card_status";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица CardStatus очищена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы CardStatus: " + e.getMessage());
        }
    }

    @Override
    public void save(CardStatus cardStatus) {
        String sql = "INSERT INTO card_status (id, card_status_name) VALUES (?, ?)";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, cardStatus.getId());
            preparedStatement.setString(2, cardStatus.getCardStatusName());
            preparedStatement.executeUpdate();
            System.out.println("CardStatus добавлен: " + cardStatus);
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении CardStatus: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM card_status WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("CardStatus с id " + id + " удален.");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении CardStatus: " + e.getMessage());
        }
    }

    @Override
    public List<CardStatus> findAll() {
        List<CardStatus> cardStatuses = new ArrayList<>();
        String sql = "SELECT id, card_status_name FROM card_status";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                CardStatus cardStatus = new CardStatus(
                        resultSet.getLong("id"),
                        resultSet.getString("card_status_name")
                );
                cardStatuses.add(cardStatus);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех CardStatus: " + e.getMessage());
        }
        return cardStatuses;
    }

    @Override
    public Optional<CardStatus> findById(Long id) {
        String sql = "SELECT id, card_status_name FROM card_status WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                CardStatus cardStatus = new CardStatus(
                        resultSet.getLong("id"),
                        resultSet.getString("card_status_name")
                );
                return Optional.of(cardStatus);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении CardStatus по id: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void update(CardStatus cardStatus) {
        String sql = "UPDATE card_status SET card_status_name = ? WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cardStatus.getCardStatusName());
            preparedStatement.setLong(2, cardStatus.getId());
            preparedStatement.executeUpdate();
            System.out.println("CardStatus обновлен: " + cardStatus);
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении CardStatus: " + e.getMessage());
        }
    }
}
