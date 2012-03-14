package sforums.dao;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import sforums.domain.User;

@Transactional(readOnly = true)
public class HibernateUserDao extends AbstractHibernateDao implements UserDao {

	@Override
	public User getById(Long id) throws DataAccessException {
		return (User) super.getById(User.class, id);
	}

	@Override
	public User getByEmail(String email) throws DataAccessException {
		return (User) super.findOne("from User where email=?", email);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() throws DataAccessException {
		return (List<User>) super
				.findAll("from User order by firstName, lastName");
	}

	@Transactional(readOnly = false)
	@Override
	public void save(User user) throws DataAccessException {
		synchronized (user) {
			if (!user.isIdSet()) {
				user.setCreated(new Date());
			}
		}
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
		super.deleteById(User.class, id);
	}
}
