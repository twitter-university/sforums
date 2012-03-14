package sforums.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import sforums.domain.User;

public interface UserDao {
    public User getById(Long id) throws DataAccessException;

    public User getByEmail(String email) throws DataAccessException;

    public List<User> getAll() throws DataAccessException;

    public void save(User user) throws DataAccessException;

    public void delete(User user) throws DataAccessException;

    public void deleteById(Long id) throws DataAccessException;
}
