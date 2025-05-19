package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.TransactionType;
import org.hibernate.query.Query;

import java.util.List;

public class TransactionTypeHibernateDaoImpl extends AbstractHibernateDao implements Dao<TransactionType, Long>
 {

    @Override
    public List<TransactionType> findAll() {
        return executeInsideTransaction(session -> {
            String hql = "FROM TransactionType";
            Query<TransactionType> query = session.createQuery(hql, TransactionType.class);
            return query.list();
        });
    }

    @Override
    public void save(TransactionType transactionType) {
        executeInsideTransaction(session -> {
            session.persist(transactionType);
        });
    }

    @Override
    public void update(TransactionType transactionType) {
        executeInsideTransaction(session -> {
            session.merge(transactionType);
        });
    }

    @Override
    public void delete(Long id) {
        executeInsideTransaction(session -> {
            TransactionType transactionType = session.get(TransactionType.class, id);
            if (transactionType != null) {
                session.remove(transactionType);
            }
        });
    }

    @Override
    public TransactionType findById(Long id) {
        return executeInsideTransaction(session -> {
            TransactionType transactionType = session.get(TransactionType.class, id);
            return transactionType;
        });
    }

    @Override
    public void createTable() {
        System.out.println("TransactionType table schema is managed by Hibernate (hbm2ddl.auto). createTable() is a no-op.");
    }

    @Override
    public void dropTable() {
        System.out.println("TransactionType table schema is managed by Hibernate (hbm2ddl.auto). dropTable() is a no-op.");
    }

    @Override
    public void clearTable() {
        executeInsideTransaction(session -> {
            String hql = "DELETE FROM TransactionType";
            session.createMutationQuery(hql).executeUpdate();
            System.out.println("TransactionType table cleared via Hibernate.");
        });
    }
}