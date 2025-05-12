package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.ResponseCode;
import org.bank.processing_center.configuration.HibernateConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class ResponseCodeHibernateDaoImpl implements Dao<ResponseCode, Long> {

    private final SessionFactory sessionFactory;

    public ResponseCodeHibernateDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void createTable() {
        // Hibernate handles schema creation based on entity definitions and configuration
        // No explicit DDL is needed in DAO for standard Hibernate usage.
        // Schema will be created when the SessionFactory is built if ddl-auto is set appropriately.
    }

    @Override
    public void dropTable() {
        // Hibernate handles schema dropping based on entity definitions and configuration
        // No explicit DDL is needed in DAO for standard Hibernate usage.
        // Schema will be dropped when the SessionFactory is built/closed if ddl-auto is set appropriately.
    }

    @Override
    public void clearTable() {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM ResponseCode").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Consider logging
        }
    }

    @Override
    public void save(ResponseCode responseCode) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(responseCode);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Consider logging
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            ResponseCode responseCode = session.get(ResponseCode.class, id);
            if (responseCode != null) {
                session.delete(responseCode);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Consider logging
        }
    }

    @Override
    public List<ResponseCode> findAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("FROM ResponseCode", ResponseCode.class).list();
        } catch (Exception e) {
            e.printStackTrace(); // Consider logging
            return null; // Or throw an exception
        }
    }

    @Override
    public Optional<ResponseCode> findById(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            ResponseCode responseCode = session.get(ResponseCode.class, id);
            return Optional.ofNullable(responseCode);
        } catch (Exception e) {
            e.printStackTrace(); // Consider logging
            return Optional.empty(); // Or throw an exception
        }
    }

    @Override
    public void update(ResponseCode responseCode) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(responseCode);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Consider logging
        }
    }
}