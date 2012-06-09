package sforums.dao;

import static sforums.Util.array;
import static sforums.Util.uniqueResult;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import sforums.domain.Category;
import sforums.domain.Forum;

public class HibernateForumDao extends AbstractHibernateDao<Forum> implements
		ForumDao {

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public Forum getById(Long id) throws DataAccessException {
		return (Forum) uniqueResult(super.getHibernateTemplate()
				.findByNamedQueryAndNamedParam("forum-by-id-fetch-all", "id",
						id));
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public Forum getByCategoryAndName(Category category, String name)
			throws DataAccessException {
		return (Forum) uniqueResult(super.getHibernateTemplate()
				.findByNamedQueryAndNamedParam("forum-by-category-and-name",
						array("category", "name"), array(category, name)));
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<Forum> getAll() throws DataAccessException {
		return super.getHibernateTemplate().findByNamedQuery("all-forums");
	}
}