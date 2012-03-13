package sforums.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends IdentifiableEntity {

	private static final long serialVersionUID = 2993569267760500809L;

	private String firstName;

	private String lastName;

	private String organization;

	private String title;

	private String email;

	private String passwordDigest;

	private Date created;

	@Column(length = 64)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(length = 20, nullable = false)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(length = 20, nullable = false)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(length = 64, nullable = false, unique = true)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length = 32, nullable = false)
	public String getPasswordDigest() {
		return this.passwordDigest;
	}

	public void setPasswordDigest(String passwordDigest) {
		this.passwordDigest = passwordDigest;
	}

	@Column(length = 64)
	public String getOrganization() {
		return this.organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
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

	@Transient
	public String getName() {
		return this.getFirstName() + " " + this.getLastName();
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
