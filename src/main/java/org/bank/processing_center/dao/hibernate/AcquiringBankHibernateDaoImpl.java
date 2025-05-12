package org.bank.processing_center.dao.hibernate;

import java.util.List;
import java.util.Optional;

import org.bank.processing_center.model.AcquiringBank;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class AcquiringBankHibernateDaoImpl implements HibernateDao<AcquiringBank, Long> {
    private final SessionFactory sessionFactory;
    @Override
    public Optional<AcquiringBank> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(AcquiringBank.class, id));
        }
    }

    @Override
    public List<AcquiringBank> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from AcquiringBank", AcquiringBank.class).list();
        }
    }

    @Override
    public void save(AcquiringBank acquiringBank) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(acquiringBank);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void update(AcquiringBank acquiringBank) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(acquiringBank);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) { // Change return type to void as per Dao interface
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            // Load the entity first before deleting
            AcquiringBank acquiringBank = session.get(AcquiringBank.class, id); // Use get instead of load for null check
            if (acquiringBank != null) {
                session.delete(acquiringBank);
            } else {
                // Optionally handle the case where the entity with the given ID is not found
                System.out.println("AcquiringBank with ID " + id + " not found for deletion.");
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
    public void createTable() {
        // Basic Hibernate logic to create table - typically handled by Hibernate
        // configuration
        // In a real application, you would configure Hibernate to generate schema
        System.out.println("Hibernate configured to create AcquiringBank table.");
    }

    @Override
    public void dropTable() {
        // Basic Hibernate logic to drop table - typically handled by Hibernate
        // configuration
        // In a real application, you would configure Hibernate to drop schema
        System.out.println("Hibernate configured to drop AcquiringBank table.");
    }

    @Override
    public void clearTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM AcquiringBank").executeUpdate();
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
        return this.sessionFactory;
    }

    public AcquiringBankHibernateDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}