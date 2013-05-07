
package com.marakana.sforums.dao;

import org.springframework.dao.DataAccessException;

import com.marakana.sforums.domain.Category;

public interface CategoryDao extends IdentifiableEntityDao<Category> {
    public Category getByName(String name) throws DataAccessException;
}
