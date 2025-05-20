package org.bank.processing_center.mapper;

import org.bank.processing_center.dao.jdbc.*;
import org.bank.processing_center.model.Transaction;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransactionMapper {

    // Placeholder DAOs (replace with actual injected DAOs in a real application)
    private AccountJDBCDaoImpl accountDao = new AccountJDBCDaoImpl();
    private TransactionTypeJDBCDaoImpl transactionTypeDao = new TransactionTypeJDBCDaoImpl();
    private CardJDBCDaoImpl cardDao = new CardJDBCDaoImpl();
    private TerminalJDBCDaoImpl terminalDao = new TerminalJDBCDaoImpl();
    private ResponseCodeJDBCDaoImpl responseCodeDao = new ResponseCodeJDBCDaoImpl();

    public Transaction mapResultSetToTransaction(ResultSet resultSet) throws SQLException {
        Transaction transaction = new Transaction();

        transaction.setId(resultSet.getLong("id"));

        java.sql.Date transactionDateSql = resultSet.getDate("transaction_date");
        if (transactionDateSql != null) {
            transaction.setTransactionDate(transactionDateSql.toLocalDate());
        }

        transaction.setSum(BigDecimal.valueOf(resultSet.getDouble("sum")));
        transaction.setTransactionName(resultSet.getString("transaction_name"));

        // Fetch and set related Account
        Long accountId = resultSet.getLong("account_id");
        if (!resultSet.wasNull()) {
            accountDao.findById(accountId).ifPresent(transaction::setAccount);
        }

        // Fetch and set related TransactionType
        Long transactionTypeId = resultSet.getLong("transaction_type_id");
        if (!resultSet.wasNull()) {
            transactionTypeDao.findById(transactionTypeId).ifPresent(transaction::setTransactionType);
        }

        // Fetch and set related Card
        Long cardId = resultSet.getLong("card_id");
        if (!resultSet.wasNull()) {
            cardDao.findById(cardId).ifPresent(transaction::setCard);
        }

        // Fetch and set related Terminal
        Long terminalId = resultSet.getLong("terminal_id");
        if (!resultSet.wasNull()) {
            terminalDao.findById(terminalId).ifPresent(transaction::setTerminal);
        }

        // Fetch and set related ResponseCode
        Long responseCodeId = resultSet.getLong("response_code_id");
        if (!resultSet.wasNull()) {
            responseCodeDao.findById(responseCodeId).ifPresent(transaction::setResponseCode);
        }

        transaction.setAuthorizationCode(resultSet.getString("authorization_code"));

        // Convert TIMESTAMP to LocalDateTime
        Timestamp receivedFromIssuingBankTimestamp = resultSet.getTimestamp("received_from_issuing_bank");
        if (receivedFromIssuingBankTimestamp != null) {
            transaction.setReceivedFromIssuingBank(receivedFromIssuingBankTimestamp.toLocalDateTime());
        }

        Timestamp sentToIssuingBankTimestamp = resultSet.getTimestamp("sent_to_issuing_bank");
        if (sentToIssuingBankTimestamp != null) {
            transaction.setSentToIssuingBank(sentToIssuingBankTimestamp.toLocalDateTime());
        }

        return transaction;
    }
}