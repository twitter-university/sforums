package sforums.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import sforums.domain.Category;

@Transactional(readOnly = true)
public class HibernateCategoryDao extends AbstractHibernateDao<Category>
		implements CategoryDao {

	@Override
	public Category getByName(String name) throws DataAccessException {
		return super.findOne("from Category where name=?", name);
	}

	@Override
	public List<Category> getAll() throws DataAccessException {
		return super.findAll("from Category order by name");
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
		super.deleteById(id);
	}
}
