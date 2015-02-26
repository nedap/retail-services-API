package com.nedap.retail.services.examples;

import java.util.Arrays;

import org.joda.time.DateTime;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.workflow.WorkflowEvent;
import com.sun.jersey.api.client.UniformInterfaceException;

public class WorkflowExample {

    private static final String LOCATION = "Store:Sales floor";

    public static void runExample(final Client client) {
        System.out.println("*** Workflow API example ***");

        final WorkflowEvent workflow = makeWorkflowEvent();

        try {
            // capture
            System.out.println("Capturing workflow events...");
            client.captureWorkflow(workflow);
            System.out.println("--- Done ---");

        } catch (final UniformInterfaceException e) {
            System.err.println("Server responded with an error: " + e.getResponse().getEntity(String.class));
        }
    }

    private static WorkflowEvent makeWorkflowEvent() {
        final WorkflowEvent workflow = new WorkflowEvent();
        workflow.setType("cycle_count_started");
        workflow.setEventTime(DateTime.now());
        workflow.setLocation(LOCATION);
        workflow.setEpcCount((long) 10);
        workflow.setMessageIds(Arrays.asList("abc-123", "def-456", "ghi-789"));
        return workflow;
    }
}