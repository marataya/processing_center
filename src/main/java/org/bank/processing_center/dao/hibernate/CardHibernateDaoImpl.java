package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.Card;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class CardHibernateDaoImpl implements Dao<Card, Long> {

    private final SessionFactory sessionFactory;

    public CardHibernateDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void createTable() {
        // Not typically needed with Hibernate's auto-DDL features,
        // but can be implemented if manual table creation is required.
    }

    @Override
    public Optional<Card> findById(Long id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Card card = session.get(Card.class, id); // Retrieve the entity by ID
            return Optional.ofNullable(card); // Wrap the result in Optional
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return Optional.empty(); // Return empty Optional in case of error
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

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
            throw new RuntimeException("Error saving card", e);
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
            throw new RuntimeException("Error updating card", e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Card card = session.get(Card.class, id);
            if (card != null) {
                session.delete(card);
            }
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error deleting card", e);
        }
    }

    @Override
    public void dropTable() {
        // Not typically needed with Hibernate's auto-DDL features,
        // but can be implemented if manual table dropping is required.
    }

    @Override
    public void clearTable() {
        try (Session session = sessionFactory.openSession()) {
            org.hibernate.Transaction transaction = session.beginTransaction();
            String hql = "DELETE FROM Card";
            session.createQuery(hql).executeUpdate();
        }
    }

}