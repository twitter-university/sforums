package sforums.web.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sforums.dao.UserDao;
import sforums.domain.User;

@Component
public class UserJsonDeserializer extends
		IdentifiableEntityJsonDeserializer<User> {
	@Autowired
	public UserJsonDeserializer(UserDao dao) {
		super(dao);
	}
}
