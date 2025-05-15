package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.Currency;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class CurrencyHibernateDaoImpl extends AbstractHibernateDao implements Dao<Currency, Long> {

    @Override
    public List<Currency> findAll() {
        return executeInsideTransaction(session -> {
            String hql = "FROM Currency";
            Query<Currency> query = session.createQuery(hql, Currency.class);
            return query.list();
        });
    }

    @Override
    public void save(Currency currency) {
        executeInsideTransaction(session -> {
            session.persist(currency);
        });
    }

    @Override
    public void update(Currency currency) {
        executeInsideTransaction(session -> {
            session.merge(currency);
        });
    }

    @Override
    public void delete(Long id) {
        executeInsideTransaction(session -> {
            Currency currency = session.get(Currency.class, id);
            if (currency != null) {
                session.remove(currency);
            }
        });
    }

    @Override
    public Optional<Currency> findById(Long id) {
        return executeInsideTransaction(session -> {
            Currency currency = session.get(Currency.class, id);
            return Optional.ofNullable(currency);
        });
    }

    @Override
    public void createTable() {
        System.out.println("Currency table schema is managed by Hibernate (hbm2ddl.auto). createTable() is a no-op.");
    }

    @Override
    public void dropTable() {
        System.out.println("Currency table schema is managed by Hibernate (hbm2ddl.auto). dropTable() is a no-op.");
    }

    @Override
    public void clearTable() {
        executeInsideTransaction(session -> {
            String hql = "DELETE FROM Currency";
            session.createMutationQuery(hql).executeUpdate();
            System.out.println("Currency table cleared via Hibernate.");
        });
    }
}