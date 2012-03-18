package sforums.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

import sforums.Util;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "forum", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"name", "category_id" }) })
@NamedQueries({
		@NamedQuery(name = "all-forums", query = "from Forum order by category.name, name", hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true") }),
		@NamedQuery(name = "forum-by-id-fetch-all", query = "select distinct f from Forum as f inner join fetch f.category left join fetch f.topics as t inner join fetch t.author where f.id=:id", hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true") }),
		@NamedQuery(name = "forum-by-category-and-name", query = "from Forum where category=:category and name=:name") })
@BatchSize(size = 10)
public class Forum extends IdentifiableEntity {

	private static final long serialVersionUID = 8322524543528154627L;
	private Category category;
	private String name;
	private String description;
	private List<Topic> topics = new ArrayList<Topic>();

	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Size(max = 128)
	@NotEmpty
	@NotNull
	@Column(length = 128, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Lob
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(mappedBy = "forum", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@OrderBy
	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	@Override
	public int hashCode() {
		return Util.hashCode(Util.hashCode(17, getName()), getCategory());
	}

	@Transient
	public String getFullName() {
		return this.getCategory().getName() + " - " + this.getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof Forum) {
			Forum that = (Forum) obj;
			return Util.equal(this.getCategory(), that.getCategory())
					&& Util.equal(this.getName(), that.getName());
		} else {
			return false;
		}
	}
}