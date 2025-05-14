// package org.bank.processing_center.dao.factory;

// import org.bank.processing_center.dao.Dao;
// import org.bank.processing_center.model.Account;
// import org.bank.processing_center.model.Card;
// import org.bank.processing_center.model.CardStatus;
// import org.bank.processing_center.model.PaymentSystem;
// import org.junit.jupiter.api.Test;

// import static org.junit.jupiter.api.Assertions.*;

// public class DaoFactoryTest {

//     @Test
//     public void testGetInstance() {
//         DaoFactory factory1 = DaoFactory.getInstance();
//         DaoFactory factory2 = DaoFactory.getInstance();

//         // Test singleton pattern
//         assertNotNull(factory1);
//         assertSame(factory1, factory2);
//     }

//     @Test
//     public void testGetCardDao() {
//         DaoFactory factory = DaoFactory.getInstance();
//         Dao<Card, Long> cardDao = factory.getCardDao();

//         assertNotNull(cardDao);
//         // Ensure we get the same instance each time
//         assertSame(cardDao, factory.getCardDao());
//     }

//     @Test
//     public void testGetCardStatusDao() {
//         DaoFactory factory = DaoFactory.getInstance();
//         Dao<CardStatus, Long> cardStatusDao = factory.getCardStatusDao();

//         assertNotNull(cardStatusDao);
//         // Ensure we get the same instance each time
//         assertSame(cardStatusDao, factory.getCardStatusDao());
//     }

//     @Test
//     public void testGetPaymentSystemDao() {
//         DaoFactory factory = DaoFactory.getInstance();
//         Dao<PaymentSystem, Long> paymentSystemDao = factory.getPaymentSystemDao();

//         assertNotNull(paymentSystemDao);
//         // Ensure we get the same instance each time
//         assertSame(paymentSystemDao, factory.getPaymentSystemDao());
//     }

//     @Test
//     public void testGetAccountDao() {
//         DaoFactory factory = DaoFactory.getInstance();
//         Dao<Account, Long> accountDao = factory.getAccountDao();

//         assertNotNull(accountDao);
//         // Ensure we get the same instance each time
//         assertSame(accountDao, factory.getAccountDao());
//     }
// }
