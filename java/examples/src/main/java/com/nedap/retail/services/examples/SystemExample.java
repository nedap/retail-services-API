package com.nedap.retail.services.examples;

import static com.nedap.retail.services.examples.PrintHelper.COLON;
import static com.nedap.retail.services.examples.PrintHelper.DOUBLE_TAB;
import static com.nedap.retail.services.examples.PrintHelper.NEW_LINE;
import static com.nedap.retail.services.examples.PrintHelper.TAB;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.ClientException;
import com.nedap.retail.messages.metrics.DetailedStatus;
import com.nedap.retail.messages.metrics.Status;
import com.nedap.retail.messages.system.SystemListPayload;
import com.nedap.retail.messages.system.SystemStatusPayload;

public class SystemExample {

    private SystemExample() {
    }

    public static void runExample(final Client client) {
        System.out.println(NEW_LINE + "*** System API example ***");

        try {
            // List of systems
            System.out.println(NEW_LINE + "Getting system list...");
            final List<SystemListPayload> systems = client.getSystemList();
            System.out.println(printSystems(systems));

            // System status
            System.out.println(NEW_LINE + "Getting system status...");
            final List<SystemStatusPayload> statuses = client.getSystemStatus();
            System.out.println(printSystemStatuses(statuses));

            System.out.println(NEW_LINE + "--- System API example finished ---");

        } catch (final ClientException ex) {
            System.err.println("Server responded with an error: " + ex.getMessage());
        }
    }

    private static String printSystems(final List<SystemListPayload> systems) {
        final StringBuilder sb = new StringBuilder("Got ").append(systems.size()).append(" systems");
        for (int i = 0; i < systems.size(); i++) {
            sb.append(NEW_LINE).append(TAB).append("System ").append(i + 1).append(COLON);
            sb.append(NEW_LINE).append(TAB).append("System id: ").append(systems.get(i).systemId);
            sb.append(NEW_LINE).append(TAB).append("Name: ").append(systems.get(i).name);
            sb.append(NEW_LINE).append(TAB).append("Location: ").append(systems.get(i).location);
        }
        return sb.toString();
    }

    private static String printSystemStatuses(final List<SystemStatusPayload> statuses) {
        final StringBuilder sb = new StringBuilder("Got ").append(statuses.size()).append(" systems");
        for (int i = 0; i < statuses.size(); i++) {
            sb.append(NEW_LINE).append(TAB).append("System ").append(i + 1).append(COLON);
            sb.append(NEW_LINE).append(TAB).append("System id: ").append(statuses.get(i).systemId);
            sb.append(NEW_LINE).append(TAB).append("Firmware version: ").append(statuses.get(i).firmwareVersion);
            sb.append(NEW_LINE).append(TAB).append("Status: ").append(statuses.get(i).status);

            if (statuses.get(i).status == Status.OFFLINE) {
                sb.append(NEW_LINE).append(TAB).append("Offline since: ").append(statuses.get(i).offlineSince);
            }
            if (CollectionUtils.isEmpty(statuses.get(i).detailedStatus)) {
                sb.append(showStatusDetails(statuses.get(i).detailedStatus));
            }
        }
        return sb.toString();
    }

    private static String showStatusDetails(final List<DetailedStatus> detailedStatuses) {
        final StringBuilder sb = new StringBuilder(NEW_LINE).append(TAB).append("Detailed status:");
        if (detailedStatuses == null) {
            return sb.append(" not available").toString();
        }
        for (int i = 0; i < detailedStatuses.size(); i++) {
            sb.append(NEW_LINE).append(TAB).append("Detailed status ").append(i + 1).append(COLON);
            sb.append(NEW_LINE).append(DOUBLE_TAB).append("Type: ").append(detailedStatuses.get(i).type);
            sb.append(NEW_LINE).append(DOUBLE_TAB).append("Status: ").append(detailedStatuses.get(i).status);
            sb.append(NEW_LINE);
        }
        return sb.toString();
    }
}
