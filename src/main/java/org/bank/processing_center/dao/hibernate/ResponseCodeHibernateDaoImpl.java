package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.ResponseCode;
import org.hibernate.query.Query;

import java.util.List;

public class ResponseCodeHibernateDaoImpl extends AbstractHibernateDao implements Dao<ResponseCode, Long>
 {

    @Override
    public List<ResponseCode> findAll() {
        return executeInsideTransaction(session -> {
            String hql = "FROM ResponseCode";
            Query<ResponseCode> query = session.createQuery(hql, ResponseCode.class);
            return query.list();
        });
    }

    @Override
    public void save(ResponseCode responseCode) {
        executeInsideTransaction(session -> {
            session.persist(responseCode);
        });

    }

    @Override
    public void update(ResponseCode responseCode) {
        executeInsideTransaction(session -> {
            session.merge(responseCode);
        });
    }

    @Override
    public void delete(Long id) {
        executeInsideTransaction(session -> {
            ResponseCode responseCode = session.get(ResponseCode.class, id);
            if (responseCode != null) {
                session.remove(responseCode);
            }
        });
    }

    @Override
    public ResponseCode findById(Long id) {
        return executeInsideTransaction(session -> {
            ResponseCode responseCode = session.get(ResponseCode.class, id);
            return responseCode;
        });
    }

    @Override
    public void createTable() {
        System.out.println("ResponseCode table schema is managed by Hibernate (hbm2ddl.auto). createTable() is a no-op.");
    }

    @Override
    public void dropTable() {
        System.out.println("ResponseCode table schema is managed by Hibernate (hbm2ddl.auto). dropTable() is a no-op.");
    }

    @Override
    public void clearTable() {
        executeInsideTransaction(session -> {
            String hql = "DELETE FROM ResponseCode";
            session.createMutationQuery(hql).executeUpdate();
            System.out.println("ResponseCode table cleared via Hibernate.");
        });
    }
}