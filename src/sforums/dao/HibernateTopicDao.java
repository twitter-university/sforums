package sforums.dao;

import static sforums.Util.array;
import static sforums.Util.uniqueResult;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import sforums.domain.Forum;
import sforums.domain.Topic;

@Transactional(readOnly = true)
public class HibernateTopicDao extends AbstractHibernateDao<Topic> implements
        TopicDao {

    @SuppressWarnings("unchecked")
    @Override
    public Topic getByForumAndTitle(Forum forum, String title)
            throws DataAccessException {
        return (Topic) uniqueResult(super.getHibernateTemplate()
                .findByNamedQueryAndNamedParam("topic-by-forum-and-title",
                        array("forum", "title"), array(forum, title)));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Topic> getAll() throws DataAccessException {
        return super.getHibernateTemplate().findByNamedQuery("all-topics");
    }

    @Transactional(readOnly = false)
    @Override
    public void save(Topic topic) throws DataAccessException {
        topic.markCreated();
        super.save(topic);
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(Topic Topic) throws DataAccessException {
        super.delete(Topic);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteById(Long id) throws DataAccessException {
        super.deleteById(id);
    }
}