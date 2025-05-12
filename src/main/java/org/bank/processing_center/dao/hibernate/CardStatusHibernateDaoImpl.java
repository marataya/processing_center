package org.bank.processing_center.dao.hibernate;

import java.util.List;
import java.util.Optional;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.CardStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CardStatusHibernateDaoImpl implements Dao<CardStatus, Long> {

    private SessionFactory sessionFactory = null;

    public CardStatusHibernateDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public CardStatusHibernateDaoImpl() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    @Override
    public void createTable() {
        // Table creation is typically handled by Hibernate configuration
    }

    @Override
    public Optional<CardStatus> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(CardStatus.class, id));
        }
    }

    @Override
    public List<CardStatus> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM CardStatus", CardStatus.class).list();
        }
    }

    @Override
    public void save(CardStatus cardStatus) {
        org.hibernate.Transaction transaction = null;
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
        org.hibernate.Transaction transaction = null;
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
        org.hibernate.Transaction transaction = null;
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

    @Override
    public void clearTable() {
        org.hibernate.Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("DELETE FROM card_status").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus() == org.hibernate.resource.transaction.spi.TransactionStatus.ACTIVE) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropTable() {
        org.hibernate.Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS card_status").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus() == org.hibernate.resource.transaction.spi.TransactionStatus.ACTIVE) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}