package com.mash.kafkametrics.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mash.kafkametrics.ConsumerServiceApplication;
import lombok.experimental.UtilityClass;

import java.util.Map;

/**
 * Utility class to work with json.
 *
 * @author Mikhail Shamanov
 */
@UtilityClass
public class JsonUtils {

    /**
     * Gets an instance of {@link ObjectMapper} from application context.
     *
     * @return instance of {@link ObjectMapper}
     */
    private static ObjectMapper getObjectMapper() {
        return ConsumerServiceApplication.getApplicationContext().getBean(ObjectMapper.class);
    }

    /**
     * Converts stringified json to a {@link Map} representation.
     *
     * @param rawJson json as {@link String}
     * @return a {@link Map} representation of json
     * @throws JsonProcessingException in case it failed to convert json
     */
    public Map<String, Object> readAsMap(String rawJson) throws JsonProcessingException {
        return JsonUtils.getObjectMapper().readValue(rawJson, new TypeReference<>() {
        });
    }
}
