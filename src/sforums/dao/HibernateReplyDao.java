package sforums.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import sforums.domain.Reply;

public class HibernateReplyDao extends AbstractHibernateDao<Reply> implements
		ReplyDao {

	@Transactional(readOnly = true)
	@Override
	public List<Reply> getAll() throws DataAccessException {
		return super.findAll("from Reply order by created");
	}

	@Transactional(readOnly = false)
	@Override
	public void save(Reply reply) throws DataAccessException {
		reply.markCreated();
		super.save(reply);
	}
}
