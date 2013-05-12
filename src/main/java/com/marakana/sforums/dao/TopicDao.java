
package com.marakana.sforums.dao;

import org.springframework.dao.DataAccessException;

import com.marakana.sforums.domain.Forum;
import com.marakana.sforums.domain.Topic;

public interface TopicDao extends IdentifiableEntityDao<Topic> {
    public Topic getByForumAndTitle(Forum forum, String title) throws DataAccessException;
}
