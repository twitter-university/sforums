package sforums.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

@Embeddable
@XmlRootElement(name = "name")
public class Name implements Serializable {

	private static final long serialVersionUID = -6645706242888900930L;

	private String first;

	private String last;

	@Size(max = 20)
	@NotEmpty
	@NotNull
	@Column(name = "firstName", length = 20, nullable = false)
	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	@Size(max = 20)
	@NotEmpty
	@NotNull
	@Column(name = "lastName", length = 20, nullable = false)
	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	@Override
	public String toString() {
		return this.getFirst() + " " + this.getLast();
	}

}
