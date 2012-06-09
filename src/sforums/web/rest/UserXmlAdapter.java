package sforums.web.rest;

import org.springframework.beans.factory.annotation.Autowired;

import sforums.dao.UserDao;
import sforums.domain.User;

public class UserXmlAdapter extends IdentifiableEntityXmlAdapter<User> {

	@Autowired
	public UserXmlAdapter(UserDao dao) {
		super(dao);
	}

	public UserXmlAdapter() {
		this(null);
	}
}
