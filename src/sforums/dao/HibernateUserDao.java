package sforums.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import sforums.domain.User;

@Transactional(readOnly = true)
public class HibernateUserDao extends AbstractHibernateDao<User> implements
		UserDao {

	@Override
	public User getByEmail(String email) throws DataAccessException {
		return super.findOne("from User where email=?", email);
	}

	@Override
	public List<User> getAll() throws DataAccessException {
		return super.findAll("from User order by firstName, lastName");
	}

	@Transactional(readOnly = false)
	@Override
	public void save(User user) throws DataAccessException {
		user.markCreated();
		super.save(user);
	}

	@Transactional(readOnly = false)
	@Override
	public void delete(User user) throws DataAccessException {
		super.delete(user);
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteById(Long id) throws DataAccessException {
		super.deleteById(id);
	}
}
