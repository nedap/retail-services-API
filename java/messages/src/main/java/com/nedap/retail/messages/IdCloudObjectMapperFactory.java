package com.nedap.retail.messages;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class IdCloudObjectMapperFactory {

    public static ObjectMapper create() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        // Configure the object mapper to serialize dates according to the ISO-8601 standard
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // Don't fail on unkonwn properties (Feature is enabled by default).
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        mapper.setSerializationInclusion(Include.NON_NULL);
        return mapper;
    }
}
