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

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

import sforums.Util;

@Entity
@Table(name = "topic", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"title", "forum_id" }) })
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
		@NamedQuery(name = "all-topics", query = "from Topic order by created", hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true") }),
		@NamedQuery(name = "topic-by-forum-and-title", query = "from Topic where forum=:forum and title=:title") })
@BatchSize(size = 10)
public class Topic extends Post {

	private static final long serialVersionUID = -8501774350808842862L;

	private Forum forum;
	private String title;
	private List<Reply> replies = new ArrayList<Reply>();

	@NotNull
	@ManyToOne(optional = false)
	public Forum getForum() {
		return forum;
	}

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
	@OrderBy
	public List<Reply> getReplies() {
		return replies;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}

	@Override
	public int hashCode() {
		return Util.hashCode(Util.hashCode(17, getTitle()), getForum());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof Topic) {
			Topic that = (Topic) obj;
			return Util.equal(this.getTitle(), that.getTitle())
					&& Util.equal(this.getForum(), that.getForum());
		} else {
			return false;
		}
	}
}