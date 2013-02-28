package sforums.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import sforums.domain.Topic;

@XmlRootElement(name = "topics")
public class TopicList extends IdentifiableEntityList<Topic> {

	public TopicList() {
	}

	public TopicList(List<Topic> topics) {
		super(topics);
	}

	@Override
	@XmlElement(name = "topic")
	public List<Topic> getList() {
		return super.getList();
	}
}
