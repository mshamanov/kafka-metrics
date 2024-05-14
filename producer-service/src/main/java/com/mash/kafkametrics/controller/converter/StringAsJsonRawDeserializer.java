package com.mash.kafkametrics.controller.converter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Deserializer to keep json as a raw value (without trying to deserialize it).
 *
 * @author Mikhail Shamanov
 */
@Component
public class StringAsJsonRawDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser p, DeserializationContext context) throws IOException, JacksonException {
        TreeNode tree = p.getCodec().readTree(p);
        return tree.toString();
    }
}
