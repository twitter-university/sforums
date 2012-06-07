package sforums.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;

@MappedSuperclass
public abstract class IdentifiableEntity implements Serializable {

	private static final long serialVersionUID = 4681596106749607674L;

	private Long id;

	private int version = 0;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlTransient
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Transient
	public boolean isIdSet() {
		return this.id != null;
	}

	@Transient
	@XmlElement(name = "id")
	@XmlID
	public String getIdAsString() {
		return String.valueOf(this.id);
	}

	public void setIdAsString(String id) {
		this.id = Long.parseLong(id);
	}

	@Version
	@XmlElement
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (this.isIdSet() && o instanceof IdentifiableEntity) {
			IdentifiableEntity that = (IdentifiableEntity) o;
			return this.getId().equals(that.getId());
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.isIdSet() ? 17 * this.id.hashCode() : super.hashCode();
	}

	@Override
	public String toString() {
		return this.getClass().getName() + " with id " + this.getId();
	}
}
