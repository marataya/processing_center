package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.*;
import org.hibernate.query.Query;

import java.util.List;

public class TransactionHibernateDaoImpl extends AbstractHibernateDao implements Dao<Transaction, Long>
 {

    @Override
    public List<Transaction> findAll() {
        return executeInsideTransaction(session -> {
            String hql = "FROM Transaction";
            Query<Transaction> query = session.createQuery(hql, Transaction.class);
            return query.list();
        });
    }

    @Override
    public void save(Transaction transaction) {
        executeInsideTransaction(session -> {
            if (transaction.getAccount() != null) {
                Account managedAccount = session.merge(transaction.getAccount());
                transaction.setAccount(managedAccount);
            }
            if (transaction.getTransactionType() != null) {
                TransactionType managedTransactionType = session.merge(transaction.getTransactionType());
                transaction.setTransactionType(managedTransactionType);
            }
            if (transaction.getCard() != null) {
                Card managedCard = session.merge(transaction.getCard());
                transaction.setCard(managedCard);
            }
            if (transaction.getTerminal() != null) {
                Terminal managedTerminal = session.merge(transaction.getTerminal());
                transaction.setTerminal(managedTerminal);
            }
            if (transaction.getResponseCode() != null) {
                ResponseCode managedResponseCode = session.merge(transaction.getResponseCode());
                transaction.setResponseCode(managedResponseCode);
            }

            session.persist(transaction);
        });
    }

    @Override
    public void update(Transaction transaction) {
        executeInsideTransaction(session -> {
            if (transaction.getAccount() != null) {
                Account managedAccount = session.merge(transaction.getAccount());
                transaction.setAccount(managedAccount);
            }
            if (transaction.getTransactionType() != null) {
                TransactionType managedTransactionType = session.merge(transaction.getTransactionType());
                transaction.setTransactionType(managedTransactionType);
            }
            if (transaction.getCard() != null) {
                Card managedCard = session.merge(transaction.getCard());
                transaction.setCard(managedCard);
            }
            if (transaction.getTerminal() != null) {
                Terminal managedTerminal = session.merge(transaction.getTerminal());
                transaction.setTerminal(managedTerminal);
            }
            if (transaction.getResponseCode() != null) {
                ResponseCode managedResponseCode = session.merge(transaction.getResponseCode());
                transaction.setResponseCode(managedResponseCode);
            }

            session.merge(transaction);
        });
    }

    @Override
    public void delete(Long id) {
        executeInsideTransaction(session -> {
            Transaction transaction = session.get(Transaction.class, id);
            if (transaction != null) {
                session.remove(transaction);
            }
        });
    }

    @Override
    public Transaction findById(Long id) {
        return executeInsideTransaction(session -> {
            Transaction transaction = session.get(Transaction.class, id);
            return transaction;
        });
    }

    @Override
    public void createTable() {
        System.out.println("Transaction table schema is managed by Hibernate (hbm2ddl.auto). createTable() is a no-op.");
    }

    @Override
    public void dropTable() {
        System.out.println("Transaction table schema is managed by Hibernate (hbm2ddl.auto). dropTable() is a no-op.");
    }

    @Override
    public void clearTable() {
        executeInsideTransaction(session -> {
            String hql = "DELETE FROM Transaction";
            session.createMutationQuery(hql).executeUpdate();
            System.out.println("Transaction table cleared via Hibernate.");
        });
    }
}