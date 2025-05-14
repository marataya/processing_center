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
                settings.put("hibernate.hbm2ddl.auto", "update");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(Account.class);
                configuration.addAnnotatedClass(Card.class);
                configuration.addAnnotatedClass(CardStatus.class);
                configuration.addAnnotatedClass(Currency.class);
                configuration.addAnnotatedClass(IssuingBank.class);
                configuration.addAnnotatedClass(PaymentSystem.class);

                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}