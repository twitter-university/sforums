package sforums.xml;

import org.springframework.beans.factory.annotation.Autowired;

import sforums.dao.ForumDao;
import sforums.domain.Forum;

public class ForumXmlAdapter extends IdentifiableEntityXmlAdapter<Forum> {

	@Autowired
	public ForumXmlAdapter(ForumDao dao) {
		super(dao);
	}

	public ForumXmlAdapter() {
		this(null);
	}
}
