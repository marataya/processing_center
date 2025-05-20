package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.AcquiringBank;
import org.bank.processing_center.model.SalesPoint;
import org.hibernate.query.Query;

import java.util.List;

public class SalesPointHibernateDaoImpl extends AbstractHibernateDao implements Dao<SalesPoint, Long> {

    @Override
    public List<SalesPoint> findAll() {
        return executeInsideTransaction(session -> {
            String hql = "FROM SalesPoint";
            Query<SalesPoint> query = session.createQuery(hql, SalesPoint.class);
            return query.list();
        });
    }

    @Override
    public SalesPoint save(SalesPoint salesPoint) {
        return executeInsideTransaction(session -> {
            // Option 2: Explicitly merge the associated AcquiringBank if it's detached
            // This brings it into the current session's managed state.
            if (salesPoint.getAcquiringBank() != null) {
                // If acquiringBank has an ID, it's potentially detached.
                // If it's a new AcquiringBank (ID is null), merge will behave like persist.
                // The session.merge() returns a managed instance.
                AcquiringBank managedAcquiringBank = session.merge(salesPoint.getAcquiringBank());
                salesPoint.setAcquiringBank(managedAcquiringBank); // Ensure SalesPoint references the managed instance
            }
            session.persist(salesPoint); // Now persist SalesPoint.
            return salesPoint;
        });
    }

    @Override
    public SalesPoint update(SalesPoint salesPoint) {
        return executeInsideTransaction(session -> {
            // Option 2: Explicitly merge the associated AcquiringBank if it's detached
            // This brings it into the current session's managed state.
            if (salesPoint.getAcquiringBank() != null) {
                // If acquiringBank has an ID, it's potentially detached.
                // If it's a new AcquiringBank (ID is null), merge will behave like persist.
                // The session.merge() returns a managed instance.
                AcquiringBank managedAcquiringBank = session.merge(salesPoint.getAcquiringBank());
                salesPoint.setAcquiringBank(managedAcquiringBank); // Ensure SalesPoint references the managed instance
            }
            return session.merge(salesPoint); // Now persist SalesPoint.
            // CascadeType.PERSIST on acquiringBank will be a no-op if it was merged.
            // CascadeType.MERGE would also be fine.
        });
    }

    @Override
    public void delete(Long id) {
        executeInsideTransaction(session -> {
            SalesPoint salesPoint = session.get(SalesPoint.class, id);
            if (salesPoint != null) {
                session.remove(salesPoint);
            }
        });
    }

    @Override
    public SalesPoint findById(Long id) {
        return executeInsideTransaction(session -> {
            SalesPoint salesPoint = session.get(SalesPoint.class, id);
            return salesPoint;
        });
    }

    @Override
    public void createTable() {
        System.out.println("SalesPoint table schema is managed by Hibernate (hbm2ddl.auto). createTable() is a no-op.");
    }

    @Override
    public void dropTable() {
        System.out.println("SalesPoint table schema is managed by Hibernate (hbm2ddl.auto). dropTable() is a no-op.");
    }

    @Override
    public void clearTable() {
        executeInsideTransaction(session -> {
            String hql = "DELETE FROM SalesPoint";
            session.createMutationQuery(hql).executeUpdate();
            System.out.println("SalesPoint table cleared via Hibernate.");
        });
    }
}