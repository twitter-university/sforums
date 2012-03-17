package sforums.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "post")
public class Post extends TimestampedEntity {

	private static final long serialVersionUID = 3450505472571505476L;

	private String message;

	private User author;

	@NotNull
	@NotEmpty
	@Column(nullable = false)
	@Lob
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@NotNull
	@ManyToOne(optional = false)
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
}