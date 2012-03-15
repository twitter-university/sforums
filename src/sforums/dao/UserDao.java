package sforums.dao;

import org.springframework.dao.DataAccessException;

import sforums.domain.User;

public interface UserDao extends IdentifiableEntityDao<User> {
	public User getByEmail(String email) throws DataAccessException;
}
