package org.bank.processing_center.dao.factory;

import org.bank.processing_center.configuration.HibernateConfig;
import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.Account;
import org.bank.processing_center.model.Card;
import org.bank.processing_center.model.Currency;
import org.bank.processing_center.model.SalesPoint;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // To use non-static @BeforeAll/@AfterAll if needed
public class DaoFactoryTest {

    private static SessionFactory sessionFactory;

    @BeforeAll
    void setUpAll() {
        // Initialize SessionFactory for Hibernate tests
        // This assumes HibernateConfig is set up for a test environment (e.g., H2, create-drop)
        try {
            sessionFactory = HibernateConfig.getSessionFactory();
            assertNotNull(sessionFactory, "SessionFactory should be initialized for Hibernate tests.");
        } catch (Exception e) {
            fail("Failed to initialize SessionFactory for Hibernate tests: " + e.getMessage(), e);
        }
    }

    @AfterAll
    void tearDownAll() {
        // Shutdown Hibernate SessionFactory
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            HibernateConfig.shutdown();
        }
    }

    @BeforeEach
    void setUpEach() {
        // Reset static instances in DaoFactory before each test
        // to ensure isolation for singleton creation tests.
        DaoFactory.resetInstancesForTesting();
    }

    @Test
    void testGetInstance_Jdbc_ReturnsNotNullAndSingleton() {
        DaoFactory factory1 = DaoFactory.getInstance("jdbc");
        DaoFactory factory2 = DaoFactory.getInstance("jdbc");

        assertNotNull(factory1, "JDBC DaoFactory instance should not be null.");
        assertSame(factory1, factory2, "Multiple calls for JDBC DaoFactory should return the same instance.");
    }

    @Test
    void testGetInstance_Hibernate_WithSessionFactory_ReturnsNotNullAndSingleton() {
        assertNotNull(sessionFactory, "SessionFactory must be available for this test.");

        DaoFactory factory1 = DaoFactory.getInstance("hibernate", sessionFactory);
        DaoFactory factory2 = DaoFactory.getInstance("hibernate", sessionFactory);

        assertNotNull(factory1, "Hibernate DaoFactory instance should not be null when SessionFactory is provided.");
        assertSame(factory1, factory2, "Multiple calls for Hibernate DaoFactory with the same SessionFactory should return the same instance.");
    }

    @Test
    void testGetInstance_Hibernate_SingleArg_WhenNotInitialized_ThrowsException() {
        // This tests the DaoFactory.getInstance("hibernate") which delegates
        // to getInstance("hibernate", null).
        // It should throw an IllegalArgumentException because hibernateInstance is null
        // and a null SessionFactory is passed.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            DaoFactory.getInstance("hibernate");
        });
        assertTrue(exception.getMessage().contains("Hibernate DAO type requires a SessionFactory"),
                "Exception message should indicate SessionFactory requirement.");
    }

    @Test
    void testGetInstance_Hibernate_WithNullSessionFactory_ThrowsException() {
        // Directly test getInstance("hibernate", null)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            DaoFactory.getInstance("hibernate", null);
        });
        assertTrue(exception.getMessage().contains("Hibernate DAO type requires a SessionFactory"),
                "Exception message should indicate SessionFactory requirement.");
    }


    @Test
    void testGetInstance_InvalidType_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            DaoFactory.getInstance("unknown-type");
        });
        assertTrue(exception.getMessage().contains("Invalid DAO type specified"),
                "Exception message should indicate invalid DAO type.");
    }

    @Test
    void testJdbcAndHibernateFactories_AreDifferentInstances() {
        assertNotNull(sessionFactory, "SessionFactory must be available for this test.");

        DaoFactory jdbcFactory = DaoFactory.getInstance("jdbc");
        DaoFactory hibernateFactory = DaoFactory.getInstance("hibernate", sessionFactory);

        assertNotNull(jdbcFactory);
        assertNotNull(hibernateFactory);
        assertNotSame(jdbcFactory, hibernateFactory, "JDBC and Hibernate DaoFactory instances should be different.");
    }

    // --- Tests for specific DAO getters from JDBC Factory ---
    @Test
    void testGetCardDao_FromJdbcFactory() {
        DaoFactory factory = DaoFactory.getInstance("jdbc");
        Dao<Card, Long> dao1 = factory.getCardDao();
        Dao<Card, Long> dao2 = factory.getCardDao();
        assertNotNull(dao1, "CardDao from JDBC factory should not be null.");
        assertSame(dao1, dao2, "CardDao from JDBC factory should be a singleton within the factory.");
    }

    @Test
    void testGetAccountDao_FromJdbcFactory() {
        DaoFactory factory = DaoFactory.getInstance("jdbc");
        Dao<Account, Long> dao1 = factory.getAccountDao();
        Dao<Account, Long> dao2 = factory.getAccountDao();
        assertNotNull(dao1, "AccountDao from JDBC factory should not be null.");
        assertSame(dao1, dao2, "AccountDao from JDBC factory should be a singleton within the factory.");
    }

    // --- Tests for specific DAO getters from Hibernate Factory ---
    @Test
    void testGetCardDao_FromHibernateFactory() {
        assertNotNull(sessionFactory, "SessionFactory must be available for this test.");
        DaoFactory factory = DaoFactory.getInstance("hibernate", sessionFactory);
        Dao<Card, Long> dao1 = factory.getCardDao();
        Dao<Card, Long> dao2 = factory.getCardDao();
        assertNotNull(dao1, "CardDao from Hibernate factory should not be null.");
        assertSame(dao1, dao2, "CardDao from Hibernate factory should be a singleton within the factory.");
    }

    @Test
    void testGetAccountDao_FromHibernateFactory() {
        assertNotNull(sessionFactory, "SessionFactory must be available for this test.");
        DaoFactory factory = DaoFactory.getInstance("hibernate", sessionFactory);
        Dao<Account, Long> dao1 = factory.getAccountDao();
        Dao<Account, Long> dao2 = factory.getAccountDao();
        assertNotNull(dao1, "AccountDao from Hibernate factory should not be null.");
        assertSame(dao1, dao2, "AccountDao from Hibernate factory should be a singleton within the factory.");
    }

    // Add similar tests for all other DAO getters (getCardStatusDao, getPaymentSystemDao, etc.)
    // for both JDBC and Hibernate factories. I'll add a couple more as examples.

    @Test
    void testGetCurrencyDao_FromJdbcFactory() {
        DaoFactory factory = DaoFactory.getInstance("jdbc");
        Dao<Currency, Long> dao = factory.getCurrencyDao();
        assertNotNull(dao);
        assertSame(dao, factory.getCurrencyDao());
    }

    @Test
    void testGetCurrencyDao_FromHibernateFactory() {
        assertNotNull(sessionFactory, "SessionFactory must be available for this test.");
        DaoFactory factory = DaoFactory.getInstance("hibernate", sessionFactory);
        Dao<Currency, Long> dao = factory.getCurrencyDao();
        assertNotNull(dao);
        assertSame(dao, factory.getCurrencyDao());
    }

    @Test
    void testGetSalesPointDao_FromJdbcFactory() {
        DaoFactory factory = DaoFactory.getInstance("jdbc");
        Dao<SalesPoint, Long> dao = factory.getSalesPointDao();
        assertNotNull(dao);
        assertSame(dao, factory.getSalesPointDao());
    }

    @Test
    void testGetSalesPointDao_FromHibernateFactory() {
        assertNotNull(sessionFactory, "SessionFactory must be available for this test.");
        DaoFactory factory = DaoFactory.getInstance("hibernate", sessionFactory);
        Dao<SalesPoint, Long> dao = factory.getSalesPointDao();
        assertNotNull(dao);
        assertSame(dao, factory.getSalesPointDao());
    }
}