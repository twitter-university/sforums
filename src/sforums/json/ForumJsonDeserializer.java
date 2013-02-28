package sforums.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sforums.dao.ForumDao;
import sforums.domain.Forum;

@Component
public class ForumJsonDeserializer extends
		IdentifiableEntityJsonDeserializer<Forum> {
	@Autowired
	public ForumJsonDeserializer(ForumDao dao) {
		super(dao);
	}
}
