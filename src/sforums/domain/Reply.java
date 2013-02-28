package sforums.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import sforums.json.IdentifiableEntityJsonSerializer;
import sforums.json.TopicJsonDeserializer;
import sforums.xml.TopicXmlAdapter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "reply")
@BatchSize(size = 10)
@XmlRootElement(name = "reply")
public class Reply extends Post {

	private static final long serialVersionUID = -5040902653340337084L;
	private Topic topic;

	@NotNull
	@ManyToOne(optional = false)
	@XmlJavaTypeAdapter(value = TopicXmlAdapter.class)
	@JsonSerialize(using = IdentifiableEntityJsonSerializer.class)
	public Topic getTopic() {
		return topic;
	}

	@JsonDeserialize(using = TopicJsonDeserializer.class)
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
}