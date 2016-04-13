package com.nedap.retail.messages.stock;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StockTypeTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getStockTypeFromValue_shouldReturnRFID() throws Exception {
        // act
        final StockType stockType = StockType.permissiveValueOf(2);

        // assert
        assertThat(stockType, is(StockType.RFID_COUNT));
    }

    @Test
    public void toStockSummary_shouldReturnTypeNotFound() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Value 0 cannot be converted to a StockType.");

        // act
        StockType.permissiveValueOf(0);

        // assert (@Rule)
    }
}