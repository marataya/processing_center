package org.bank.processing_center.dao.hibernate;

import org.bank.processing_center.dao.Dao;
import org.hibernate.SessionFactory;

public interface HibernateDao<T, ID> extends Dao<T, ID> {

    /**
     * Returns the Hibernate SessionFactory associated with this DAO.
     *
     * @return the SessionFactory
     */
    SessionFactory getSessionFactory();
}