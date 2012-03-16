package sforums.service;

import sforums.domain.User;

public interface UserStoreService {
	public void store(User user, String password);
}
