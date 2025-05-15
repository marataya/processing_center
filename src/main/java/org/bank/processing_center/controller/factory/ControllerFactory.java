package org.bank.processing_center.controller.factory;

import org.bank.processing_center.configuration.HibernateConfig;
import org.bank.processing_center.controller.*;
import org.bank.processing_center.service.factory.ServiceFactory;
import org.bank.processing_center.view.ConsoleView;
import org.hibernate.SessionFactory;

/**
 * Factory for creating controller instances
 */
public class ControllerFactory {

    private static ControllerFactory jdbcInstance;
    private static ControllerFactory hibernateInstance;

    private final CardController cardController;
    private final AccountController accountController;
    private final CardStatusController cardStatusController;
    private final PaymentSystemController paymentSystemController;
    private final CurrencyController currencyController;
    private final IssuingBankController issuingBankController;
    private final AcquiringBankController acquiringBankController;
    private final MerchantCategoryCodeController merchantCategoryCodeController;
    private final TransactionTypeController transactionTypeController;
    private final ResponseCodeController responseCodeController;
    private final TerminalController terminalController;
    private final TransactionController transactionController;
    private final SalesPointController salesPointController;
    private final ConsoleView view;

    private ControllerFactory(String daoType, SessionFactory sessionFactory) {
        // Create view
        this.view = new ConsoleView();

        // Get service instances
        ServiceFactory serviceFactory = ServiceFactory.getInstance(daoType, sessionFactory);

        // Create controllers
        this.cardController = new CardController(serviceFactory.getCardService(), view);
        this.accountController = new AccountController(serviceFactory.getAccountService(), view);
        this.cardStatusController = new CardStatusController(serviceFactory.getCardStatusService(), view);
        this.paymentSystemController = new PaymentSystemController(serviceFactory.getPaymentSystemService(), view);
        this.currencyController = new CurrencyController(serviceFactory.getCurrencyService(), view);
        this.issuingBankController = new IssuingBankController(serviceFactory.getIssuingBankService(), view);
        this.acquiringBankController = new AcquiringBankController(serviceFactory.getAcquiringBankService(), view);
        this.merchantCategoryCodeController = new MerchantCategoryCodeController(serviceFactory.getMerchantCategoryCodeService(), view);
        this.transactionTypeController = new TransactionTypeController(serviceFactory.getTransactionTypeService(), view);
        this.responseCodeController = new ResponseCodeController(serviceFactory.getResponseCodeService(), view);
        this.terminalController = new TerminalController(serviceFactory.getTerminalService(), view);
        this.transactionController = new TransactionController(serviceFactory.getTransactionService(), view);
        this.salesPointController = new SalesPointController(serviceFactory.getSalesPointService(), view);
    }

    public CardController getCardController() {
        return cardController;
    }

    public AccountController getAccountController() {
        return accountController;
    }

    public PaymentSystemController getPaymentSystemController() {
        return paymentSystemController;
    }

    public CardStatusController getCardStatusController() {
        return cardStatusController;
    }

    public CurrencyController getCurrencyController() {
        return currencyController;
    }

    public IssuingBankController getIssuingBankController() {
        return issuingBankController;
    }

    public AcquiringBankController getAcquiringBankController() {
        return acquiringBankController;
    }

    public MerchantCategoryCodeController getMerchantCategoryCodeController() {
        return merchantCategoryCodeController;
    }

    public TransactionTypeController getTransactionTypeController() {
        return transactionTypeController;
    }

    public ResponseCodeController getResponseCodeController() {
        return responseCodeController;
    }

    public TerminalController getTerminalController() {
        return terminalController;
    }

    public TransactionController getTransactionController() {
        return transactionController;
    }

    public SalesPointController getSalesPointController() {
        return salesPointController;
    }

    public ConsoleView getView() {
        return view;
    }

    public static synchronized ControllerFactory getInstance(String daoType) {
        if ("jdbc".equalsIgnoreCase(daoType)) {
            if (jdbcInstance == null) {
                // For JDBC, SessionFactory is null
                jdbcInstance = new ControllerFactory(daoType, null);
            }
            return jdbcInstance;
        } else if ("hibernate".equalsIgnoreCase(daoType)) {
            if (hibernateInstance == null) {
                SessionFactory sessionFactory = null;
                try {
                    sessionFactory = HibernateConfig.getSessionFactory();
                    // Create the instance, passing the obtained SessionFactory
                    hibernateInstance = new ControllerFactory(daoType, sessionFactory);
                } catch (Exception e) {
                    System.err.println("Error obtaining Hibernate SessionFactory: " + e.getMessage());
                    e.printStackTrace();
                    if (sessionFactory != null) {
                        sessionFactory.close();
                    }
                    throw new RuntimeException("Failed to initialize Hibernate SessionFactory", e);
                }
            }
            return hibernateInstance;
        }
        throw new IllegalArgumentException("Unknown DAO type: " + daoType);
    }
}
