package org.bank.processing_center.dao.factory;

import org.bank.processing_center.dao.base.Dao;
import org.bank.processing_center.dao.jdbc.*;
import org.bank.processing_center.model.*;

public class DaoFactory {

    private static DaoFactory instance;

    private final Dao<Card, Long> cardDao;
    private final Dao<CardStatus, Long> cardStatusDao;
    private final Dao<PaymentSystem, Long> paymentSystemDao;
    private final Dao<Account, Long> accountDao;
    private final Dao<Currency, Long> currencyDao;
    private final Dao<IssuingBank, Long> issuingBankDao;

    private DaoFactory() {
        cardDao = new CardJDBCDaoImpl();
        cardStatusDao = new CardStatusJDBCDaoImpl();
        paymentSystemDao = new PaymentSystemJDBCDaoImpl();
        accountDao = new AccountJDBCDaoImpl();
        currencyDao = new CurrencyJDBCDaoImpl();
        issuingBankDao = new IssuingBankJDBCDaoImpl();
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

    public Dao<Currency, Long> getCurrencyDao() {
        return currencyDao;
    }

    public Dao<IssuingBank, Long> getIssuingBankDao() {
        return issuingBankDao;
    }
}
