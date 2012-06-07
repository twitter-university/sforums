package sforums.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "users")
public class UserList extends IdentifiableEntityList<User> {

	public UserList() {
	}

	public UserList(List<User> users) {
		super(users);
	}

	@XmlElement(name = "user")
	@Override
	public List<User> getList() {
		return super.getList();
	}
}
