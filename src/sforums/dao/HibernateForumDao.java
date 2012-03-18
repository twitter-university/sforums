package sforums.dao;

import static sforums.Util.array;
import static sforums.Util.uniqueResult;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import sforums.domain.Category;
import sforums.domain.Forum;

@Transactional(readOnly = true)
public class HibernateForumDao extends AbstractHibernateDao<Forum> implements
		ForumDao {

	@SuppressWarnings("unchecked")
	@Override
	public Forum getById(Long id) throws DataAccessException {
		return (Forum) uniqueResult(super.getHibernateTemplate()
				.findByNamedQueryAndNamedParam("forum-by-id-fetch-all", "id",
						id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Forum getByCategoryAndName(Category category, String name)
			throws DataAccessException {
		return (Forum) uniqueResult(super.getHibernateTemplate()
				.findByNamedQueryAndNamedParam("forum-by-category-and-name",
						array("category", "name"), array(category, name)));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Forum> getAll() throws DataAccessException {
		return super.getHibernateTemplate().findByNamedQuery("all-forums");
	}

	@Transactional(readOnly = false)
	@Override
	public void save(Forum forum) throws DataAccessException {
		super.save(forum);
	}

	@Transactional(readOnly = false)
	@Override
	public void delete(Forum forum) throws DataAccessException {
		super.delete(forum);
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteById(Long id) throws DataAccessException {
		super.deleteById(id);
	}
}