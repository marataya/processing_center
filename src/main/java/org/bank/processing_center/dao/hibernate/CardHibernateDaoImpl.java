package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.Card;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class CardHibernateDaoImpl implements Dao<Card> {

    private final SessionFactory sessionFactory;

    public CardHibernateDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Card findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Card.class, id);
        }
    }

    @Override
    public List<Card> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Card", Card.class).list();
        }
    }

    @Override
    public void save(Card card) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(card);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Log the exception
        }
    }

    @Override
    public void update(Card card) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(card);
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
            Card card = session.get(Card.class, id);
            if (card != null) {
                session.delete(card);
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