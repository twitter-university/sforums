package sforums.dao;

import org.springframework.dao.DataAccessException;

import sforums.domain.Category;
import sforums.domain.Forum;

public interface ForumDao extends IdentifiableEntityDao<Forum> {
	public Forum getByCategoryAndName(Category category, String name)
			throws DataAccessException;
}