package org.bank.processing_center.mapper;

import org.bank.processing_center.dao.jdbc.AccountJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.CardStatusJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.PaymentSystemJDBCDaoImpl;
import org.bank.processing_center.model.Account;
import org.bank.processing_center.model.Card;
import org.bank.processing_center.model.CardStatus;
import org.bank.processing_center.model.PaymentSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

public class CardMapper {

    private CardStatusJDBCDaoImpl cardStatusDao = new CardStatusJDBCDaoImpl(); // Placeholder DAO
    private PaymentSystemJDBCDaoImpl paymentSystemDao = new PaymentSystemJDBCDaoImpl(); // Placeholder DAO
    private AccountJDBCDaoImpl accountDao = new AccountJDBCDaoImpl(); // Placeholder DAO

    public Card mapResultSetToCard(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String cardNumber = resultSet.getString("card_number");
        LocalDate expirationDate = resultSet.getDate("expiration_date").toLocalDate();
        String holderName = resultSet.getString("holder_name");
        Long cardStatusId = resultSet.getLong("card_status_id");
        Long paymentSystemId = resultSet.getLong("payment_system_id");
        Long accountId = resultSet.getLong("account_id");
        Timestamp receivedFromIssuingBank = resultSet.getTimestamp("received_from_issuing_bank");
        Timestamp sentToIssuingBank = resultSet.getTimestamp("sent_to_issuing_bank");

        // Fetch related objects using placeholder DAOs
        // Assuming findById methods in DAOs return the object or null if not found
        CardStatus cardStatus = null;
        if (!resultSet.wasNull() && cardStatusId != 0) {
            cardStatus = cardStatusDao.findById(cardStatusId);
        }

        PaymentSystem paymentSystem = null;
        if (!resultSet.wasNull() && paymentSystemId != 0) {
            paymentSystem = paymentSystemDao.findById(paymentSystemId);
        }

        Account account = null;
        if (!resultSet.wasNull() && accountId != 0) {
            account = accountDao.findById(accountId);
        }


        // Construct and return Card object
        return new Card(
                id,
                cardNumber,
                expirationDate,
                holderName,
                cardStatus,
                paymentSystem,
                account,
                receivedFromIssuingBank,
                sentToIssuingBank
        );
    }
}