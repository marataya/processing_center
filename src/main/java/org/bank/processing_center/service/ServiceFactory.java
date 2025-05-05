package org.bank.processing_center.service;

import org.bank.processing_center.dao.jdbc.AccountJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.CardJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.CardStatusJDBCDaoImpl;
import org.bank.processing_center.dao.jdbc.PaymentSystemJDBCDaoImpl;

/**
 * Factory for creating service instances
 */
public class ServiceFactory {

    private static ServiceFactory instance;

    private final CardService cardService;
    private final CardStatusService cardStatusService;
    private final PaymentSystemService paymentSystemService;
    private final AccountService accountService;

    private ServiceFactory() {
        // Create DAO instances
        CardJDBCDaoImpl cardDao = new CardJDBCDaoImpl();
        CardStatusJDBCDaoImpl cardStatusDao = new CardStatusJDBCDaoImpl();
        PaymentSystemJDBCDaoImpl paymentSystemDao = new PaymentSystemJDBCDaoImpl();
        AccountJDBCDaoImpl accountDao = new AccountJDBCDaoImpl();

        // Create service instances
        cardService = new CardService(cardDao);
        cardStatusService = new CardStatusService(cardStatusDao);
        paymentSystemService = new PaymentSystemService(paymentSystemDao);
        accountService = new AccountService(accountDao);
    }

    public static synchronized ServiceFactory getInstance() {
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
