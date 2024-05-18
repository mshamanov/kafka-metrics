package com.mash.kafkametrics.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mash.kafkametrics.ProducerServiceApplication;
import lombok.experimental.UtilityClass;

/**
 * Utility class to work with json.
 *
 * @author Mikhail Shamanov
 */
@UtilityClass
public class JsonUtils {
    /**
     * Gets an instance of {@link ObjectMapper} from the application context.
     *
     * @return an instance of {@link ObjectMapper}
     */
    public static ObjectMapper getObjectMapper() {
        return ProducerServiceApplication.getApplicationContext().getBean(ObjectMapper.class);
    }

    /**
     * Converts an object to a stringified json, i.e. as {@link String}.
     *
     * @param obj any object
     * @return object as string
     * @throws JsonProcessingException in case it failed to convert an object to a json
     */
    public String stringify(Object obj) throws JsonProcessingException {
        return JsonUtils.getObjectMapper().writeValueAsString(obj);
    }
}
