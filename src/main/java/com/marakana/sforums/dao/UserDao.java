
package com.marakana.sforums.dao;

import org.springframework.dao.DataAccessException;

import com.marakana.sforums.domain.User;

public interface UserDao extends IdentifiableEntityDao<User> {
    public User getByEmail(String email) throws DataAccessException;
}
