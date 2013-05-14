
package com.marakana.sforums.service;

import com.marakana.sforums.domain.User;

public interface UserContextService {
    public User getUserFromContext();

    public void addUserToContext(User user, String password);
}
