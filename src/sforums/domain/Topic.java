package sforums.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

import sforums.web.json.ForumJsonDeserializer;
import sforums.web.json.IdentifiableEntityJsonSerializer;
import sforums.web.rest.ForumXmlAdapter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "topic", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"title", "forum_id" }) })
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
		@NamedQuery(name = "all-topics", query = "from Topic order by created", hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true") }),
		@NamedQuery(name = "topic-by-forum-and-title", query = "from Topic where forum=:forum and title=:title"),
		@NamedQuery(name = "topic-by-id-fetch-all", query = "select distinct t from Topic as t inner join fetch t.author inner join fetch t.forum f inner join fetch f.category left join fetch t.replies r left join fetch r.author where t.id=:id", hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true") }) })
@BatchSize(size = 10)
@XmlRootElement(name = "topic")
public class Topic extends Post {

	private static final long serialVersionUID = -8501774350808842862L;

	private Forum forum;
	private String title;
	private List<Reply> replies = new ArrayList<Reply>();

	@NotNull
	@ManyToOne(optional = false)
	@XmlJavaTypeAdapter(value = ForumXmlAdapter.class)
	@JsonSerialize(using = IdentifiableEntityJsonSerializer.class)
	public Forum getForum() {
		return forum;
	}

	@JsonDeserialize(using = ForumJsonDeserializer.class)
	public void setForum(Forum forum) {
		this.forum = forum;
	}

	@Size(max = 128)
	@NotEmpty
	@NotNull
	@Column(nullable = false, length = 128)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@OneToMany(mappedBy = "topic", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@OrderBy("created")
	@XmlTransient
	@JsonIgnore
	public List<Reply> getReplies() {
		return replies;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((forum == null) ? 0 : forum.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Topic))
			return false;
		Topic other = (Topic) obj;
		if (forum == null) {
			if (other.forum != null)
				return false;
		} else if (!forum.equals(other.forum))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}