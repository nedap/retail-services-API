package com.nedap.retail.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nedap.retail.messages.stock.RetrieveStockSummaryRequest;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class IdCloudObjectMapperFactoryTest {

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = IdCloudObjectMapperFactory.create();
    }

    @Test
    public void test_unknown_fields() throws IOException {

        final String payload = "{ \"id\" : \"abc\", \"foo\":\"abc\" }";
        final RetrieveStockSummaryRequest request = mapper.readValue(payload, RetrieveStockSummaryRequest.class);
        assertEquals("abc", request.id);
        assertFalse(request.withExcluded);
    }
}
