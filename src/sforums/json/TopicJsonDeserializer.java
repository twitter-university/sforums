package sforums.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sforums.dao.TopicDao;
import sforums.domain.Topic;

@Component
public class TopicJsonDeserializer extends
		IdentifiableEntityJsonDeserializer<Topic> {
	@Autowired
	public TopicJsonDeserializer(TopicDao dao) {
		super(dao);
	}
}
