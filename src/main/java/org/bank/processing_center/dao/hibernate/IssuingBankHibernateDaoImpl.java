package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.IssuingBank;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class IssuingBankHibernateDaoImpl implements Dao<IssuingBank, Long>, HibernateDao<IssuingBank, Long> {

 private SessionFactory sessionFactory;

    public IssuingBankHibernateDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
 }

    @Override
    public void save(IssuingBank issuingBank) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(issuingBank);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Log the exception
        }
    }

    @Override
    public Optional<IssuingBank> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(IssuingBank.class, id));
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return Optional.empty();
        }
    }

    @Override
    public List<IssuingBank> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM IssuingBank", IssuingBank.class).list();
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return null;
        }
    }

    @Override
    public void update(IssuingBank issuingBank) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(issuingBank);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Log the exception
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            IssuingBank issuingBank = session.get(IssuingBank.class, id);
            if (issuingBank != null) {
                session.delete(issuingBank);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Log the exception
        }
    }

    @Override
    public void createTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(
                    "CREATE TABLE IF NOT EXISTS issuing_bank (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255))")
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Log the exception
        }
    }

    @Override
    public void dropTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS issuing_bank").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

 @Override
 public void clearTable() {
 Transaction transaction = null;
 try (Session session = sessionFactory.openSession()) {
 transaction = session.beginTransaction();
            session.createNativeQuery("DELETE FROM issuing_bank").executeUpdate();
 transaction.commit();
 } catch (Exception e) {
 if (transaction != null) {
 transaction.rollback();
 }
 e.printStackTrace(); // Log the exception
 }
 }

 @Override
 public SessionFactory getSessionFactory() {
 return sessionFactory;
 }

}