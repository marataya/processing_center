package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.PaymentSystem;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class PaymentSystemHibernateDaoImpl extends AbstractHibernateDao implements Dao<PaymentSystem, Long> {

    @Override
    public List<PaymentSystem> findAll() {
        return executeInsideTransaction(session -> {
            String hql = "FROM PaymentSystem";
            Query<PaymentSystem> query = session.createQuery(hql, PaymentSystem.class);
            return query.list();
        });
    }

    @Override
    public void save(PaymentSystem paymentSystem) {
        executeInsideTransaction(session -> {
            session.persist(paymentSystem);
        });
    }

    @Override
    public void update(PaymentSystem paymentSystem) {
        executeInsideTransaction(session -> {
            session.merge(paymentSystem);
        });
    }

    @Override
    public void delete(Long id) {
        executeInsideTransaction(session -> {
            PaymentSystem paymentSystem = session.get(PaymentSystem.class, id);
            if (paymentSystem != null) {
                session.remove(paymentSystem);
            }
        });
    }

    @Override
    public Optional<PaymentSystem> findById(Long id) {
        return executeInsideTransaction(session -> {
            PaymentSystem paymentSystem = session.get(PaymentSystem.class, id);
            return Optional.ofNullable(paymentSystem);
        });
    }

    @Override
    public void createTable() {
        System.out.println("PaymentSystem table schema is managed by Hibernate (hbm2ddl.auto). createTable() is a no-op.");
    }

    @Override
    public void dropTable() {
        System.out.println("PaymentSystem table schema is managed by Hibernate (hbm2ddl.auto). dropTable() is a no-op.");
    }

    @Override
    public void clearTable() {
        executeInsideTransaction(session -> {
            String hql = "DELETE FROM PaymentSystem";
            session.createMutationQuery(hql).executeUpdate();
            System.out.println("PaymentSystem table cleared via Hibernate.");
        });
    }
}