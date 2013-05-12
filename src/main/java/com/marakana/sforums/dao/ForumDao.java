package com.marakana.sforums.dao;

import org.springframework.dao.DataAccessException;

import com.marakana.sforums.domain.Category;
import com.marakana.sforums.domain.Forum;

public interface ForumDao extends IdentifiableEntityDao<Forum> {
    public Forum getByCategoryAndName(Category category, String name) throws DataAccessException;
}