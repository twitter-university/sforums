package sforums.web.rest;

import org.springframework.beans.factory.annotation.Autowired;

import sforums.dao.TopicDao;
import sforums.domain.Topic;

public class TopicXmlAdapter extends IdentifiableEntityXmlAdapter<Topic> {

	@Autowired
	public TopicXmlAdapter(TopicDao dao) {
		super(dao);
	}

	public TopicXmlAdapter() {
		this(null);
	}
}
