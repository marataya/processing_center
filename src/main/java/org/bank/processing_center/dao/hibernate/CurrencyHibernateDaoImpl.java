package org.bank.processing_center.dao.hibernate;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.bank.processing_center.model.Currency;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CurrencyHibernateDaoImpl implements HibernateDao<Currency, Long> {

    private SessionFactory sessionFactory;

    public CurrencyHibernateDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void createTable() {
        // Basic implementation for creating table using Hibernate
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS currency (id BIGINT PRIMARY KEY, name VARCHAR(255))").executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void dropTable() {
        // Basic implementation for dropping table using Hibernate
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS currency").executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void clearTable() {
        // Basic implementation for clearing table using Hibernate
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("DELETE FROM currency").executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void save(Currency currency) {
        // Basic implementation for saving entity using Hibernate
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(currency);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        // Basic implementation for deleting entity using Hibernate
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Currency currency = session.get(Currency.class, id);
            if (currency != null) {
                session.delete(currency);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<Currency> findAll() {
        // Basic implementation for finding all entities using Hibernate
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Currency> criteria = builder.createQuery(Currency.class);
            Root<Currency> root = criteria.from(Currency.class);
            criteria.select(root);
            return session.createQuery(criteria).getResultList();
        }
    }

    @Override
    public Optional<Currency> findById(Long id) {
        // Basic implementation for finding entity by id using Hibernate
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Currency.class, id));
        }
    }

    @Override
    public void update(Currency currency) {
        // Basic implementation for updating entity using Hibernate
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(currency);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}