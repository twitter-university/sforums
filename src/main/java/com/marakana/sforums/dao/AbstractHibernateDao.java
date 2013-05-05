
package com.marakana.sforums.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractHibernateDao {
    private static final Logger classLogger = LoggerFactory.getLogger(AbstractHibernateDao.class);

    private static final SessionFactory sessionFactory;

    static {
        try {
            classLogger.debug("Initializing Hibernate session factory");
            Configuration configuration = new Configuration();
            configuration.configure();
            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(
                    configuration.getProperties()).buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            classLogger.debug("Initialized Hibernate session factory");
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    protected void save(Object entity) throws DataAccessException {
        try {
            Session session = this.getSession();
            try {
                session.beginTransaction();
                try {
                    session.saveOrUpdate(entity);
                    session.getTransaction().commit();
                    logger.debug("Saved {}", entity);
                } catch (RuntimeException e) {
                    session.getTransaction().rollback();
                    throw e;
                }
            } finally {
                session.close();
            }
        } catch (RuntimeException e) {
            throw new DataAccessException("Failed to save: " + entity, e);
        }
    }

    protected void delete(Object entity) throws DataAccessException {
        try {
            Session session = this.getSession();
            try {
                session.beginTransaction();
                try {
                    session.delete(entity);
                    session.getTransaction().commit();
                    logger.debug("Deleted {}", entity);
                } catch (RuntimeException e) {
                    session.getTransaction().rollback();
                    throw e;
                }
            } finally {
                session.close();
            }
        } catch (RuntimeException e) {
            throw new DataAccessException("Failed to delete: " + entity, e);
        }
    }

    protected Object getById(Class<?> clazz, Serializable id) throws DataAccessException {
        try {
            Session session = this.getSession();
            try {
                session.beginTransaction();
                try {
                    Object result = session.get(clazz, id);
                    session.getTransaction().commit();
                    logger.debug("Got {} by id {}", result, id);
                    return result;
                } catch (RuntimeException e) {
                    session.getTransaction().rollback();
                    throw e;
                }
            } finally {
                session.close();
            }
        } catch (RuntimeException e) {
            throw new DataAccessException("Failed to get by id: " + id, e);
        }
    }

    protected List<?> findAll(String hqlQuery, Object... params) throws DataAccessException {
        try {
            Session session = this.getSession();
            try {
                session.beginTransaction();
                try {
                    Query query = session.createQuery(hqlQuery);
                    this.initQueryParams(query, params);
                    List<?> result = query.list();
                    session.getTransaction().commit();
                    logger.debug("Found {} entities by query {}", +result.size(), hqlQuery);
                    return result;
                } catch (RuntimeException e) {
                    session.getTransaction().rollback();
                    throw e;
                }
            } finally {
                session.close();
            }
        } catch (RuntimeException e) {
            throw new DataAccessException("Failed to find all using query: " + hqlQuery, e);
        }
    }

    protected Object findOne(String hqlQuery, Object... params) throws DataAccessException {
        try {
            Session session = this.getSession();
            try {
                session.beginTransaction();
                try {
                    Query query = session.createQuery(hqlQuery);
                    this.initQueryParams(query, params);
                    Object result = query.uniqueResult();
                    session.getTransaction().commit();
                    logger.debug("Found {} by query {}", result, hqlQuery);
                    return result;
                } catch (RuntimeException e) {
                    session.getTransaction().rollback();
                    throw e;
                }
            } finally {
                session.close();
            }
        } catch (RuntimeException e) {
            throw new DataAccessException("Failed to find one using query: " + hqlQuery, e);
        }
    }

    private void initQueryParams(Query query, Object... params) {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
        }
    }
}
