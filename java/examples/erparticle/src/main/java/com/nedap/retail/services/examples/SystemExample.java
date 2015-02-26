package com.nedap.retail.services.examples;

import java.util.List;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.metrics.DetailedStatus;
import com.nedap.retail.messages.system.SystemListPayload;
import com.nedap.retail.messages.system.SystemStatusPayload;
import com.sun.jersey.api.client.UniformInterfaceException;

public class SystemExample {
    public static void runExample(final Client client) {
        System.out.println("*** System API example ***");

        try {
            // show list of systems
            System.out.println("Getting system list...");
            final List<SystemListPayload> systems = client.getSystemList();
            System.out.println("Got " + systems.size() + " systems");
            printSystems(systems);

            // get system statuses
            System.out.println("Getting system status...");
            final List<SystemStatusPayload> statuses = client.getSystemStatus();
            System.out.println("Got " + statuses.size() + " systems");
            printStatuses(statuses);

        } catch (final UniformInterfaceException e) {
            System.err.println("Server responded with an error: " + e.getResponse().getEntity(String.class));
        }
    }

    private static void printSystems(final List<SystemListPayload> systems) {
        for (final SystemListPayload system : systems) {
            System.out.println("  SystemId: " + system.getSystemId());
            System.out.println("  Name: " + system.getName());
            System.out.println("  Location: " + system.getLocation());
        }
    }

    private static void printStatuses(final List<SystemStatusPayload> statuses) {
        for (final SystemStatusPayload status : statuses) {
            System.out.println("  SystemId: " + status.getSystemId());
            System.out.println("  Firmware: " + status.getFirmwareVersion());
            System.out.println("  Status: " + status.getStatus());
            if (status.getDetailedStatus() != null) {
                showStatusDetails(status);
            }
            if (!status.getOfflineSince().isEmpty()) {
                System.out.println("  Offline since: " + status.getOfflineSince());
            }
        }
    }

    private static void showStatusDetails(final SystemStatusPayload status) {
        for (final DetailedStatus detailedStatus : status.getDetailedStatus()) {
            System.out.println("    " + detailedStatus.getType() + " = " + detailedStatus.getStatus());
        }
    }
}