package org.bank.processing_center.dao.factory;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.dao.hibernate.*;
import org.bank.processing_center.dao.jdbc.*;
import org.bank.processing_center.model.*;
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
            if (jdbcInstance == null) {
                jdbcInstance = new DaoFactory("jdbc");
            }
            return jdbcInstance;
        } else if ("hibernate".equalsIgnoreCase(daoType)) {
            if (sessionFactory == null) { // Check if sessionFactory is provided for hibernate
                throw new IllegalArgumentException("Hibernate DAO type requires a SessionFactory.");
            }
            if (hibernateInstance == null) {
                hibernateInstance = new DaoFactory("hibernate", sessionFactory); // Use the constructor with SessionFactory
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
