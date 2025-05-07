package org.bank.processing_center.dao.factory;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.dao.jdbc.AccountJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.CardJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.CardStatusJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.PaymentSystemJDBCDaoImpl;
import org.bank.processing_center.model.Account;
import org.bank.processing_center.model.Card;
import org.bank.processing_center.model.CardStatus;
import org.bank.processing_center.model.PaymentSystem;

/**
 * Factory for creating DAO instances
 */
public class DaoFactory {

    private static DaoFactory instance;

    private final Dao<Card, Long> cardDao;
    private final Dao<CardStatus, Long> cardStatusDao;
    private final Dao<PaymentSystem, Long> paymentSystemDao;
    private final Dao<Account, Long> accountDao;

    private DaoFactory() {
        // Create DAO JDBC instances
        this.cardDao = new CardJDBCDaoImpl();
        this.cardStatusDao = new CardStatusJDBCDaoImpl();
        this.paymentSystemDao = new PaymentSystemJDBCDaoImpl();
        this.accountDao = new AccountJDBCDaoImpl();
    }

    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
        }
        return instance;
    }

    public Dao<Card, Long> getCardDao() {
        return cardDao;
    }

    public Dao<CardStatus, Long> getCardStatusDao() {
        return cardStatusDao;
    }

    public Dao<PaymentSystem, Long> getPaymentSystemDao() {
        return paymentSystemDao;
    }

    public Dao<Account, Long> getAccountDao() {
        return accountDao;
    }
}
