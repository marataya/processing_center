package org.bank.processing_center.configuration;

import org.bank.processing_center.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateConfig {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put("hibernate.connection.driver_class", "org.postgresql.Driver");
                settings.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/processing_center_db");
                settings.put("hibernate.connection.username", "postgres");
                settings.put("hibernate.connection.password", "pass");
                settings.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                settings.put("hibernate.show_sql", "true");
                settings.put("hibernate.format_sql", "true");
                settings.put("hibernate.hbm2ddl.auto", "create-drop");

                configuration.setProperties(settings);

                // Add all your annotated entity classes here
                configuration.addAnnotatedClass(Account.class);
                configuration.addAnnotatedClass(Card.class);
                configuration.addAnnotatedClass(CardStatus.class);
                configuration.addAnnotatedClass(Currency.class);
                configuration.addAnnotatedClass(IssuingBank.class);
                configuration.addAnnotatedClass(PaymentSystem.class);
                configuration.addAnnotatedClass(AcquiringBank.class); // Make sure all are added
                configuration.addAnnotatedClass(MerchantCategoryCode.class);
                configuration.addAnnotatedClass(ResponseCode.class);
                configuration.addAnnotatedClass(SalesPoint.class);
                configuration.addAnnotatedClass(Terminal.class);
                configuration.addAnnotatedClass(Transaction.class);
                configuration.addAnnotatedClass(TransactionType.class);

                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
                // If SessionFactory creation fails, ensure the static field remains null or handle appropriately
                sessionFactory = null; // Ensure it's null on failure
                throw new RuntimeException("Error building Hibernate SessionFactory", e); // Re-throw the exception
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            sessionFactory = null; // Set to null after closing
            System.out.println("Hibernate SessionFactory has been shut down.");
        }
    }
}