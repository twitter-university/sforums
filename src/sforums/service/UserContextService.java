package sforums.service;

import sforums.domain.User;

public interface UserContextService {
	public User getUserFromContext();

	public void addUserToContext(User user, String password);
}
