package org.bank.processing_center.dao.jdbc;

import org.bank.processing_center.configuration.JDBCConfig;
import org.bank.processing_center.dao.base.Dao;
import org.bank.processing_center.model.Card;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Пример реализации Dao для сущности Card
public class CardJDBCDaoImpl implements Dao<Card, Long> {

    private final Connection connection;

    // Конструктор по умолчанию
    public CardJDBCDaoImpl() {
        this.connection = JDBCConfig.getConnection();
    }

    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS card (" +
                "id BIGINT PRIMARY KEY," +
                "card_number VARCHAR(30)," +
                "expiration_date DATE," +
                "holder_name VARCHAR(50)," +
                "card_status_id BIGINT," +
                "payment_system_id BIGINT," +
                "account_id BIGINT," +
                "received_from_issuing_bank TIMESTAMP," +
                "sent_to_issuing_bank TIMESTAMP," +
                "FOREIGN KEY (card_status_id) REFERENCES card_status(id)," +
                "FOREIGN KEY (payment_system_id) REFERENCES payment_system(id)," +
                "FOREIGN KEY (account_id) REFERENCES account(id)" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица Card создана (или уже существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы Card: " + e.getMessage());
        }
    }

    @Override
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS card";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица Card удалена (если существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы Card: " + e.getMessage());
        }
    }

    @Override
    public void clearTable() {
        String sql = "DELETE FROM card";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица Card очищена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы Card: " + e.getMessage());
        }
    }

    @Override
    public void save(Card card) {
        String sql = "INSERT INTO card (id, card_number, expiration_date, holder_name, card_status_id, payment_system_id, account_id, received_from_issuing_bank, sent_to_issuing_bank) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, card.getId());
            preparedStatement.setString(2, card.getCardNumber());
            preparedStatement.setDate(3, Date.valueOf(card.getExpirationDate()));
            preparedStatement.setString(4, card.getHolderName());
            preparedStatement.setLong(5, card.getCardStatusId());
            preparedStatement.setLong(6, card.getPaymentSystemId());
            preparedStatement.setLong(7, card.getAccountId());
            preparedStatement.setTimestamp(8, card.getReceivedFromIssuingBank());
            preparedStatement.setTimestamp(9, card.getSentToIssuingBank());
            preparedStatement.executeUpdate();
            System.out.println("Card добавлена: " + card);
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении Card: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM card WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Card card = new Card(
                        resultSet.getLong("id"),
                        resultSet.getString("card_number"),
                        resultSet.getDate("expiration_date").toLocalDate(),
                        resultSet.getString("holder_name"),
                        resultSet.getLong("card_status_id"),
                        resultSet.getLong("payment_system_id"),
                        resultSet.getLong("account_id"),
                        resultSet.getTimestamp("received_from_issuing_bank"),
                        resultSet.getTimestamp("sent_to_issuing_bank")
                );
                cards.add(card);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех Card: " + e.getMessage());
        }
        return cards;
    }

    @Override
    public Optional<Card> findById(Long id) {
        String sql = "SELECT id, card_number, expiration_date, holder_name, card_status_id, payment_system_id, account_id, received_from_issuing_bank, sent_to_issuing_bank FROM card WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Card card = new Card(
                        resultSet.getLong("id"),
                        resultSet.getString("card_number"),
                        resultSet.getDate("expiration_date").toLocalDate(),
                        resultSet.getString("holder_name"),
                        resultSet.getLong("card_status_id"),
                        resultSet.getLong("payment_system_id"),
                        resultSet.getLong("account_id"),
                        resultSet.getTimestamp("received_from_issuing_bank"),
                        resultSet.getTimestamp("sent_to_issuing_bank")
                );
                return Optional.of(card);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении Card по id: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void update(Card card) {
        String sql = "UPDATE card SET card_number = ?, expiration_date = ?, holder_name = ?, card_status_id = ?, payment_system_id = ?, account_id = ?, received_from_issuing_bank = ?, sent_to_issuing_bank = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, card.getCardNumber());
            preparedStatement.setDate(2, Date.valueOf(card.getExpirationDate()));
            preparedStatement.setString(3, card.getHolderName());
            preparedStatement.setLong(4, card.getCardStatusId());
            preparedStatement.setLong(5, card.getPaymentSystemId());
            preparedStatement.setLong(6, card.getAccountId());
            preparedStatement.setTimestamp(7, card.getReceivedFromIssuingBank());
            preparedStatement.setTimestamp(8, card.getSentToIssuingBank());
            preparedStatement.setLong(9, card.getId());
            preparedStatement.executeUpdate();
            System.out.println("Card обновлена: " + card);
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении Card: " + e.getMessage());
        }
    }
}
