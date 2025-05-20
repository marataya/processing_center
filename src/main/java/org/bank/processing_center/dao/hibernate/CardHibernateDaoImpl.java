package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.Card;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class CardHibernateDaoImpl extends AbstractHibernateDao implements Dao<Card, Long> {

    @Override
    public List<Card> findAll() {
        return executeInsideTransaction(session -> {
            String hql = "FROM Card";
            Query<Card> query = session.createQuery(hql, Card.class);
            return query.list();
        });
    }

    @Override
    public Card save(Card card) {
        return executeInsideTransaction(session -> {
            session.persist(card);
            return card;
        });
    }

    @Override
    public Card update(Card card) {
        return executeInsideTransaction(session -> {
            return session.merge(card);
        });
    }

    @Override
    public void delete(Long id) {
        executeInsideTransaction(session -> {
            Card card = session.get(Card.class, id);
            if (card != null) {
                session.remove(card);
            }
        });
    }

    @Override
    public Optional<Card> findById(Long id) {
        return executeInsideTransaction(session -> {
            Card card = session.get(Card.class, id);
            return Optional.ofNullable(card);
        });
    }

    @Override
    public void createTable() {
        System.out.println("Card table schema is managed by Hibernate (hbm2ddl.auto). createTable() is a no-op.");
    }

    @Override
    public void dropTable() {
        System.out.println("Card table schema is managed by Hibernate (hbm2ddl.auto). dropTable() is a no-op.");
    }

    @Override
    public void clearTable() {
        executeInsideTransaction(session -> {
            String hql = "DELETE FROM Card";
            session.createMutationQuery(hql).executeUpdate();
            System.out.println("Card table cleared via Hibernate.");
        });
    }
}