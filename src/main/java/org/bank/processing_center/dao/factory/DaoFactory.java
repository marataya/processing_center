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
    // The sessionFactory field is kept here to signify that this DaoFactory instance
    // is for Hibernate and was initialized with a (presumably valid) SessionFactory.
    // It's not directly used for DAO instantiation anymore if DAOs get it from HibernateConfig.
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

    // Constructor for JDBC DAOs
    private DaoFactory(String daoType) {
        if (!"jdbc".equalsIgnoreCase(daoType)) {
            // This constructor should only be called with "jdbc"
            throw new IllegalArgumentException("This DaoFactory constructor is intended for JDBC type only.");
        }
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
    }

    // Constructor for Hibernate DAOs
    private DaoFactory(String daoType, SessionFactory sessionFactory) {
        if (!"hibernate".equalsIgnoreCase(daoType)) {
            // This constructor should only be called with "hibernate"
            throw new IllegalArgumentException("This DaoFactory constructor is intended for Hibernate type only.");
        }
        if (sessionFactory == null) {
            // This check is crucial and should have been done by the caller (getInstance)
            // but an extra safeguard here is fine.
            throw new IllegalArgumentException("Hibernate DAO type requires a non-null SessionFactory for DaoFactory initialization.");
        }
        this.sessionFactory = sessionFactory; // Store it, even if not passed to individual DAOs

        // Instantiate Hibernate DAOs using their no-argument constructors
        // as they now extend AbstractHibernateDao which handles SessionFactory access.
        this.cardDao = new CardHibernateDaoImpl();
        this.cardStatusDao = new CardStatusHibernateDaoImpl();
        this.paymentSystemDao = new PaymentSystemHibernateDaoImpl();
        this.accountDao = new AccountHibernateDaoImpl();
        this.acquiringBankDao = new AcquiringBankHibernateDaoImpl();
        this.merchantCategoryCodeDao = new MerchantCategoryCodeHibernateDaoImpl();
        this.transactionTypeDao = new TransactionTypeHibernateDaoImpl();
        this.responseCodeDao = new ResponseCodeHibernateDaoImpl();
        this.terminalDao = new TerminalHibernateDaoImpl();
        this.transactionDao = new TransactionHibernateDaoImpl();
        this.salesPointDao = new SalesPointHibernateDaoImpl();
        this.currencyDao = new CurrencyHibernateDaoImpl();
        this.issuingBankDao = new IssuingBankHibernateDaoImpl();
    }

    public static synchronized DaoFactory getInstance(String daoType, SessionFactory sessionFactory) {
        if ("jdbc".equalsIgnoreCase(daoType)) {
            if (jdbcInstance == null) {
                // Call the JDBC-specific constructor
                jdbcInstance = new DaoFactory("jdbc");
            }
            return jdbcInstance;
        } else if ("hibernate".equalsIgnoreCase(daoType)) {
            if (sessionFactory == null) {
                throw new IllegalArgumentException("Hibernate DAO type requires a SessionFactory to initialize DaoFactory.");
            }
            if (hibernateInstance == null) {
                // Call the Hibernate-specific constructor, passing the sessionFactory
                // (even if individual DAOs don't take it, DaoFactory itself might need to know it's for Hibernate)
                hibernateInstance = new DaoFactory("hibernate", sessionFactory);
            }
            // Optional: Could add a check here if hibernateInstance.sessionFactory != sessionFactory
            // to handle cases where a different SessionFactory is passed on subsequent calls.
            // For now, it uses the SessionFactory from the first initialization.
            return hibernateInstance;
        } else {
            throw new IllegalArgumentException("Invalid DAO type specified: " + daoType);
        }
    }

    /**
     * Gets an instance of DaoFactory.
     * For JDBC, it initializes if not already done.
     * For Hibernate, this method will fail if the Hibernate instance hasn't been
     * initialized with a SessionFactory via {@link #getInstance(String, SessionFactory)}.
     *
     * @param daoType The type of DAO implementation to use ("jdbc" or "hibernate").
     * @return The DaoFactory instance for the specified type.
     */
    public static synchronized DaoFactory getInstance(String daoType) {
        // This method delegates to the two-argument version.
        // If daoType is "hibernate", it will pass null for sessionFactory,
        // which will correctly cause the two-argument getInstance to throw an
        // IllegalArgumentException if hibernateInstance is not already created.
        // This is the desired behavior: Hibernate DaoFactory requires explicit SessionFactory setup.
        return getInstance(daoType, null);
    }

    // Getter methods remain the same
    public Dao<Card, Long> getCardDao() { return cardDao; }
    public Dao<CardStatus, Long> getCardStatusDao() { return cardStatusDao; }
    public Dao<PaymentSystem, Long> getPaymentSystemDao() { return paymentSystemDao; }
    public Dao<Account, Long> getAccountDao() { return accountDao; }
    public Dao<AcquiringBank, Long> getAcquiringBankDao() { return acquiringBankDao; }
    public Dao<MerchantCategoryCode, Long> getMerchantCategoryCodeDao() { return merchantCategoryCodeDao; }
    public Dao<TransactionType, Long> getTransactionTypeDao() { return transactionTypeDao; }
    public Dao<ResponseCode, Long> getResponseCodeDao() { return responseCodeDao; }
    public Dao<Terminal, Long> getTerminalDao() { return terminalDao; }
    public Dao<Transaction, Long> getTransactionDao() { return transactionDao; }
    public Dao<SalesPoint, Long> getSalesPointDao() { return salesPointDao; }
    public Dao<Currency, Long> getCurrencyDao() { return currencyDao; }
    public Dao<IssuingBank, Long> getIssuingBankDao() { return issuingBankDao; }


    /**
     * Resets the static singleton instances.
     * FOR TESTING PURPOSES ONLY.
     */
    public static synchronized void resetInstancesForTesting() {
        jdbcInstance = null;
        hibernateInstance = null;
    }
}