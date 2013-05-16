
package com.marakana.sforums.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.marakana.sforums.domain.Category;
import com.marakana.sforums.domain.Forum;

public class HibernateForumDao extends AbstractHibernateDao<Forum> implements ForumDao {

    @Transactional(readOnly = true)
    @Override
    public Forum getByCategoryAndName(Category category, String name) throws DataAccessException {
        return super.findOne(super.getNamedQuery("forum-by-category-and-name")
                .setParameter("category", category).setParameter("name", name));
    }

    @Transactional(readOnly = true)
    @Override
    public Forum getById(Long id) throws DataAccessException {
        return super.findOne(super.getNamedQuery("forum-by-id-fetch-all").setParameter("id", id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Forum> getAll() throws DataAccessException {
        return super.findAll(super.getNamedQuery("all-forums"));
    }
}
