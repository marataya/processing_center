package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.model.PaymentSystem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class PaymentSystemHibernateDaoImpl implements HibernateDao<PaymentSystem, Long> {
    
    private SessionFactory sessionFactory;

 public PaymentSystemHibernateDaoImpl(SessionFactory sessionFactory) {
 this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(PaymentSystem paymentSystem) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(paymentSystem);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Optional<PaymentSystem> findById(Long id) {
 return Optional.ofNullable(sessionFactory.openSession().get(PaymentSystem.class, id));
    }

    @Override
 public List<PaymentSystem> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM PaymentSystem", PaymentSystem.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(PaymentSystem paymentSystem) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(paymentSystem);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (sessionFactory != null && sessionFactory.getCurrentSession().getTransaction() != null) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null; // Keep declaration outside try-with-resources
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction(); // Initialize transaction inside the try block
            PaymentSystem paymentSystem = session.get(PaymentSystem.class, id);
            if (paymentSystem != null) {
                session.delete(paymentSystem);
            }
            transaction.commit(); // Commit the transaction if successful
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void createTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession();) {
            transaction = session.beginTransaction(); // Initialize transaction inside the try block
            session.getTransaction().commit();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropTable() {
        try (Session session = sessionFactory.openSession()) {
            session.createNativeQuery("DROP TABLE IF EXISTS payment_system").executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("DELETE FROM payment_system").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}