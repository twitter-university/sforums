package sforums.dao;

import org.springframework.dao.DataAccessException;

import sforums.domain.Category;

public interface CategoryDao extends IdentifiableEntityDao<Category> {
	public Category getByName(String name) throws DataAccessException;
}
