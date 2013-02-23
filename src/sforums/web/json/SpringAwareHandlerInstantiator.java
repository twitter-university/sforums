package sforums.web.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;

public class SpringAwareHandlerInstantiator extends HandlerInstantiator {
	private static final Logger logger = LoggerFactory
			.getLogger(SpringAwareHandlerInstantiator.class);

	private final ApplicationContext applicationContext;

	@Autowired
	public SpringAwareHandlerInstantiator(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public JsonDeserializer<?> deserializerInstance(
			DeserializationConfig config, Annotated annotated,
			Class<?> deserClass) {
		return (JsonDeserializer<?>) getBean(deserClass,
				"deserializer instance");
	}

	@Override
	public KeyDeserializer keyDeserializerInstance(
			DeserializationConfig config, Annotated annotated,
			Class<?> keyDeserClass) {
		return (KeyDeserializer) getBean(keyDeserClass,
				"key deserializer instance");
	}

	@Override
	public JsonSerializer<?> serializerInstance(SerializationConfig config,
			Annotated annotated, Class<?> serClass) {
		return (JsonSerializer<?>) getBean(serClass, "serializer instance");
	}

	@Override
	public TypeResolverBuilder<?> typeResolverBuilderInstance(
			MapperConfig<?> config, Annotated annotated, Class<?> builderClass) {
		return (TypeResolverBuilder<?>) getBean(builderClass,
				"type resolver builder");
	}

	@Override
	public TypeIdResolver typeIdResolverInstance(MapperConfig<?> config,
			Annotated annotated, Class<?> resolverClass) {
		return (TypeIdResolver) getBean(resolverClass,
				"type id resolver instance");
	}

	private Object getBean(Class<?> clazz, String what) {
		try {
			if (logger.isTraceEnabled()) {
				logger.trace("Getting " + what + " for " + clazz);
			}
			return this.applicationContext.getBean(clazz);
		} catch (Exception e) {
			if (logger.isWarnEnabled()) {
				logger.warn("Failed to get " + what + " for " + clazz, e);
			}
			return null;
		}
	}
}
