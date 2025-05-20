package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.AcquiringBank;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class AcquiringBankHibernateDaoImpl extends AbstractHibernateDao implements Dao<AcquiringBank, Long> {

    @Override
    public List<AcquiringBank> findAll() {
        return executeInsideTransaction(session -> {
            String hql = "FROM AcquiringBank";
            Query<AcquiringBank> query = session.createQuery(hql, AcquiringBank.class);
            return query.list();
        });
    }

    @Override
    public AcquiringBank save(AcquiringBank acquiringBank) {
        return executeInsideTransaction(session -> {
            session.persist(acquiringBank);
            return acquiringBank;
        });
    }

    @Override
    public AcquiringBank update(AcquiringBank acquiringBank) {
        return executeInsideTransaction(session -> {
            return session.merge(acquiringBank);
        });
    }

    @Override
    public void delete(Long id) {
        executeInsideTransaction(session -> {
            AcquiringBank acquiringBank = session.get(AcquiringBank.class, id);
            if (acquiringBank != null) {
                session.remove(acquiringBank);
            }
        });
    }

    @Override
    public Optional<AcquiringBank> findById(Long id) {
        return executeInsideTransaction(session -> {
            AcquiringBank acquiringBank = session.get(AcquiringBank.class, id);
            return Optional.ofNullable(acquiringBank);
        });
    }

    @Override
    public void createTable() {
        System.out.println("AcquiringBank table schema is managed by Hibernate (hbm2ddl.auto). createTable() is a no-op.");
    }

    @Override
    public void dropTable() {
        System.out.println("AcquiringBank table schema is managed by Hibernate (hbm2ddl.auto). dropTable() is a no-op.");
    }

    @Override
    public void clearTable() {
        executeInsideTransaction(session -> {
            String hql = "DELETE FROM AcquiringBank";
            session.createMutationQuery(hql).executeUpdate();
            System.out.println("AcquiringBank table cleared via Hibernate.");
        });
    }
}