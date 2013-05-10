
package com.marakana.sforums.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import com.marakana.sforums.domain.Category;

public class HibernateCategoryDao extends AbstractHibernateDao<Category> implements CategoryDao {

    @Transactional(readOnly = true)
    @Override
    public List<Category> getAll() throws DataAccessException {
        return super.findAll("from Category order by name");
    }

    @Transactional(readOnly = true)
    @Override
    public Category getByName(String name) throws DataAccessException {
        return super.findOne("from Category where name=?", name);
    }

    @Secured("ROLE_ADMIN")
    @Override
    @Transactional(readOnly = false)
    public void save(Category entity) throws DataAccessException {
        super.save(entity);
    }

    @Secured("ROLE_ADMIN")
    @Override
    @Transactional(readOnly = false)
    public void delete(Category entity) throws DataAccessException {
        super.delete(entity);
    }
}