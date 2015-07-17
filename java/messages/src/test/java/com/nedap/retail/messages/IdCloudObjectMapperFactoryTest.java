package com.nedap.retail.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nedap.retail.messages.stock.StockSummaryRequest;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class IdCloudObjectMapperFactoryTest {

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = IdCloudObjectMapperFactory.create();
    }

    @Test
    public void test_unknown_fields() throws IOException {

        final String payload = "{ \"id\" : \"abc\", \"foo\":\"abc\" }";
        final StockSummaryRequest request = mapper.readValue(payload, StockSummaryRequest.class);
        assertEquals("abc", request.id);
    }
}
