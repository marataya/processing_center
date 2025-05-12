package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.TransactionType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class TransactionTypeHibernateDaoImpl implements Dao<TransactionType, Long> {

    private final SessionFactory sessionFactory;

    public TransactionTypeHibernateDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void createTable() {
        // Table creation is typically handled by Hibernate schema generation
        // based on entity mappings. This method might not be needed in a
        // pure Hibernate setup or could be used for initial data population.
        System.out.println("Hibernate handles TransactionType table creation.");
    }

    @Override
    public void dropTable() {
        // Table dropping is typically handled by Hibernate schema generation.
        // Use with caution in production.
        System.out.println("Hibernate handles TransactionType table dropping.");
    }

    @Override
    public void clearTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM TransactionType").executeUpdate();
            transaction.commit();
            System.out.println("TransactionType table cleared.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error clearing TransactionType table: " + e.getMessage());
        }
    }

    @Override
    public void save(TransactionType transactionType) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(transactionType);
            transaction.commit();
            System.out.println("TransactionType saved: " + transactionType);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error saving TransactionType: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            TransactionType transactionType = session.get(TransactionType.class, id);
            if (transactionType != null) {
                session.delete(transactionType);
                transaction.commit();
                System.out.println("TransactionType with id " + id + " deleted.");
            } else {
                transaction.rollback();
                System.out.println("TransactionType with id " + id + " not found.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error deleting TransactionType: " + e.getMessage());
        }
    }

    @Override
    public List<TransactionType> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM TransactionType", TransactionType.class).list();
        } catch (Exception e) {
            System.err.println("Error finding all TransactionTypes: " + e.getMessage());
            return null; // Or throw an exception
        }
    }

    @Override
    public Optional<TransactionType> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            TransactionType transactionType = session.get(TransactionType.class, id);
            return Optional.ofNullable(transactionType);
        } catch (Exception e) {
            System.err.println("Error finding TransactionType by id: " + e.getMessage());
            return Optional.empty(); // Or throw an exception
        }
    }

    @Override
    public void update(TransactionType transactionType) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(transactionType);
            transaction.commit();
            System.out.println("TransactionType updated: " + transactionType);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error updating TransactionType: " + e.getMessage());
        }
    }
}