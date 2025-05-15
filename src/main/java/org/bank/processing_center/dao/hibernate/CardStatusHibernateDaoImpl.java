package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.CardStatus;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class CardStatusHibernateDaoImpl extends AbstractHibernateDao implements Dao<CardStatus, Long> {

    @Override
    public List<CardStatus> findAll() {
        return executeInsideTransaction(session -> {
            String hql = "FROM CardStatus";
            Query<CardStatus> query = session.createQuery(hql, CardStatus.class);
            return query.list();
        });
    }

    @Override
    public void save(CardStatus cardStatus) {
        executeInsideTransaction(session -> {
            session.persist(cardStatus);
        });
    }

    @Override
    public void update(CardStatus cardStatus) {
        executeInsideTransaction(session -> {
            session.merge(cardStatus);
        });
    }

    @Override
    public void delete(Long id) {
        executeInsideTransaction(session -> {
            CardStatus cardStatus = session.get(CardStatus.class, id);
            if (cardStatus != null) {
                session.remove(cardStatus);
            }
        });
    }

    @Override
    public Optional<CardStatus> findById(Long id) {
        return executeInsideTransaction(session -> {
            CardStatus cardStatus = session.get(CardStatus.class, id);
            return Optional.ofNullable(cardStatus);
        });
    }

    @Override
    public void createTable() {
        System.out.println("CardStatus table schema is managed by Hibernate (hbm2ddl.auto). createTable() is a no-op.");
    }

    @Override
    public void dropTable() {
        System.out.println("CardStatus table schema is managed by Hibernate (hbm2ddl.auto). dropTable() is a no-op.");
    }

    @Override
    public void clearTable() {
        executeInsideTransaction(session -> {
            String hql = "DELETE FROM CardStatus";
            session.createMutationQuery(hql).executeUpdate();
            System.out.println("CardStatus table cleared via Hibernate.");
        });
    }
}