package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.MerchantCategoryCode;
import org.bank.processing_center.model.SalesPoint;
import org.bank.processing_center.model.Terminal;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class TerminalHibernateDaoImpl extends AbstractHibernateDao implements Dao<Terminal, Long> {

    // No SessionFactory field or constructor needed anymore,
    // as AbstractHibernateDao gets it from HibernateConfig

    @Override
    public List<Terminal> findAll() {
        return executeInsideTransaction(session -> {
            String hql = "FROM Terminal"; // "Terminal" is the Entity name
            Query<Terminal> query = session.createQuery(hql, Terminal.class);
            return query.list();
        });
    }

    @Override
    public Terminal save(Terminal terminal) {
        return executeInsideTransaction(session -> {
            if (terminal.getMcc() != null) {
                MerchantCategoryCode managedMcc = session.merge(terminal.getMcc());
                terminal.setMcc(managedMcc);
            }
            if (terminal.getPos() != null) {
                SalesPoint managedPos = session.merge(terminal.getPos());
                terminal.setPos(managedPos);
            }
            session.persist(terminal);
            return terminal;
        });
    }

    @Override
    public Terminal update(Terminal terminal) {
        return executeInsideTransaction(session -> {
            if (terminal.getMcc() != null) {
                MerchantCategoryCode managedMcc = session.merge(terminal.getMcc());
                terminal.setMcc(managedMcc);
            }
            if (terminal.getPos() != null) {
                SalesPoint managedPos = session.merge(terminal.getPos());
                terminal.setPos(managedPos);
            }
            return session.merge(terminal); // Use merge for updates
        });
    }

    @Override
    public void delete(Long id) {
        executeInsideTransaction(session -> {
            Terminal terminal = session.get(Terminal.class, id);
            if (terminal != null) {
                session.remove(terminal);
            }
        });
    }

    @Override
    public Optional<Terminal> findById(Long id) {
        return executeInsideTransaction(session -> {
            Terminal terminal = session.get(Terminal.class, id);
            return Optional.ofNullable(terminal);
        });
    }

    @Override
    public void createTable() {
        // NO-OP: Schema is managed by hibernate.hbm2ddl.auto setting in HibernateConfig.java
        System.out.println("Terminal table schema is managed by Hibernate (hbm2ddl.auto). " +
                "TerminalHibernateDaoImpl.createTable() is a no-op.");
    }

    @Override
    public void dropTable() {
        // NO-OP: Schema is managed by hibernate.hbm2ddl.auto setting in HibernateConfig.java
        // For "create-drop", Hibernate drops tables when SessionFactory is closed.
        System.out.println("Terminal table schema is managed by Hibernate (hbm2ddl.auto). " +
                "TerminalHibernateDaoImpl.dropTable() is a no-op.");
    }

    @Override
    public void clearTable() {
        executeInsideTransaction(session -> {
            String hql = "DELETE FROM Terminal";
            session.createMutationQuery(hql).executeUpdate();
            System.out.println("Terminal table cleared via Hibernate.");
        });
    }
}