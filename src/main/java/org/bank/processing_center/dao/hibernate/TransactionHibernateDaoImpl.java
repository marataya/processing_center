package org.bank.processing_center.dao.hibernate;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.bank.processing_center.model.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class TransactionHibernateDaoImpl implements HibernateDao<Transaction, Long> {

    private final SessionFactory sessionFactory;

    public TransactionHibernateDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<org.bank.processing_center.model.Transaction> findById(Long id) {
        return Optional.ofNullable(sessionFactory.openSession().get(org.bank.processing_center.model.Transaction.class, id));
    }

    @Override
    public List<Transaction> findAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
            Root<Transaction> root = criteria.from(Transaction.class);
            criteria.select(root);
            return session.createQuery(criteria).getResultList();
        }
    }

    @Override
    public void save(Transaction transaction) {
        org.hibernate.Transaction transactionHibernate = null;
        try (Session session = sessionFactory.openSession()) {
            transactionHibernate = session.beginTransaction();
            session.save(transaction);
            transactionHibernate.commit();
        } catch (Exception e) {
            if (transactionHibernate != null) {
                transactionHibernate.rollback();
            }
            e.printStackTrace(); // Or log the exception
        }
    }

    @Override
    public void update(Transaction transaction) {
        org.hibernate.Transaction transactionHibernate = null;
        try (Session session = sessionFactory.openSession()) {
            transactionHibernate = session.beginTransaction();
            session.update(transaction);
            transactionHibernate.commit();
        } catch (Exception e) {
            if (transactionHibernate != null) {
                transactionHibernate.rollback();
            }
            e.printStackTrace(); // Or log the exception
        }
    }

    @Override
    public void delete(Long id) {
        org.hibernate.Transaction transactionHibernate = null;
        try (Session session = sessionFactory.openSession()) {
            transactionHibernate = session.beginTransaction();
            Transaction transaction = session.get(Transaction.class, id);
            if (transaction != null) {
                session.delete(transaction);
            }
            transactionHibernate.commit();
        } catch (Exception e) {
            if (transactionHibernate != null) {
                transactionHibernate.rollback();
            }
            e.printStackTrace(); // Or log the exception
        }
    }

    @Override
    public void clearTable() {
        org.hibernate.Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete from Transaction").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void createTable() {
        // Basic Hibernate logic to create table (often handled by Hibernate schema
        // export)
        System.out.println("Hibernate table creation for Transaction entity is typically handled by schema export.");
    }

    @Override
    public void dropTable() {
        // Basic Hibernate logic to drop table (often handled by Hibernate schema
        // export)
        System.out.println("Hibernate table dropping for Transaction entity is typically handled by schema export.");
    }

    @Override
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }
}