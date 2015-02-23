package com.nedap.retail.services.examples;

import java.util.Arrays;

import org.joda.time.DateTime;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.workflow.WorkflowEvent;
import com.sun.jersey.api.client.UniformInterfaceException;

public class WorkfolwExample {

    private static final String LOCATION = "http://location-testing";

    public static void runExample(final Client client) {
        System.out.println("*** Workflow API example ***");

        final WorkflowEvent workflow = makeWorkflowEvent();

        try {
            // capture
            System.out.println("--- Capture workflow events");
            client.captureWorkflow(workflow);
            System.out.println("--- Captured workflow events");

        } catch (final UniformInterfaceException e) {
            System.err.println("Server responded with an error:");
            System.err.println(e.getResponse().getEntity(String.class));
        }
        System.out.println("--- Done");
    }

    private static WorkflowEvent makeWorkflowEvent() {
        final WorkflowEvent workflow = new WorkflowEvent();
        workflow.setType("cycle_count_started");
        workflow.setEventTime(DateTime.now());
        workflow.setLocation(LOCATION);
        workflow.setEpcCount((long) 10);
        workflow.setMessageIds(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
        return workflow;
    }
}