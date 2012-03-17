package sforums.dao;

import org.springframework.dao.DataAccessException;

import sforums.domain.Forum;
import sforums.domain.Topic;

public interface TopicDao extends IdentifiableEntityDao<Topic> {
    public Topic getByForumAndTitle(Forum forum, String title) throws DataAccessException;
}