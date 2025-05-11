package org.bank.processing_center.dao.factory;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.dao.hibernate.AccountHibernateDaoImpl;
import org.bank.processing_center.dao.hibernate.CardHibernateDaoImpl;
import org.bank.processing_center.dao.hibernate.CardStatusHibernateDaoImpl;
import org.bank.processing_center.dao.hibernate.PaymentSystemHibernateDaoImpl;
import org.bank.processing_center.dao.jdbc.AccountJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.CardJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.CardStatusJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.PaymentSystemJDBCDaoImpl;
import org.bank.processing_center.model.Account;
import org.bank.processing_center.model.Card;
import org.bank.processing_center.model.Currency;
import org.bank.processing_center.model.CardStatus;
import org.bank.processing_center.model.PaymentSystem;

/**
 * Factory for creating DAO instances
 */
public class DaoFactory {

 private static DaoFactory jdbcInstance;
 private static DaoFactory hibernateInstance;

    private final Dao<Card, Long> cardDao;
    private final Dao<CardStatus, Long> cardStatusDao;
    private final Dao<PaymentSystem, Long> paymentSystemDao;
    private final Dao<Account, Long> accountDao;

    private DaoFactory(String daoType) {
 if ("jdbc".equalsIgnoreCase(daoType)) {
            this.cardDao = new CardJDBCDaoImpl();
            this.cardStatusDao = new CardStatusJDBCDaoImpl();
            this.paymentSystemDao = new PaymentSystemJDBCDaoImpl();
            this.accountDao = new AccountJDBCDaoImpl();
        } else if ("hibernate".equalsIgnoreCase(daoType)) {
            this.cardDao = new CardHibernateDaoImpl();
            this.cardStatusDao = new CardStatusHibernateDaoImpl();
            this.paymentSystemDao = new PaymentSystemHibernateDaoImpl();
            this.accountDao = new AccountHibernateDaoImpl();
        } else {
 throw new IllegalArgumentException("Invalid DAO type specified: " + daoType);
        }
    }


    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
        }
        return instance;
    }

    public static synchronized DaoFactory getInstance(String daoType) {
        if ("jdbc".equalsIgnoreCase(daoType)) {
            if (jdbcInstance == null) {
                jdbcInstance = new DaoFactory("jdbc");
            }
            return jdbcInstance;
        } else if ("hibernate".equalsIgnoreCase(daoType)) {
            if (hibernateInstance == null) {
                hibernateInstance = new DaoFactory("hibernate");
            }
            return hibernateInstance;
        } else {
            throw new IllegalArgumentException("Invalid DAO type specified: " + daoType);
        }
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
