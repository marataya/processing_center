package org.bank.processing_center.dao.jdbc;

import org.bank.processing_center.configuration.JDBCConfig;
import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.mapper.CardMapper;
import org.bank.processing_center.model.Card;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// JDBC implementation for Card entity DAO
public class CardJDBCDaoImpl implements Dao<Card, Long> {

    @Override
    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS card (
                id BIGINT PRIMARY KEY,
                card_number VARCHAR(50),
                expiration_date DATE,
                holder_name VARCHAR(50),
                card_status_id BIGINT,
                payment_system_id BIGINT,
                account_id BIGINT,
                received_from_issuing_bank TIMESTAMP,
                sent_to_issuing_bank TIMESTAMP,
                FOREIGN KEY (card_status_id) REFERENCES card_status (id),
                FOREIGN KEY (payment_system_id) REFERENCES payment_system (id),
                FOREIGN KEY (account_id) REFERENCES account (id)
                )""";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица Card создана (или уже существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы Card: " + e.getMessage());
        }
    }

    @Override
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS card CASCADE";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица Card удалена (если существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы Card: " + e.getMessage());
        }
    }

    @Override
    public void clearTable() {
        String sql = "DELETE FROM card";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица Card очищена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы Card: " + e.getMessage());
        }
    }

    private CardMapper cardMapper = new CardMapper();

    @Override
    public void save(Card card) {
        String sql = "INSERT INTO card (id, card_number, expiration_date, holder_name, card_status_id, payment_system_id, account_id, received_from_issuing_bank, sent_to_issuing_bank) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, card.getId());
            preparedStatement.setString(2, card.getCardNumber());
            preparedStatement.setDate(3, Date.valueOf(card.getExpirationDate()));
            preparedStatement.setString(4, card.getHolderName());

            if (card.getCardStatus() != null) {
                preparedStatement.setLong(5, card.getCardStatus().getId());
            } else {
                preparedStatement.setNull(5, Types.BIGINT);
            }

            if (card.getPaymentSystem() != null) {
                preparedStatement.setLong(6, card.getPaymentSystem().getId());
            } else {
                preparedStatement.setNull(6, Types.BIGINT);
            }

            if (card.getAccount() != null) {
                preparedStatement.setLong(7, card.getAccount().getId());
            } else {
                preparedStatement.setNull(7, Types.BIGINT);
            }

            if (card.getReceivedFromIssuingBank() != null) {
                preparedStatement.setTimestamp(8, card.getReceivedFromIssuingBank());
            } else {
                preparedStatement.setNull(8, Types.TIMESTAMP);
            }

            if (card.getSentToIssuingBank() != null) {
                preparedStatement.setTimestamp(9, card.getSentToIssuingBank());
            } else {
                preparedStatement.setNull(9, Types.TIMESTAMP); // Corrected type
            }

            preparedStatement.executeUpdate();
            System.out.println("Card добавлена: " + card);
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении Card: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM card WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Card с id " + id + " удалена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении Card: " + e.getMessage());
        }
    }

    @Override
    public List<Card> findAll() {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT id, card_number, expiration_date, holder_name, card_status_id, payment_system_id, account_id, received_from_issuing_bank, sent_to_issuing_bank FROM card";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                cards.add(cardMapper.mapResultSetToCard(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех Card: " + e.getMessage());
        }
        return cards;
    }

    @Override
    public Card findById(Long id) {
        String sql = "SELECT id, card_number, expiration_date, holder_name, card_status_id, payment_system_id, account_id, received_from_issuing_bank, sent_to_issuing_bank FROM card WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return cardMapper.mapResultSetToCard(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении Card по id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Card card) {
        String sql = "UPDATE card SET card_number = ?, expiration_date = ?, holder_name = ?, card_status_id = ?, payment_system_id = ?, account_id = ?, received_from_issuing_bank = ?, sent_to_issuing_bank = ? WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, card.getCardNumber());
            preparedStatement.setDate(2, Date.valueOf(card.getExpirationDate()));
            preparedStatement.setString(3, card.getHolderName());

            if (card.getCardStatus() != null) {
                preparedStatement.setLong(4, card.getCardStatus().getId());
            } else {
                preparedStatement.setNull(4, Types.BIGINT);
            }

            if (card.getPaymentSystem() != null) {
                preparedStatement.setLong(5, card.getPaymentSystem().getId());
            } else {
                preparedStatement.setNull(5, Types.BIGINT);
            }

            if (card.getAccount() != null) {
                preparedStatement.setLong(6, card.getAccount().getId());
            } else {
                preparedStatement.setNull(6, Types.BIGINT);
            }

            if (card.getReceivedFromIssuingBank() != null) {
                preparedStatement.setTimestamp(7, card.getReceivedFromIssuingBank());
            } else {
                preparedStatement.setNull(7, Types.TIMESTAMP);
            }

            if (card.getSentToIssuingBank() != null) {
                preparedStatement.setTimestamp(8, card.getSentToIssuingBank());
            } else {
                preparedStatement.setNull(8, Types.TIMESTAMP); // Corrected type
            }

            preparedStatement.setLong(9, card.getId());
            preparedStatement.executeUpdate();
            System.out.println("Card обновлена: " + card);
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении Card: " + e.getMessage());
        }
    }
}