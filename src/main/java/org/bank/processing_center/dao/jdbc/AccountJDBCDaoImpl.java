package org.bank.processing_center.dao.jdbc;

import org.bank.processing_center.configuration.JDBCConfig;
import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.mapper.AccountMapper;
import org.bank.processing_center.model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AccountJDBCDaoImpl implements Dao<Account, Long> {

    @Override
    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS account (\
                id BIGINT PRIMARY KEY,\
                account_number VARCHAR(50),\
                balance DOUBLE PRECISION,\
                currency_id BIGINT,\
                issuing_bank_id BIGINT,\
                FOREIGN KEY (currency_id) REFERENCES currency(id),\
                FOREIGN KEY (issuing_bank_id) REFERENCES issuing_bank(id)\
                )""";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица Account создана (или уже существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы Account: " + e.getMessage());
        }
    }

 private AccountMapper accountMapper = new AccountMapper();

    @Override
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS account CASCADE";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица Account удалена (если существовала).");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы Account: " + e.getMessage());
        }
    }

    @Override
    public void clearTable() {
        String sql = "DELETE FROM account";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица Account очищена.");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы Account: " + e.getMessage());
        }
    }

    @Override
    public void save(Account account) {
        String sql = "INSERT INTO account (id, account_number, balance, currency_id, issuing_bank_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, account.getId());
            preparedStatement.setString(2, account.getAccountNumber());
            preparedStatement.setBigDecimal(3, account.getBalance());
            preparedStatement.setLong(4, account.getCurrency().getId());            preparedStatement.setLong(5, account.getIssuingBank().getId());
            preparedStatement.executeUpdate();
            System.out.println("Account добавлен: " + account);
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении Account: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM account WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Account с id " + id + " удален.");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении Account: " + e.getMessage());
        }
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT id, account_number, balance, currency_id, issuing_bank_id FROM account";
        try (Connection connection = JDBCConfig.getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Account account = accountMapper.mapResultSetToAccount(resultSet);
                accounts.add(account);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех Account: " + e.getMessage());
        }
        return accounts;
    }

    @Override
    public Optional<Account> findById(Long id) {
        String sql = "SELECT id, account_number, balance, currency_id, issuing_bank_id FROM account WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Account account = accountMapper.mapResultSetToAccount(resultSet);
                return Optional.of(account);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении Account по id: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void update(Account account) {
        String sql = "UPDATE account SET account_number = ?, balance = ?, currency_id = ?, issuing_bank_id = ? WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, account.getAccountNumber());
            preparedStatement.setBigDecimal(2, account.getBalance());
            preparedStatement.setLong(3, account.getCurrency().getId());            preparedStatement.setLong(4, account.getIssuingBank().getId());
            preparedStatement.setLong(5, account.getId());
            preparedStatement.executeUpdate();
            System.out.println("Account обновлен: " + account);
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении Account: " + e.getMessage());
        }
    }

    // Дополнительные методы для работы со счетами

    public void updateBalance(Long id, Double newBalance) {
        String sql = "UPDATE account SET balance = ? WHERE id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
            System.out.println("Баланс счета с id " + id + " обновлен на " + newBalance);
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении баланса счета: " + e.getMessage());
        }
    }

    public Optional<Account> findByAccountNumber(String accountNumber) {
        String sql = "SELECT id, account_number, balance, currency_id, issuing_bank_id FROM account WHERE account_number = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Account account = accountMapper.mapResultSetToAccount(resultSet);
                return Optional.of(account);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении Account по номеру счета: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Account> findByIssuingBankId(Long issuingBankId) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT id, account_number, balance, currency_id, issuing_bank_id FROM account WHERE issuing_bank_id = ?";
        try (Connection connection = JDBCConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, issuingBankId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = accountMapper.mapResultSetToAccount(resultSet);
                accounts.add(account);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении Account по id банка-эмитента: " + e.getMessage());
        }
        return accounts;
    }
}
