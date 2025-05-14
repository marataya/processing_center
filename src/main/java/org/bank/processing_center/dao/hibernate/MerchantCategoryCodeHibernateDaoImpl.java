package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.configuration.HibernateConfig;
import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.MerchantCategoryCode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class MerchantCategoryCodeHibernateDaoImpl implements Dao<MerchantCategoryCode, Long> {

    private final SessionFactory sessionFactory;

    public MerchantCategoryCodeHibernateDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void createTable() {
        // In Hibernate, table creation is typically handled by the Hibernate
        // configuration (e.g., hbm2ddl.auto)
        // This method can be left empty or used for any manual schema setup if needed.
        System.out.println("Table creation managed by Hibernate.");
    }

    @Override
    public void dropTable() {
        // In Hibernate, table dropping is typically handled by the Hibernate
        // configuration (e.g., hbm2ddl.auto)
        // This method can be left empty or used for any manual schema cleanup if
        // needed.
        System.out.println("Table dropping managed by Hibernate.");
    }

    @Override
    public void clearTable() {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM MerchantCategoryCode";
            Query query = session.createQuery(hql);
            query.executeUpdate();
            transaction.commit();
            System.out.println("MerchantCategoryCode table cleared.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error clearing MerchantCategoryCode table: " + e.getMessage());
        }
    }

    @Override
    public void save(MerchantCategoryCode merchantCategoryCode) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(merchantCategoryCode);
            transaction.commit();
            System.out.println("MerchantCategoryCode saved: " + merchantCategoryCode);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error saving MerchantCategoryCode: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            MerchantCategoryCode merchantCategoryCode = session.get(MerchantCategoryCode.class, id);
            if (merchantCategoryCode != null) {
                session.delete(merchantCategoryCode);
                transaction.commit();
                System.out.println("MerchantCategoryCode with id " + id + " deleted.");
            } else {
                transaction.rollback();
                System.out.println("MerchantCategoryCode with id " + id + " not found.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error deleting MerchantCategoryCode: " + e.getMessage());
        }
    }

    @Override
    public List<MerchantCategoryCode> findAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("FROM MerchantCategoryCode", MerchantCategoryCode.class).list();
        } catch (Exception e) {
            System.err.println("Error finding all MerchantCategoryCodes: " + e.getMessage());
            return List.of(); // Return empty list in case of error
        }
    }

    @Override
    public Optional<MerchantCategoryCode> findById(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            MerchantCategoryCode merchantCategoryCode = session.get(MerchantCategoryCode.class, id);
            return Optional.ofNullable(merchantCategoryCode);
        } catch (Exception e) {
            System.err.println("Error finding MerchantCategoryCode by id: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void update(MerchantCategoryCode merchantCategoryCode) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(merchantCategoryCode);
            transaction.commit();
            System.out.println("MerchantCategoryCode updated: " + merchantCategoryCode);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error updating MerchantCategoryCode: " + e.getMessage());
        }
    }
}