
package com.marakana.sforums.dao;

import java.util.List;

import com.marakana.sforums.domain.Category;

public class HibernateCategoryDao extends AbstractHibernateDao implements CategoryDao {

    @Override
    public Category getById(Long id) throws DataAccessException {
        return (Category) super.getById(Category.class, id);
    }

    @Override
    public Category getByName(String name) throws DataAccessException {
        return (Category) super.findOne("from Category where name=?", name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Category> getAll() throws DataAccessException {
        return (List<Category>) super.findAll("from Category order by name");
    }

    @Override
    public void save(Category category) throws DataAccessException {
        super.save(category);
    }

    @Override
    public void delete(Category category) throws DataAccessException {
        super.delete(category);
    }
}
