package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.IssuingBank;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class IssuingBankHibernateDaoImpl implements Dao<IssuingBank> {

    private final SessionFactory sessionFactory;

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
    public IssuingBank getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(IssuingBank.class, id);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return null;
        }
    }

    @Override
    public List<IssuingBank> getAll() {
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
}