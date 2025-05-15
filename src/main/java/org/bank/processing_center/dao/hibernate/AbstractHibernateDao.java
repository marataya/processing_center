package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.configuration.HibernateConfig;
import org.bank.processing_center.dao.exception.DaoException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractHibernateDao {

    protected <R> R executeInsideTransaction(Function<Session, R> function) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            // Log the original exception BEFORE attempting rollback or wrapping
            System.err.println("Original exception caught in AbstractHibernateDao.executeInsideTransaction (before rollback attempt):");
            e.printStackTrace(); // THIS IS THE MOST IMPORTANT LINE TO ADD/UNCOMMENT
            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (Exception rbEx) {
                    System.err.println("Exception during transaction rollback: " + rbEx.getMessage());
                    // Optionally, chain rbEx to the DaoException or log it more prominently
                    // e.g., e.addSuppressed(rbEx);
                }
            }
            throw new DaoException("Error during Hibernate transaction", e);
        }
    }

    protected void executeInsideTransaction(Consumer<Session> consumer) {
        executeInsideTransaction(session -> {
            consumer.accept(session);
            return null; // Return null for void operations
        });
    }
}