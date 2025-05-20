package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.IssuingBank;
import org.hibernate.query.Query;

import java.util.List;

public class IssuingBankHibernateDaoImpl extends AbstractHibernateDao implements Dao<IssuingBank, Long> {

    @Override
    public List<IssuingBank> findAll() {
        return executeInsideTransaction(session -> {
            String hql = "FROM IssuingBank";
            Query<IssuingBank> query = session.createQuery(hql, IssuingBank.class);
            return query.list();
        });
    }

    @Override
    public IssuingBank save(IssuingBank issuingBank) {
        return executeInsideTransaction(session -> {
            session.persist(issuingBank);
            return issuingBank;
        });
    }

    @Override
    public IssuingBank update(IssuingBank issuingBank) {
        return executeInsideTransaction(session -> {
            return session.merge(issuingBank);
        });
    }

    @Override
    public void delete(Long id) {
        executeInsideTransaction(session -> {
            IssuingBank issuingBank = session.get(IssuingBank.class, id);
            if (issuingBank != null) {
                session.remove(issuingBank);
            }
        });
    }

    @Override
    public IssuingBank findById(Long id) {
        return executeInsideTransaction(session -> {
            IssuingBank issuingBank = session.get(IssuingBank.class, id);
            return issuingBank;
        });
    }

    @Override
    public void createTable() {
        System.out.println("IssuingBank table schema is managed by Hibernate (hbm2ddl.auto). createTable() is a no-op.");
    }

    @Override
    public void dropTable() {
        System.out.println("IssuingBank table schema is managed by Hibernate (hbm2ddl.auto). dropTable() is a no-op.");
    }

    @Override
    public void clearTable() {
        executeInsideTransaction(session -> {
            String hql = "DELETE FROM IssuingBank";
            session.createMutationQuery(hql).executeUpdate();
            System.out.println("IssuingBank table cleared via Hibernate.");
        });
    }
}