package sforums.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import sforums.domain.IdentifiableEntity;

public interface IdentifiableEntityDao<E extends IdentifiableEntity> {
	public E getById(Long id) throws DataAccessException;

	public List<E> getAll() throws DataAccessException;

	public void save(E e) throws DataAccessException;

	public void delete(E e) throws DataAccessException;

	public void deleteById(Long id) throws DataAccessException;
}
