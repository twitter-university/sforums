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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

import sforums.json.CategoryJsonDeserializer;
import sforums.json.IdentifiableEntityJsonSerializer;
import sforums.xml.CategoryXmlAdapter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "forum", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"name", "category_id" }) })
@NamedQueries({
		@NamedQuery(name = "all-forums", query = "from Forum order by category.name, name", hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true") }),
		@NamedQuery(name = "forum-by-id-fetch-all", query = "select distinct f from Forum as f inner join fetch f.category left join fetch f.topics as t left join fetch t.author where f.id=:id", hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true") }),
		@NamedQuery(name = "forum-by-category-and-name", query = "from Forum where category=:category and name=:name") })
@BatchSize(size = 10)
@XmlRootElement(name = "forum")
@XmlType(propOrder = { "category", "name", "description" })
public class Forum extends IdentifiableEntity {

	private static final long serialVersionUID = 8322524543528154627L;
	private Category category;
	private String name;
	private String description;
	private List<Topic> topics = new ArrayList<Topic>();

	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@XmlJavaTypeAdapter(value = CategoryXmlAdapter.class)
	@JsonSerialize(using = IdentifiableEntityJsonSerializer.class)
	public Category getCategory() {
		return category;
	}

	@JsonDeserialize(using = CategoryJsonDeserializer.class)
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
	@OrderBy("title")
	@XmlTransient
	@JsonIgnore
	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	@Transient
	@XmlTransient
	@JsonIgnore
	public String getFullName() {
		return this.getCategory().getName() + " - " + this.getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Forum))
			return false;
		Forum other = (Forum) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}