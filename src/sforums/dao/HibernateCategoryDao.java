package sforums.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import sforums.domain.Category;

@Transactional(readOnly = true)
public class HibernateCategoryDao extends AbstractHibernateDao implements
		CategoryDao {

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

	@Transactional(readOnly = false)
	@Override
	public void save(Category category) throws DataAccessException {
		super.save(category);
	}

	@Transactional(readOnly = false)
	@Override
	public void delete(Category category) throws DataAccessException {
		super.delete(category);
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteById(Long id) throws DataAccessException {
		super.deleteById(Category.class, id);
	}

}
