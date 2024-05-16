package com.mash.kafkametrics.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Deserializer to create a stringified json (as {@link String}) from json object {@link TreeNode}.
 *
 * @author Mikhail Shamanov
 */
@Component
public class JsonTreeToStringDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser p, DeserializationContext context) throws IOException {
        TreeNode tree = p.getCodec().readTree(p);
        return tree.toString();
    }
}
