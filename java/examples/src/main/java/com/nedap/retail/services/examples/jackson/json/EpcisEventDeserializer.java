package com.nedap.retail.services.examples.jackson.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nedap.retail.client.model.*;
import java.io.IOException;

/**
 * Custom Jackson JSON deserializer for deserialization of EpcisEvent and child objects.
 *
 * Swagger =< 2.2.1 does not have any functionality regarding polymorphism.
 * Swagger 2.2.2-SNAPSHOT has some limited functionality regarding polymorphism but does not support translation
 * between type names like 'object_event' and its implementation class ObjectEvent.
 *
 * The vendor extension 'x-discriminator-value' exists but is not supported by the
 * com.github.kongchen:swagger-maven-plugin:3.1.4 maven plugin which we use to generate the swagger.json file based
 * on the annotated message and server classes.
 *
 * The field 'type' will be used to determine the right concrete EpcisEvent.
 */
public class EpcisEventDeserializer extends JsonDeserializer<EpcisEvent> {

    private final ObjectMapper mapper;

    public EpcisEventDeserializer(final ObjectMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public EpcisEvent deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        final JsonNode node = jp.getCodec().readTree(jp);
        final String type = node.get("type").asText();
        final EpcisEvent.TypeEnum typeEnum = EpcisEvent.TypeEnum.fromValue(type);

        Class<? extends EpcisEvent> valueType = null;

        switch (typeEnum) {
            case OBJECT_EVENT:
                valueType = ObjectEvent.class;
                break;
            case AGGREGATION_EVENT:
                valueType = AggregationEvent.class;
                break;
            case TRANSACTION_EVENT:
                valueType = TransactionEvent.class;
                break;
            case QUANTITY_EVENT:
                valueType = QuantityEvent.class;
                break;
            case TRANSFORMATION_EVENT:
                valueType = TransformationEvent.class;
                break;
        }

        if (valueType != null) {
            return mapper.treeToValue(node, valueType);
        } else {
            throw new JsonParseException(jp, "Enumeration value of " + type + " not recognised");
        }
    }
}
