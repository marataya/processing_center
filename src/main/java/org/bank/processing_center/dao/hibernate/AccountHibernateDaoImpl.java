package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.model.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;
// Implement Dao interface
public class AccountHibernateDaoImpl implements HibernateDao<Account, Long> {

    private final SessionFactory sessionFactory;

    public AccountHibernateDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public Optional<Account> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Account.class, id));
        }
    }
    
    @Override
    public List<Account> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Account", Account.class).list();
        }
    }

    @Override
    public void save(Account account) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(account);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void update(Account account) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(account);
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
        Transaction transaction = null; // Declare outside try-with-resources
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Account account = session.get(Account.class, id);
            if (account != null) {
                session.delete(account);
            }
            transaction.commit(); // Commit here if deletion is successful
        } catch (Exception e) {
            // Rollback in case of exception

            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void createTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE account (id BIGINT PRIMARY KEY, account_number VARCHAR(50), balance DECIMAL(19, 2), currency_id BIGINT, issuing_bank_id BIGINT, FOREIGN KEY (currency_id) REFERENCES currency(id), FOREIGN KEY (issuing_bank_id) REFERENCES issuing_bank(id))").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void dropTable() {
        try (Session session = sessionFactory.openSession()) {
            session.createNativeQuery("DROP TABLE IF EXISTS account").executeUpdate();
        }
    }

    @Override
    public void clearTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("DELETE FROM account").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}