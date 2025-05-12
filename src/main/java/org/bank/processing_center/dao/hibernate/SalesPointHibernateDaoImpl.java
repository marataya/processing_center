package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.SalesPoint;
import org.bank.processing_center.configuration.HibernateConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class SalesPointHibernateDaoImpl implements Dao<SalesPoint, Long> {

    @Override
    public void createTable() {
        // Hibernate handles schema creation based on entity definitions and configuration
        // No explicit DDL is needed in DAO for this
    }

    @Override
    public void dropTable() {
        // Hibernate handles schema drop if configured
        // No explicit DDL is needed in DAO for this
    }

    @Override
    public void clearTable() {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM SalesPoint").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Or log the error
        }
    }

    @Override
    public void save(SalesPoint salesPoint) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(salesPoint);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Or log the error
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            SalesPoint salesPoint = session.get(SalesPoint.class, id);
            if (salesPoint != null) {
                session.delete(salesPoint);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Or log the error
        }
    }

    @Override
    public List<SalesPoint> findAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("FROM SalesPoint", SalesPoint.class).list();
        } catch (Exception e) {
            e.printStackTrace(); // Or log the error
            return null; // Or throw a custom exception
        }
    }

    @Override
    public Optional<SalesPoint> findById(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            SalesPoint salesPoint = session.get(SalesPoint.class, id);
            return Optional.ofNullable(salesPoint);
        } catch (Exception e) {
            e.printStackTrace(); // Or log the error
            return Optional.empty(); // Or throw a custom exception
        }
    }

    @Override
    public void update(SalesPoint salesPoint) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(salesPoint);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Or log the error
        }
    }
}