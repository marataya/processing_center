package org.bank.processing_center.controller.factory;

import org.bank.processing_center.controller.AccountController;
import org.bank.processing_center.controller.CardController;
import org.bank.processing_center.controller.CardStatusController;
import org.bank.processing_center.controller.PaymentSystemController;
import org.bank.processing_center.service.factory.ServiceFactory;
import org.bank.processing_center.view.ConsoleView;

/**
 * Factory for creating controller instances
 */
public class ControllerFactory {

    private static ControllerFactory instance;

    private final CardController cardController;
    private final AccountController accountController;
    private final CardStatusController cardStatusController;
    private final PaymentSystemController paymentSystemController;
    private final ConsoleView view;

    private ControllerFactory() {
        // Create view
        this.view = new ConsoleView();

        // Get service instances
        ServiceFactory serviceFactory = ServiceFactory.getInstance();

        // Create controllers
        this.cardController = new CardController(serviceFactory.getCardService(), view);
        this.accountController = new AccountController(serviceFactory.getAccountService(), view);
        this.cardStatusController = new CardStatusController(serviceFactory.getCardStatusService(), view);
        this.paymentSystemController = new PaymentSystemController(serviceFactory.getPaymentSystemService(), view);
    }

    public static synchronized ControllerFactory getInstance() {
        if (instance == null) {
            instance = new ControllerFactory();
        }
        return instance;
    }

    public CardController getCardController() {
        return cardController;
    }

    public AccountController getAccountController() {
        return accountController;
    }

    public CardStatusController getCardStatusController() {
        return cardStatusController;
    }

    public PaymentSystemController getPaymentSystemController() {
        return paymentSystemController;
    }

    public ConsoleView getView() {
        return view;
    }
}
