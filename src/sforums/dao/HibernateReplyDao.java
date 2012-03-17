package sforums.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import sforums.domain.Reply;

@Transactional(readOnly = true)
public class HibernateReplyDao extends AbstractHibernateDao<Reply> implements
		ReplyDao {
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

	@Transactional(readOnly = false)
	@Override
	public void delete(Reply reply) throws DataAccessException {
		super.delete(reply);
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteById(Long id) throws DataAccessException {
		super.deleteById(id);
	}
}
