package org.bank.processing_center.service.factory;

import org.bank.processing_center.dao.factory.DaoFactory;
import org.bank.processing_center.dao.jdbc.AccountJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.CardJDBCDaoImpl;
import org.bank.processing_center.service.*;

/**
 * Factory for creating service instances
 */
public class ServiceFactory {

    private static ServiceFactory instance;

    private final CardService cardService;
    private final CardStatusService cardStatusService;
    private final PaymentSystemService paymentSystemService;
    private final AccountService accountService;

    private ServiceFactory(String daoType) {

        // Pass the daoType to the Service constructors
        cardService = new CardService(daoType);
        cardStatusService = new CardStatusService(daoType);
        paymentSystemService = new PaymentSystemService(daoType);
        accountService = new AccountService(daoType);

        // Create service instances

    }

    public static synchronized ServiceFactory getInstance(String daoType) {
        // Consider how you want to handle multiple instances with different daoTypes if needed
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    public CardService getCardService() {
        return cardService;
    }

    public CardStatusService getCardStatusService() {
        return cardStatusService;
    }

    public PaymentSystemService getPaymentSystemService() {
        return paymentSystemService;
    }

    public AccountService getAccountService() {
        return accountService;
    }
}
