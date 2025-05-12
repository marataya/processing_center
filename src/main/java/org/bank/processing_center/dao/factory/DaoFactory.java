package org.bank.processing_center.dao.factory;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.dao.hibernate.AccountHibernateDaoImpl;
import org.bank.processing_center.dao.hibernate.AcquiringBankHibernateDaoImpl;
import org.bank.processing_center.dao.hibernate.CardHibernateDaoImpl;
import org.bank.processing_center.dao.hibernate.CardStatusHibernateDaoImpl;
import org.bank.processing_center.dao.hibernate.CurrencyHibernateDaoImpl;
import org.bank.processing_center.dao.hibernate.IssuingBankHibernateDaoImpl;
import org.bank.processing_center.dao.hibernate.MerchantCategoryCodeHibernateDaoImpl;
import org.bank.processing_center.dao.hibernate.PaymentSystemHibernateDaoImpl;
import org.bank.processing_center.dao.hibernate.ResponseCodeHibernateDaoImpl;
import org.bank.processing_center.dao.hibernate.SalesPointHibernateDaoImpl;
import org.bank.processing_center.dao.hibernate.TerminalHibernateDaoImpl;
import org.bank.processing_center.dao.hibernate.TransactionHibernateDaoImpl;
import org.bank.processing_center.dao.hibernate.TransactionTypeHibernateDaoImpl;
import org.bank.processing_center.dao.jdbc.AccountJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.AcquiringBankJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.CardJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.CardStatusJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.CurrencyJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.IssuingBankJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.MerchantCategoryCodeJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.PaymentSystemJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.ResponseCodeJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.SalesPointJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.TerminalJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.TransactionJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.TransactionTypeJDBCDaoImpl;
import org.bank.processing_center.model.Account;
import org.bank.processing_center.model.AcquiringBank;
import org.bank.processing_center.model.Card;
import org.bank.processing_center.model.CardStatus;
import org.bank.processing_center.model.Currency;
import org.bank.processing_center.model.IssuingBank;
import org.bank.processing_center.model.MerchantCategoryCode;
import org.bank.processing_center.model.PaymentSystem;
import org.bank.processing_center.model.ResponseCode;
import org.bank.processing_center.model.SalesPoint;
import org.bank.processing_center.model.Terminal;
import org.bank.processing_center.model.Transaction;
import org.bank.processing_center.model.TransactionType;
import org.hibernate.SessionFactory;

/**
 * Factory for creating DAO instances
 */
public class DaoFactory {

    private static DaoFactory jdbcInstance;
    private static DaoFactory hibernateInstance;
    private final SessionFactory sessionFactory;

    private final Dao<Card, Long> cardDao;
    private final Dao<CardStatus, Long> cardStatusDao;
    private final Dao<PaymentSystem, Long> paymentSystemDao;
    private final Dao<Account, Long> accountDao;
    private final Dao<AcquiringBank, Long> acquiringBankDao;
    private final Dao<MerchantCategoryCode, Long> merchantCategoryCodeDao;
    private final Dao<TransactionType, Long> transactionTypeDao;
    private final Dao<ResponseCode, Long> responseCodeDao;
    private final Dao<Terminal, Long> terminalDao;
    private final Dao<Transaction, Long> transactionDao;
    private final Dao<SalesPoint, Long> salesPointDao;
    private final Dao<Currency, Long> currencyDao;
    private final Dao<IssuingBank, Long> issuingBankDao;

    private DaoFactory(String daoType) {
        if ("jdbc".equalsIgnoreCase(daoType)) {
            this.sessionFactory = null; // SessionFactory not needed for JDBC
            this.cardDao = new CardJDBCDaoImpl();
            this.cardStatusDao = new CardStatusJDBCDaoImpl();
            this.paymentSystemDao = new PaymentSystemJDBCDaoImpl();
            this.accountDao = new AccountJDBCDaoImpl();
            this.acquiringBankDao = new AcquiringBankJDBCDaoImpl();
            this.merchantCategoryCodeDao = new MerchantCategoryCodeJDBCDaoImpl();
            this.transactionTypeDao = new TransactionTypeJDBCDaoImpl();
            this.responseCodeDao = new ResponseCodeJDBCDaoImpl();
            this.terminalDao = new TerminalJDBCDaoImpl();
            this.transactionDao = new TransactionJDBCDaoImpl();
            this.salesPointDao = new SalesPointJDBCDaoImpl();
            this.currencyDao = new CurrencyJDBCDaoImpl();
            this.issuingBankDao = new IssuingBankJDBCDaoImpl();
        } else if ("hibernate".equalsIgnoreCase(daoType)) {
            throw new IllegalArgumentException("Hibernate DAO type requires a SessionFactory.");
        } else {
            throw new IllegalArgumentException("Invalid DAO type specified: " + daoType);
        }
    }

    private DaoFactory(String daoType, SessionFactory sessionFactory) {
        if ("hibernate".equalsIgnoreCase(daoType)) {
            this.sessionFactory = sessionFactory;
            this.cardDao = new CardHibernateDaoImpl(sessionFactory);
            this.cardStatusDao = new CardStatusHibernateDaoImpl(sessionFactory);
            this.paymentSystemDao = new PaymentSystemHibernateDaoImpl(sessionFactory);
            this.accountDao = new AccountHibernateDaoImpl(sessionFactory);
            this.acquiringBankDao = new AcquiringBankHibernateDaoImpl(sessionFactory);
            this.merchantCategoryCodeDao = new MerchantCategoryCodeHibernateDaoImpl(sessionFactory);
            this.transactionTypeDao = new TransactionTypeHibernateDaoImpl(sessionFactory);
            this.responseCodeDao = new ResponseCodeHibernateDaoImpl(sessionFactory);
            this.terminalDao = new TerminalHibernateDaoImpl(sessionFactory);
            this.transactionDao = new TransactionHibernateDaoImpl(sessionFactory);
            this.salesPointDao = new SalesPointHibernateDaoImpl(sessionFactory);
            this.currencyDao = new CurrencyHibernateDaoImpl(sessionFactory);
            this.issuingBankDao = new IssuingBankHibernateDaoImpl(sessionFactory);
        } else {
            throw new IllegalArgumentException("This constructor is only for Hibernate DAO type.");
        }
    }

    public static synchronized DaoFactory getInstance(String daoType, SessionFactory sessionFactory) {
        if ("jdbc".equalsIgnoreCase(daoType)) {
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

    // Overloaded getInstance for backward compatibility with JDBC (if needed)
    public static synchronized DaoFactory getInstance(String daoType) {
        return getInstance(daoType, null); // Pass null for SessionFactory for JDBC
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

    public Dao<AcquiringBank, Long> getAcquiringBankDao() {
        return acquiringBankDao;
    }

    public Dao<MerchantCategoryCode, Long> getMerchantCategoryCodeDao() {
        return merchantCategoryCodeDao;
    }

    public Dao<TransactionType, Long> getTransactionTypeDao() {
        return transactionTypeDao;
    }

    public Dao<ResponseCode, Long> getResponseCodeDao() {
        return responseCodeDao;
    }

    public Dao<Terminal, Long> getTerminalDao() {
        return terminalDao;
    }

    public Dao<Transaction, Long> getTransactionDao() {
        return transactionDao;
    }

    public Dao<SalesPoint, Long> getSalesPointDao() {
        return salesPointDao;
    }

    public Dao<Currency, Long> getCurrencyDao() {
        return currencyDao;
    }

    public Dao<IssuingBank, Long> getIssuingBankDao() {
        return issuingBankDao;
    }
}
