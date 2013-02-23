package sforums.web.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

import sforums.domain.Forum;

@XmlRootElement(name = "forums")
@JsonRootName("forums")
public class ForumList extends IdentifiableEntityList<Forum> {

	public ForumList() {
	}

	public ForumList(List<Forum> forums) {
		super(forums);
	}

	@XmlElement(name = "forum")
	@Override
	public List<Forum> getList() {
		return super.getList();
	}
}
