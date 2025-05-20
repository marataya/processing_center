package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.MerchantCategoryCode;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class MerchantCategoryCodeHibernateDaoImpl extends AbstractHibernateDao implements Dao<MerchantCategoryCode, Long>  {

    @Override
    public List<MerchantCategoryCode> findAll() {
        return executeInsideTransaction(session -> {
            String hql = "FROM MerchantCategoryCode";
            Query<MerchantCategoryCode> query = session.createQuery(hql, MerchantCategoryCode.class);
            return query.list();
        });
    }

    @Override
    public MerchantCategoryCode save(MerchantCategoryCode mcc) {
        return executeInsideTransaction(session -> {
            session.persist(mcc);
            return mcc;
        });
    }

    @Override
    public MerchantCategoryCode update(MerchantCategoryCode mcc) {
        return executeInsideTransaction(session -> {
            return session.merge(mcc);
        });
    }

    @Override
    public void delete(Long id) {
        executeInsideTransaction(session -> {
            MerchantCategoryCode mcc = session.get(MerchantCategoryCode.class, id);
            if (mcc != null) {
                session.remove(mcc);
            }
        });
    }

    @Override
    public Optional<MerchantCategoryCode> findById(Long id) {
        return executeInsideTransaction(session -> {
            MerchantCategoryCode mcc = session.get(MerchantCategoryCode.class, id);
            return Optional.ofNullable(mcc);
        });
    }

    @Override
    public void createTable() {
        System.out.println("MerchantCategoryCode table schema is managed by Hibernate (hbm2ddl.auto). createTable() is a no-op.");
    }

    @Override
    public void dropTable() {
        System.out.println("MerchantCategoryCode table schema is managed by Hibernate (hbm2ddl.auto). dropTable() is a no-op.");
    }

    @Override
    public void clearTable() {
        executeInsideTransaction(session -> {
            String hql = "DELETE FROM MerchantCategoryCode";
            session.createMutationQuery(hql).executeUpdate();
            System.out.println("MerchantCategoryCode table cleared via Hibernate.");
        });
    }
}