package sforums.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@XmlRootElement(name = "user")
public class User extends TimestampedEntity {

	private static final long serialVersionUID = 2993569267760500809L;

	private Name name = new Name();

	private String organization;

	private String title;

	private String email;

	private String passwordDigest;

	private boolean admin = false;

	private boolean enabled = true;

	private List<Post> posts = new ArrayList<Post>();

	@Size(max = 64)
	@Column(length = 64)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotNull
	@Valid
	@Embedded
	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	@Email
	@Size(max = 64)
	@NotEmpty
	@NotNull
	@Column(length = 64, nullable = false, unique = true)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length = 32, nullable = false)
	@XmlTransient
	@JsonIgnore
	public String getPasswordDigest() {
		return this.passwordDigest;
	}

	public void setPasswordDigest(String passwordDigest) {
		this.passwordDigest = passwordDigest;
	}

	@Size(max = 64)
	@Column(length = 64)
	public String getOrganization() {
		return this.organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	@Column(nullable = false)
	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Column(nullable = false)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@OrderBy("created")
	@XmlTransient
	@JsonIgnore
	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (this.getEmail() == null) {
			return false;
		} else if (o instanceof User) {
			User that = (User) o;
			return this.getEmail().equals(that.getEmail());
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.getEmail() == null ? System.identityHashCode(this)
				: 17 * this.getEmail().hashCode();
	}

	@Override
	public String toString() {
		return String.valueOf(this.getName());
	}
}
