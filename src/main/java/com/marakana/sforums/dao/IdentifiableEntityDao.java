
package com.marakana.sforums.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.marakana.sforums.domain.IdentifiableEntity;

public interface IdentifiableEntityDao<E extends IdentifiableEntity> {
    public E getById(Long id) throws DataAccessException;

    public List<E> getAll() throws DataAccessException;

    public void save(E entity) throws DataAccessException;

    public void delete(E entity) throws DataAccessException;
}
