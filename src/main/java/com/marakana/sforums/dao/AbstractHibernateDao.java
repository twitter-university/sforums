
package com.marakana.sforums.dao;

import java.lang.reflect.ParameterizedType;
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
import org.springframework.transaction.annotation.Transactional;

import com.marakana.sforums.domain.IdentifiableEntity;

@Repository
public abstract class AbstractHibernateDao<E extends IdentifiableEntity> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @SuppressWarnings("unchecked")
    private final Class<E> domainClass = (Class<E>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() throws HibernateException {
        return this.sessionFactory.getCurrentSession();
    }

    @Transactional(readOnly = false)
    public void save(E entity) throws DataAccessException {
        this.logger.trace("Saving {}", entity);
        Session session = this.getSession();
        session.saveOrUpdate(entity);
        session.flush(); // force insert/update
        this.logger.debug("Saved {}", entity);
    }

    @Transactional(readOnly = false)
    public void delete(E entity) throws DataAccessException {
        Session session = this.getSession();
        session.delete(entity);
        session.flush(); // force delete
        this.logger.debug("Deleted {}", entity);
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public E getById(Long id) throws DataAccessException {
        Object result = this.getSession().get(this.domainClass, id);
        this.logger.debug("Got {} by id {}", result, id);
        return (E) result;
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<E> getAll() {
        // this default implementation does not support any special sorting
        return this.getSession().createCriteria(this.domainClass).list();
    }

    @SuppressWarnings("unchecked")
    protected List<E> findAll(String hqlQuery, Object... params) throws DataAccessException {
        Query query = this.getSession().createQuery(hqlQuery);
        this.initQueryParams(query, params);
        List<?> result = query.list();
        this.logger.debug("Found {} entities by query {}", +result.size(), hqlQuery);
        return (List<E>) result;
    }

    @SuppressWarnings("unchecked")
    protected E findOne(String hqlQuery, Object... params) throws DataAccessException {
        Query query = this.getSession().createQuery(hqlQuery);
        this.initQueryParams(query, params);
        Object result = query.uniqueResult();
        this.logger.debug("Found {} by query {}", result, hqlQuery);
        return (E) result;
    }

    private void initQueryParams(Query query, Object... params) {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
        }
    }
}
