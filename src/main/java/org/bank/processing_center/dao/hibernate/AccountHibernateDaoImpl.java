package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.Account;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class AccountHibernateDaoImpl extends AbstractHibernateDao implements Dao<Account, Long> {

    @Override
    public List<Account> findAll() {
        return executeInsideTransaction(session -> {
            String hql = "FROM Account";
            Query<Account> query = session.createQuery(hql, Account.class);
            return query.list();
        });
    }

    @Override
    public Account save(Account account) {
        return executeInsideTransaction(session -> {
            session.persist(account);
            return account;
        });
    }

    @Override
    public Account update(Account account) {
        return executeInsideTransaction(session -> {
            return session.merge(account);
        });
    }

    @Override
    public void delete(Long id) {
        executeInsideTransaction(session -> {
            Account account = session.get(Account.class, id);
            if (account != null) {
                session.remove(account);
            }
        });
    }

    @Override
    public Optional<Account> findById(Long id) {
        return executeInsideTransaction(session -> {
            Account account = session.get(Account.class, id);
            return Optional.ofNullable(account);
        });
    }

    @Override
    public void createTable() {
        System.out.println("Account table schema is managed by Hibernate (hbm2ddl.auto). createTable() is a no-op.");
    }

    @Override
    public void dropTable() {
        System.out.println("Account table schema is managed by Hibernate (hbm2ddl.auto). dropTable() is a no-op.");
    }

    @Override
    public void clearTable() {
        executeInsideTransaction(session -> {
            String hql = "DELETE FROM Account";
            session.createMutationQuery(hql).executeUpdate();
            System.out.println("Account table cleared via Hibernate.");
        });
    }
}