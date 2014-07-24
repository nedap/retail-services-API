package com.nedap.retail.messages.epcis.v1_1;

import com.nedap.retail.messages.epcis.v1_1.cbv.Action;
import com.nedap.retail.messages.epcis.v1_1.cbv.Bizstep;
import com.nedap.retail.messages.epcis.v1_1.cbv.Disposition;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import org.joda.time.DateTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

public class ObjectEventTest {

    @Test
    public void test_equals_object_event_1() {
        final ObjectEvent event1 = createObjectEvent(null);
        final ObjectEvent event2 = createObjectEvent(null);
        assertEquals(event1, event2);
    }

    @Test
    public void test_equals_object_event_epc_test() {
        final Set<String> set1 = new HashSet<>();
        set1.add("a");
        set1.add("b");
        final ObjectEvent event1 = createObjectEvent(set1);

        final Set<String> set2 = new HashSet<>();
        set2.add("b"); // Different order, should not mather.
        set2.add("a");
        final ObjectEvent event2 = createObjectEvent(set2);

        final Set<String> set3 = new TreeSet<>();
        set3.add("a");
        set3.add("b");
        final ObjectEvent event3 = createObjectEvent(set3);

        assertEquals(event1, event2);
        assertEquals(event2, event3);
        assertEquals(event3, event1);
    }

    @Test
    public void test_not_equals_object_event_epc_test() {
        final Set<String> set1 = new HashSet<>();
        set1.add("a");
        set1.add("b");
        final ObjectEvent event1 = createObjectEvent(set1);

        final Set<String> set2 = new HashSet<>();
        set2.add("a");
        final ObjectEvent event2 = createObjectEvent(set2);

        final Set<String> set3 = new TreeSet<>();
        set3.add("b");
        final ObjectEvent event3 = createObjectEvent(set3);

        assertNotEquals(event1, event2);
        assertNotEquals(event2, event3);
        assertNotEquals(event3, event1);
    }

    private static ObjectEvent createObjectEvent(final Set<String> epcSet) {
        final ObjectEvent event = new ObjectEvent();
        event.id = "some-id";
        event.eventTime = new DateTime(2014, 07, 21, 8, 30, 0, 0);
        event.recordTime = new DateTime();
        event.eventTimeZoneOffset = "+00:00";
        event.action = Action.OBSERVE.action();
        event.bizLocation = "loc:groenlo";
        event.readPoint = "readpoint:X";
        event.disposition = Disposition.SELLABLE_ACCESSIBLE.disposition();
        event.bizStep = Bizstep.STOCK_TAKING.bizStep();
        event.epcList = epcSet;
        return event;
    }
}
