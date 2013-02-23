package sforums.web.json;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sforums.dao.IdentifiableEntityDao;
import sforums.domain.IdentifiableEntity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class IdentifiableEntityJsonDeserializer<E extends IdentifiableEntity>
		extends JsonDeserializer<E> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected final IdentifiableEntityDao<E> dao;

	public IdentifiableEntityJsonDeserializer(IdentifiableEntityDao<E> dao) {
		this.dao = dao;
		if (this.logger.isTraceEnabled()) {
			this.logger.trace("Configured with dao " + dao);
		}
	}

	@Override
	public E deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return this.dao.getById(jp.getLongValue());
	}
}
