package sforums.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import sforums.domain.IdentifiableEntity;

public abstract class AbstractHibernateDao<E extends IdentifiableEntity>
		extends HibernateDaoSupport {

	@SuppressWarnings("unchecked")
	private Class<E> domainClass = (Class<E>) ((ParameterizedType) getClass()
			.getGenericSuperclass()).getActualTypeArguments()[0];

	protected void save(E entity) throws DataAccessException {
		try {
			super.getHibernateTemplate().saveOrUpdate(entity);
			super.getHibernateTemplate().flush();
			if (logger.isDebugEnabled()) {
				logger.debug("Stored: " + entity);
			}
		} catch (HibernateOptimisticLockingFailureException e) {

		}
	}

	protected void delete(E entity) throws DataAccessException {
		super.getHibernateTemplate().delete(entity);
		if (logger.isDebugEnabled()) {
			logger.debug("Deleted: " + entity);
		}
	}

	public void deleteById(Long id) throws DataAccessException {
		super.getHibernateTemplate().delete(
				super.getHibernateTemplate().load(this.domainClass, id));
		if (logger.isDebugEnabled()) {
			logger.debug("Deleted by id: " + id);
		}
	}

	@SuppressWarnings("unchecked")
	public E getById(Long id) throws DataAccessException {
		Object result = super.getHibernateTemplate().get(this.domainClass, id);
		if (logger.isDebugEnabled()) {
			logger.debug("Got " + result + " by id " + id);
		}
		return (E) result;
	}

	@SuppressWarnings("unchecked")
	protected List<E> findAll(String hqlQuery, Object... params)
			throws DataAccessException {
		List<?> result = super.getHibernateTemplate().find(hqlQuery, params);
		if (logger.isDebugEnabled()) {
			logger.debug("Found " + result.size() + " entities by query: "
					+ hqlQuery);
		}
		return (List<E>) result;
	}

	@SuppressWarnings("unchecked")
	protected E findOne(String hqlQuery, Object... params)
			throws DataAccessException {
		List<?> results = super.getHibernateTemplate().find(hqlQuery, params);
		if (results == null || results.isEmpty()) {
			return null;
		} else if (results.size() == 1) {
			Object result = results.get(0);
			if (logger.isDebugEnabled()) {
				logger.debug("Found " + result + " by query: " + hqlQuery);
			}
			return (E) result;
		} else {
			throw new IllegalArgumentException(
					"More than one result returned by query: " + hqlQuery);
		}
	}
}