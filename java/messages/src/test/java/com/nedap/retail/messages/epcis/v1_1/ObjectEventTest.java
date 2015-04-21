package com.nedap.retail.messages.epcis.v1_1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import com.nedap.retail.messages.epcis.v1_1.cbv.Action;
import com.nedap.retail.messages.epcis.v1_1.cbv.Bizstep;
import com.nedap.retail.messages.epcis.v1_1.cbv.Disposition;

public class ObjectEventTest {

    @Test
    public void test_equals_object_event_1() {
        final ObjectEvent event1 = createObjectEvent(null);
        final ObjectEvent event2 = createObjectEvent(null);
        assertEquals(event1, event2);
    }

    @Test
    public void test_equals_object_event_epc_test() {
        final ObjectEvent event1 = createObjectEvent(Arrays.asList("a", "b"));
        final ObjectEvent event2 = createObjectEvent(Arrays.asList("b", "a"));
        final ObjectEvent event3 = createObjectEvent(Arrays.asList("a", "b", "b"));
        final ObjectEvent event4 = createObjectEvent(Arrays.asList("a"));
        final ObjectEvent event5 = createObjectEvent(Arrays.asList("b"));

        assertEquals(event1, event2);
        assertEquals(event1, event3);
        assertNotEquals(event1, event4);
        assertNotEquals(event1, event5);

        assertEquals(event2, event3);
        assertNotEquals(event2, event4);
        assertNotEquals(event2, event5);

        assertNotEquals(event3, event4);
        assertNotEquals(event3, event5);

        assertNotEquals(event4, event5);
    }

    private static ObjectEvent createObjectEvent(final List<String> epcSet) {
        final ObjectEvent event = new ObjectEvent();
        event.id = "some-id";
        event.eventTime = new DateTime(2014, 07, 21, 8, 30, 0, 0);
        event.recordTime = new DateTime();
        event.eventTimeZoneOffset = "+00:00";
        event.action = Action.OBSERVE.action;
        event.bizLocation = "loc:groenlo";
        event.readPoint = "readpoint:X";
        event.disposition = Disposition.SELLABLE_ACCESSIBLE.disposition;
        event.bizStep = Bizstep.STOCK_TAKING.bizStep;
        event.epcList = epcSet;
        return event;
    }
}
