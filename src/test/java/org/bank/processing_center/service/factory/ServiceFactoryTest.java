package org.bank.processing_center.service.factory;

import org.bank.processing_center.service.AccountService;
import org.bank.processing_center.service.CardService;
import org.bank.processing_center.service.CardStatusService;
import org.bank.processing_center.service.PaymentSystemService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceFactoryTest {

    @Test
    public void testGetInstance() {
        ServiceFactory factory1 = ServiceFactory.getInstance();
        ServiceFactory factory2 = ServiceFactory.getInstance();

        // Test singleton pattern
        assertNotNull(factory1);
        assertSame(factory1, factory2);
    }

    @Test
    public void testGetCardService() {
        ServiceFactory factory = ServiceFactory.getInstance();
        CardService cardService = factory.getCardService();

        assertNotNull(cardService);
        // Ensure we get the same instance each time
        assertSame(cardService, factory.getCardService());
    }

    @Test
    public void testGetCardStatusService() {
        ServiceFactory factory = ServiceFactory.getInstance();
        CardStatusService cardStatusService = factory.getCardStatusService();

        assertNotNull(cardStatusService);
        // Ensure we get the same instance each time
        assertSame(cardStatusService, factory.getCardStatusService());
    }

    @Test
    public void testGetPaymentSystemService() {
        ServiceFactory factory = ServiceFactory.getInstance();
        PaymentSystemService paymentSystemService = factory.getPaymentSystemService();

        assertNotNull(paymentSystemService);
        // Ensure we get the same instance each time
        assertSame(paymentSystemService, factory.getPaymentSystemService());
    }

    @Test
    public void testGetAccountService() {
        ServiceFactory factory = ServiceFactory.getInstance();
        AccountService accountService = factory.getAccountService();

        assertNotNull(accountService);
        // Ensure we get the same instance each time
        assertSame(accountService, factory.getAccountService());
    }
}
