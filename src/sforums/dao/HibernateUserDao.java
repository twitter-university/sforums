package sforums.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import sforums.domain.User;

public class HibernateUserDao extends AbstractHibernateDao<User> implements
		UserDao {

	@Transactional(readOnly = true)
	@Override
	public User getByEmail(String email) throws DataAccessException {
		return super.findOne("from User where email=?", email);
	}

	@Transactional(readOnly = true)
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
}
