
package com.marakana.sforums.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AbstractHibernateDao {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    protected void save(Object entity) throws DataAccessException {
        this.getSession().saveOrUpdate(entity);
        this.logger.debug("Saved {}", entity);
    }

    protected void delete(Object entity) throws DataAccessException {
        this.getSession().delete(entity);
        this.logger.debug("Deleted {}", entity);
    }

    protected Object getById(Class<?> clazz, Serializable id) throws DataAccessException {
        Object result = this.getSession().get(clazz, id);
        this.logger.debug("Got {} by id {}", result, id);
        return result;
    }

    protected List<?> findAll(String hqlQuery, Object... params) throws DataAccessException {
        Query query = this.getSession().createQuery(hqlQuery);
        this.initQueryParams(query, params);
        List<?> result = query.list();
        this.logger.debug("Found {} entities by query {}", +result.size(), hqlQuery);
        return result;
    }

    protected Object findOne(String hqlQuery, Object... params) throws DataAccessException {
        Query query = this.getSession().createQuery(hqlQuery);
        this.initQueryParams(query, params);
        Object result = query.uniqueResult();
        this.logger.debug("Found {} by query {}", result, hqlQuery);
        return result;
    }

    private void initQueryParams(Query query, Object... params) {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
        }
    }
}
