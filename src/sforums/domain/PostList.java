package sforums.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "posts")
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
