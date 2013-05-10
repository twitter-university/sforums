
package com.marakana.sforums.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.marakana.sforums.dao.UserDao;
import com.marakana.sforums.domain.User;

public class DefaultUserStoreService implements UserStoreService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao dao;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultUserStoreService(UserDao dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = false)
    @Override
    public void store(User user, String password) {
        synchronized (user) {
            if (password != null) {
                user.setPasswordDigest(this.passwordEncoder.encode(password));
            }
            this.dao.save(user);
            logger.debug("Stored ", user);
        }
    }
}
