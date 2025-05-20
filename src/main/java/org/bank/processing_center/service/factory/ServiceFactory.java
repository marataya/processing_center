package org.bank.processing_center.service.factory;

import org.bank.processing_center.dao.factory.DaoFactory;
import org.bank.processing_center.service.*;
import org.hibernate.SessionFactory;

public class ServiceFactory {

    private static ServiceFactory jdbcInstance;
    private static ServiceFactory hibernateInstance;

    // Declare all your service fields
    private final AccountService accountService;
    private final CardService cardService;
    private final CardStatusService cardStatusService;
    private final CurrencyService currencyService;
    private final IssuingBankService issuingBankService;
    private final PaymentSystemService paymentSystemService;
    private final AcquiringBankService acquiringBankService;
    private final MerchantCategoryCodeService merchantCategoryCodeService;
    private final SalesPointService salesPointService;
    private final TerminalService terminalService;
    private final TransactionService transactionService;
    private final TransactionTypeService transactionTypeService;
    private final ResponseCodeService responseCodeService;


    // Constructor
    private ServiceFactory(String daoType, SessionFactory sessionFactory) {
        DaoFactory daoFactory = DaoFactory.getInstance(daoType, sessionFactory);

        // CORRECTED: Initialize all your services using the daos from daoFactory
        // Ensure you have corresponding implementation classes (e.g., AccountServiceImpl)
        // that take a DAO in their constructor.
        this.accountService = new AccountService(daoFactory.getAccountDao());
        this.cardService = new CardService(daoFactory.getCardDao());
        this.cardStatusService = new CardStatusService(daoFactory.getCardStatusDao());
        this.currencyService = new CurrencyService(daoFactory.getCurrencyDao());
        this.issuingBankService = new IssuingBankService(daoFactory.getIssuingBankDao());
        this.paymentSystemService = new PaymentSystemService(daoFactory.getPaymentSystemDao());
        this.acquiringBankService = new AcquiringBankService(daoFactory.getAcquiringBankDao());
        this.merchantCategoryCodeService = new MerchantCategoryCodeService(daoFactory.getMerchantCategoryCodeDao());
        this.salesPointService = new SalesPointService(daoFactory.getSalesPointDao());
        this.terminalService = new TerminalService(daoFactory.getTerminalDao());
        this.transactionService = new TransactionService(daoFactory.getTransactionDao());
        this.transactionTypeService = new TransactionTypeService(daoFactory.getTransactionTypeDao());
        this.responseCodeService = new ResponseCodeService(daoFactory.getResponseCodeDao());
    }

    // Static factory method
    public static synchronized ServiceFactory getInstance(String daoType, SessionFactory sessionFactory) {
        if ("jdbc".equalsIgnoreCase(daoType)) {
            if (jdbcInstance == null) {
                jdbcInstance = new ServiceFactory(daoType, null);
            }
            return jdbcInstance;
        } else if ("hibernate".equalsIgnoreCase(daoType)) {
            if (sessionFactory == null) {
                throw new IllegalArgumentException("ServiceFactory.getInstance received a null SessionFactory for Hibernate type.");
            }
            if (hibernateInstance == null) {
                hibernateInstance = new ServiceFactory(daoType, sessionFactory);
            }
            return hibernateInstance;
        }
        throw new IllegalArgumentException("Unknown DAO type in ServiceFactory: " + daoType);
    }

    // Getters for all your services
    public AccountService getAccountService() {
        return accountService;
    }

    public CardService getCardService() {
        return cardService;
    }

    public CardStatusService getCardStatusService() {
        return cardStatusService;
    }

    public CurrencyService getCurrencyService() {
        return currencyService;
    }

    public IssuingBankService getIssuingBankService() {
        return issuingBankService;
    }

    public PaymentSystemService getPaymentSystemService() {
        return paymentSystemService;
    }

    public AcquiringBankService getAcquiringBankService() {
        return acquiringBankService;
    }

    public MerchantCategoryCodeService getMerchantCategoryCodeService() {
        return merchantCategoryCodeService;
    }

    public SalesPointService getSalesPointService() {
        return salesPointService;
    }

    public TerminalService getTerminalService() {
        return terminalService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public TransactionTypeService getTransactionTypeService() {
        return transactionTypeService;
    }

    public ResponseCodeService getResponseCodeService() {
        return responseCodeService;
    }
}