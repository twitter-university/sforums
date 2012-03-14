package sforums.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class AbstractHibernateDao extends HibernateDaoSupport {

	protected void save(Object entity) throws DataAccessException {
		super.getHibernateTemplate().saveOrUpdate(entity);
		if (logger.isDebugEnabled()) {
			logger.debug("Stored: " + entity);
		}
	}

	protected void delete(Object entity) throws DataAccessException {
		super.getHibernateTemplate().delete(entity);
		if (logger.isDebugEnabled()) {
			logger.debug("Deleted: " + entity);
		}
	}

	protected void deleteById(Class<?> clazz, Serializable id)
			throws DataAccessException {
		super.getHibernateTemplate().delete(
				super.getHibernateTemplate().load(clazz, id));
		if (logger.isDebugEnabled()) {
			logger.debug("Deleted by id: " + id);
		}
	}

	protected Object getById(Class<?> clazz, Serializable id)
			throws DataAccessException {
		Object result = super.getHibernateTemplate().get(clazz, id);
		if (logger.isDebugEnabled()) {
			logger.debug("Got " + result + " by id " + id);
		}
		return result;
	}

	protected List<?> findAll(String hqlQuery, Object... params)
			throws DataAccessException {
		List<?> result = super.getHibernateTemplate().find(hqlQuery, params);
		if (logger.isDebugEnabled()) {
			logger.debug("Found " + result.size() + " entities by query: "
					+ hqlQuery);
		}
		return result;
	}

	protected Object findOne(String hqlQuery, Object... params)
			throws DataAccessException {
		List<?> results = super.getHibernateTemplate().find(hqlQuery, params);
		if (results == null || results.isEmpty()) {
			return null;
		} else if (results.size() == 1) {
			Object result = results.get(0);
			if (logger.isDebugEnabled()) {
				logger.debug("Found " + result + " by query: " + hqlQuery);
			}
			return result;
		} else {
			throw new IllegalArgumentException(
					"More than one result returned by query: " + hqlQuery);
		}
	}
}