package com.nedap.retail.services.examples;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.workflow.WorkflowEvent;
import com.sun.jersey.api.client.UniformInterfaceException;

public class WorkfolwExample {

    private static final String LOCATION = "http://location-testing";

    public static void runExample(final Client client) {
        System.out.println("-------------");
        System.out.println("Workflow API example");
        System.out.println("-------------");

        final WorkflowEvent workflow = makeWorkflowEvent();

        try {
            // capture
            System.out.println("------------- Capture workflow events");
            client.captureWorkflow(workflow);
            System.out.println("Captured workflow events");

        } catch (final UniformInterfaceException e) {
            System.err.println("Server responded with an error:");
            System.err.println(e.getResponse().getEntity(String.class));
        }
        System.out.println("------------- Done");
    }

    private static WorkflowEvent makeWorkflowEvent() {
        final WorkflowEvent workflow = new WorkflowEvent();
        workflow.setType("cycle_count_started");
        workflow.setEventTime(DateTime.now());
        workflow.setLocation(LOCATION);
        workflow.setEpcCount((long) 10);
        workflow.setMessageIds(getMessageIds());
        return workflow;
    }

    private static List<String> getMessageIds() {
        final List<String> messageIds = new ArrayList<>();
        messageIds.add("1");
        messageIds.add("2");
        messageIds.add("3");
        messageIds.add("4");
        messageIds.add("5");
        messageIds.add("6");
        messageIds.add("7");
        messageIds.add("8");
        messageIds.add("9");
        messageIds.add("10");
        return messageIds;
    }
}