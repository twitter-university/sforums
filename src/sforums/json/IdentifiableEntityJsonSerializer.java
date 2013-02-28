package sforums.json;

import java.io.IOException;

import org.springframework.stereotype.Component;

import sforums.domain.IdentifiableEntity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@Component
public class IdentifiableEntityJsonSerializer extends
		JsonSerializer<IdentifiableEntity> {

	@Override
	public void serialize(IdentifiableEntity entity, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		if (entity != null) {
			jgen.writeNumber(entity.getId());
		} else {
			jgen.writeNull();
		}
	}
}