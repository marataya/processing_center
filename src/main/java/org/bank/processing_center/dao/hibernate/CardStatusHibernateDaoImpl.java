package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.CardStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class CardStatusHibernateDaoImpl implements Dao<CardStatus> {

    private SessionFactory sessionFactory;

    public CardStatusHibernateDaoImpl() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    @Override
    public CardStatus getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(CardStatus.class, id);
        }
    }

    @Override
    public List<CardStatus> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM CardStatus", CardStatus.class).list();
        }
    }

    @Override
    public void save(CardStatus cardStatus) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(cardStatus);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    @Override
    public void update(CardStatus cardStatus) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(cardStatus);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CardStatus cardStatus = session.get(CardStatus.class, id);
            if (cardStatus != null) {
                session.delete(cardStatus);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }
}