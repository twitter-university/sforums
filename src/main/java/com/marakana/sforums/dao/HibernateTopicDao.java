
package com.marakana.sforums.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.marakana.sforums.domain.Forum;
import com.marakana.sforums.domain.Topic;

public class HibernateTopicDao extends AbstractHibernateDao<Topic> implements TopicDao {

    @Transactional(readOnly = true)
    @Override
    public Topic getByForumAndTitle(Forum forum, String title) throws DataAccessException {
        return super.findOne(super.getSession().getNamedQuery("topic-by-forum-and-title")
                .setParameter("forum", forum).setParameter("title", title));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Topic> getAll() throws DataAccessException {
        return super.findAll(super.getSession().getNamedQuery("all-topics"));
    }
}
