package sforums.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import sforums.dao.UserDao;
import sforums.domain.User;

public class DefaultUserStoreService implements UserStoreService {
	private final Log logger = LogFactory.getLog(this.getClass());
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
		if (password != null) {
			if (!user.isIdSet()) {
				boolean tempPasswordSet = false;
				if (user.getPasswordDigest() == null) {
					user.setPasswordDigest("temp");
					tempPasswordSet = true;
				}
				try {
					this.dao.save(user);
				} catch (RuntimeException e) {
					if (tempPasswordSet) {
						user.setPasswordDigest(null);
					}
					throw e;
				}
			}
			String encodedPassword = this.passwordEncoder.encodePassword(
					password, user.getId());
			user.setPasswordDigest(encodedPassword);
		}
		this.dao.save(user);
		if (logger.isDebugEnabled()) {
			logger.debug("Stored user: " + user);
		}

	}
}
