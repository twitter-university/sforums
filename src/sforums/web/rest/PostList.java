package sforums.web.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.fasterxml.jackson.annotation.JsonRootName;

import sforums.domain.Post;
import sforums.domain.Reply;
import sforums.domain.Topic;

@XmlRootElement(name = "posts")
@JsonRootName("posts")
@XmlSeeAlso({ Topic.class, Reply.class })
public class PostList extends IdentifiableEntityList<Post> {

	public PostList() {
	}

	public PostList(List<Post> posts) {
		super(posts);
	}

	@Override
	@XmlElementRef
	public List<Post> getList() {
		return super.getList();
	}
}
