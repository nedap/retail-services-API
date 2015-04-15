package com.nedap.retail.services.examples;

import static com.nedap.retail.services.examples.PrintHelper.COLON;
import static com.nedap.retail.services.examples.PrintHelper.DOUBLE_TAB;
import static com.nedap.retail.services.examples.PrintHelper.NEW_LINE;
import static com.nedap.retail.services.examples.PrintHelper.TAB;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.ClientException;
import com.nedap.retail.messages.metrics.DetailedStatus;
import com.nedap.retail.messages.metrics.Status;
import com.nedap.retail.messages.system.SystemListPayload;
import com.nedap.retail.messages.system.SystemStatusPayload;

public class SystemExample {

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
        final StringBuilder sb = new StringBuilder("Got " + systems.size() + " systems");
        for (int i = 0; i < systems.size(); i++) {
            sb.append(NEW_LINE + TAB + "System " + (i + 1) + COLON);
            sb.append(NEW_LINE + TAB + "System id: " + systems.get(i).getSystemId());
            sb.append(NEW_LINE + TAB + "Name: " + systems.get(i).getName());
            sb.append(NEW_LINE + TAB + "Location: " + systems.get(i).getLocation());
        }
        return sb.toString();
    }

    private static String printSystemStatuses(final List<SystemStatusPayload> statuses) {
        final StringBuilder sb = new StringBuilder("Got " + statuses.size() + " systems");
        for (int i = 0; i < statuses.size(); i++) {
            sb.append(NEW_LINE + TAB + "System " + (i + 1) + COLON);
            sb.append(NEW_LINE + TAB + "System id: " + statuses.get(i).getSystemId());
            sb.append(NEW_LINE + TAB + "Firmware version: " + statuses.get(i).getFirmwareVersion());
            sb.append(NEW_LINE + TAB + "Status: " + statuses.get(i).getStatus());

            if (statuses.get(i).getStatus() == Status.OFFLINE) {
                sb.append(NEW_LINE + TAB + "Offline since: " + statuses.get(i).getOfflineSince());
            }
            if (CollectionUtils.isEmpty(statuses.get(i).getDetailedStatus())) {
                sb.append(showStatusDetails(statuses.get(i).getDetailedStatus()));
            }
        }
        return sb.toString();
    }

    private static String showStatusDetails(final List<DetailedStatus> detailedStatuses) {
        final StringBuilder sb = new StringBuilder(NEW_LINE + TAB + "Detailed status:");
        for (int i = 0; i < detailedStatuses.size(); i++) {
            sb.append(NEW_LINE + TAB + "Detailed status " + (i + 1) + COLON);
            sb.append(NEW_LINE + DOUBLE_TAB + "Type: " + detailedStatuses.get(i).getType());
            sb.append(NEW_LINE + DOUBLE_TAB + "Status: " + detailedStatuses.get(i).getStatus());
            sb.append(NEW_LINE);
        }
        return sb.toString();
    }
}
