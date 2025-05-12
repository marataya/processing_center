package org.bank.processing_center.dao.jdbc;

import org.bank.processing_center.configuration.JDBCConfig;
import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.mapper.TransactionMapper;
import org.bank.processing_center.model.Transaction;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionJDBCDaoImpl implements Dao<Transaction, Long> {

    private TransactionMapper transactionMapper = new TransactionMapper();

    @Override
    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS transaction
                (
                    id                         BIGINT PRIMARY KEY,
                    transaction_date           DATE,
                    sum                        DOUBLE PRECISION,
                    transaction_name           VARCHAR(255),
                    account_id                 BIGINT,
                    transaction_type_id        BIGINT,
                    card_id                    BIGINT,
                    terminal_id                BIGINT,
                    response_code_id           BIGINT,
                    authorization_code         VARCHAR(6),
                    received_from_issuing_bank TIMESTAMP,
                    sent_to_issuing_bank       TIMESTAMP,
                    FOREIGN KEY (account_id) REFERENCES account (id),
                    FOREIGN KEY (transaction_type_id) REFERENCES transaction_type (id),
                    FOREIGN KEY (card_id) REFERENCES card (id),
                    FOREIGN KEY (terminal_id) REFERENCES terminal (id),
                    FOREIGN KEY (response_code_id) REFERENCES response_code (id)
                )""";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица Transaction создана (или уже существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы Transaction: " + e.getMessage());
        }
    }

    @Override
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS transaction CASCADE";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица Transaction удалена (если существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы Transaction: " + e.getMessage());
        }
    }

    @Override
    public void clearTable() {
        String sql = "DELETE FROM transaction";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица Transaction очищена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы Transaction: " + e.getMessage());
        }
    }

    @Override
    public void save(Transaction transaction) {
        String sql = "INSERT INTO transaction (id, transaction_date, sum, transaction_name, account_id, transaction_type_id, card_id, terminal_id, response_code_id, authorization_code, received_from_issuing_bank, sent_to_issuing_bank) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, transaction.getId());

            // Handle LocalDate transactionDate
            if (transaction.getTransactionDate() != null) {
                preparedStatement.setDate(2, Date.valueOf(transaction.getTransactionDate()));
            } else {
                preparedStatement.setNull(2, Types.DATE);
            }

            // Handle BigDecimal sum (convert to Double)
            if (transaction.getSum() != null) {
                preparedStatement.setDouble(3, transaction.getSum().doubleValue());
            } else {
                preparedStatement.setNull(3, Types.DOUBLE);
            }

            preparedStatement.setString(4, transaction.getTransactionName());

            // Handle foreign keys with null checks
            if (transaction.getAccount() != null) {
                preparedStatement.setLong(5, transaction.getAccount().getId());
            } else {
                preparedStatement.setNull(5, Types.BIGINT);
            }

            if (transaction.getTransactionType() != null) {
                preparedStatement.setLong(6, transaction.getTransactionType().getId());
            } else {
                preparedStatement.setNull(6, Types.BIGINT);
            }

            if (transaction.getCard() != null) {
                preparedStatement.setLong(7, transaction.getCard().getId());
            } else {
                preparedStatement.setNull(7, Types.BIGINT);
            }

            if (transaction.getTerminal() != null) {
                preparedStatement.setLong(8, transaction.getTerminal().getId());
            } else {
                preparedStatement.setNull(8, Types.BIGINT);
            }

            if (transaction.getResponseCode() != null) {
                preparedStatement.setLong(9, transaction.getResponseCode().getId());
            } else {
                preparedStatement.setNull(9, Types.BIGINT);
            }

            preparedStatement.setString(10, transaction.getAuthorizationCode());

            // Handle LocalDateTime receivedFromIssuingBank (convert to Timestamp)
            if (transaction.getReceivedFromIssuingBank() != null) {
                preparedStatement.setTimestamp(11, Timestamp.valueOf(transaction.getReceivedFromIssuingBank()));
            } else {
                preparedStatement.setNull(11, Types.TIMESTAMP);
            }

            // Handle LocalDateTime sentToIssuingBank (convert to Timestamp)
            if (transaction.getSentToIssuingBank() != null) {
                preparedStatement.setTimestamp(12, Timestamp.valueOf(transaction.getSentToIssuingBank()));
            } else {
                preparedStatement.setNull(12, Types.TIMESTAMP);
            }

            preparedStatement.executeUpdate();
            System.out.println("Transaction добавлен: " + transaction);
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении Transaction: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM transaction WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Transaction с id " + id + " удален.");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении Transaction: " + e.getMessage());
        }
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT id, transaction_date, sum, transaction_name, account_id, transaction_type_id, card_id, terminal_id, response_code_id, authorization_code, received_from_issuing_bank, sent_to_issuing_bank FROM transaction";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                transactions.add(transactionMapper.mapResultSetToTransaction(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех Transaction: " + e.getMessage());
        }
        return transactions;
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        String sql = "SELECT id, transaction_date, sum, transaction_name, account_id, transaction_type_id, card_id, terminal_id, response_code_id, authorization_code, received_from_issuing_bank, sent_to_issuing_bank FROM transaction WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(transactionMapper.mapResultSetToTransaction(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении Transaction по id: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void update(Transaction transaction) {
        String sql = "UPDATE transaction SET transaction_date = ?, sum = ?, transaction_name = ?, account_id = ?, transaction_type_id = ?, card_id = ?, terminal_id = ?, response_code_id = ?, authorization_code = ?, received_from_issuing_bank = ?, sent_to_issuing_bank = ? WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Handle LocalDate transactionDate
            if (transaction.getTransactionDate() != null) {
                preparedStatement.setDate(1, Date.valueOf(transaction.getTransactionDate()));
            } else {
                preparedStatement.setNull(1, Types.DATE);
            }

            // Handle BigDecimal sum (convert to Double)
            if (transaction.getSum() != null) {
                preparedStatement.setDouble(2, transaction.getSum().doubleValue());
            } else {
                preparedStatement.setNull(2, Types.DOUBLE);
            }

            preparedStatement.setString(3, transaction.getTransactionName());

            // Handle foreign keys with null checks
            if (transaction.getAccount() != null) {
                preparedStatement.setLong(4, transaction.getAccount().getId());
            } else {
                preparedStatement.setNull(4, Types.BIGINT);
            }

            if (transaction.getTransactionType() != null) {
                preparedStatement.setLong(5, transaction.getTransactionType().getId());
            } else {
                preparedStatement.setNull(5, Types.BIGINT);
            }

            if (transaction.getCard() != null) {
                preparedStatement.setLong(6, transaction.getCard().getId());
            } else {
                preparedStatement.setNull(6, Types.BIGINT);
            }

            if (transaction.getTerminal() != null) {
                preparedStatement.setLong(7, transaction.getTerminal().getId());
            } else {
                preparedStatement.setNull(7, Types.BIGINT);
            }

            if (transaction.getResponseCode() != null) {
                preparedStatement.setLong(8, transaction.getResponseCode().getId());
            } else {
                preparedStatement.setNull(8, Types.BIGINT);
            }

            preparedStatement.setString(9, transaction.getAuthorizationCode());

            // Handle LocalDateTime receivedFromIssuingBank (convert to Timestamp)
            if (transaction.getReceivedFromIssuingBank() != null) {
                preparedStatement.setTimestamp(10, Timestamp.valueOf(transaction.getReceivedFromIssuingBank()));
            } else {
                preparedStatement.setNull(10, Types.TIMESTAMP);
            }

            // Handle LocalDateTime sentToIssuingBank (convert to Timestamp)
            if (transaction.getSentToIssuingBank() != null) {
                preparedStatement.setTimestamp(11, Timestamp.valueOf(transaction.getSentToIssuingBank()));
            } else {
                preparedStatement.setNull(11, Types.TIMESTAMP);
            }

            preparedStatement.setLong(12, transaction.getId());
            preparedStatement.executeUpdate();
            System.out.println("Transaction обновлен: " + transaction);
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении Transaction: " + e.getMessage());
        }
    }
}