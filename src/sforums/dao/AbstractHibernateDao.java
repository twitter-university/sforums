package sforums.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sforums.domain.IdentifiableEntity;

@Repository
public abstract class AbstractHibernateDao<E extends IdentifiableEntity> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	private Class<E> domainClass = (Class<E>) ((ParameterizedType) getClass()
			.getGenericSuperclass()).getActualTypeArguments()[0];

	private SessionFactory sessionFactory;

	public void setDomainClass(Class<E> domainClass) {
		this.domainClass = domainClass;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Transactional(readOnly = false)
	public void save(E entity) throws DataAccessException {
		try {
			this.getSession().saveOrUpdate(entity);
			this.getSession().flush();
			if (logger.isDebugEnabled()) {
				logger.debug("Stored: " + entity);
			}
		} catch (ConstraintViolationException e) {
			throw new DataIntegrityViolationException(
					"Data integrity violation while saving " + entity, e);
		}
	}

	@Transactional(readOnly = false)
	public void delete(E entity) throws DataAccessException {
		this.getSession().delete(entity);
		if (logger.isDebugEnabled()) {
			logger.debug("Deleted: " + entity);
		}
	}

	@Transactional(readOnly = false)
	public void deleteById(Long id) throws DataAccessException {
		Session session = this.getSession();
		session.delete(session.load(this.domainClass, id));
		if (logger.isDebugEnabled()) {
			logger.debug("Deleted by id: " + id);
		}
	}

	@SuppressWarnings("unchecked")
	public E getById(Long id) throws DataAccessException {
		Object result = this.getSession().get(this.domainClass, id);
		if (logger.isDebugEnabled()) {
			logger.debug("Got " + result + " by id " + id);
		}
		return (E) result;
	}

	@SuppressWarnings("unchecked")
	protected List<E> findAll(Query query) throws DataAccessException {
		List<?> result = query.list();
		if (logger.isDebugEnabled()) {
			logger.debug("Found " + result.size() + " entities by query: "
					+ query.getQueryString());
		}
		return (List<E>) result;
	}

	protected List<E> findAll(String hqlQuery, Object... params)
			throws DataAccessException {
		return this.findAll(this.buildQuery(hqlQuery, params));
	}

	@SuppressWarnings("unchecked")
	protected E findOne(Query query) throws DataAccessException {
		Object result = query.uniqueResult();
		if (logger.isDebugEnabled()) {
			logger.debug("Found " + result + " by query: "
					+ query.getQueryString());
		}
		return (E) result;
	}

	protected E findOne(String hqlQuery, Object... params)
			throws DataAccessException {
		return this.findOne(this.buildQuery(hqlQuery, params));
	}

	protected Query buildQuery(String hql, Object... params) {
		return this.initParameters(this.getSession().createQuery(hql), params);
	}

	private Query initParameters(Query query, Object... params) {
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query;
	}
}