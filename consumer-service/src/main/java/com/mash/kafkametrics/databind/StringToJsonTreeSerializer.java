package com.mash.kafkametrics.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Serializer to create json object {@link TreeNode} from a raw stringified value (json as {@link String}).
 *
 * @author Mikhail Shamanov
 */
@Component
public class StringToJsonTreeSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        try (JsonParser parser = gen.getCodec().getFactory().createParser(value)) {
            TreeNode tree = parser.readValueAsTree();
            gen.writeTree(tree);
        }
    }
}
