package com.nedap.retail.services.tool;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.InvalidMessage;
import com.nedap.retail.messages.metrics.Status;
import com.nedap.retail.messages.subscription.Subscription;
import com.nedap.retail.messages.system.SystemListPayload;
import com.nedap.retail.messages.system.SystemStatusPayload;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.cli.*;

/**
 * This tool can be used to test the Nedap Retail App API.
 *
 * usage: java -jar toole.jar -clientid <id> -secret <secret> [-url <url>]
 *
 * Exit codes:
 * 0: successfull.
 * 1: not successfull.
 */
public class App {

    private static final int EXIT_CODE_OK = 0;
    private static final int EXIT_CODE_ERROR = 1;
    private static final String OPTION_CLIENTID = "clientid";
    private static final String OPTION_SECRET = "secret";
    private static final String OPTION_URL = "url";
    private static final String OPTION_HELP = "h";
    private static final String URL = "https://api.nedapretail.com";
    private static final int WEB_SERVER_PORT = 32123;
    private final Client apiClient;
    private final WebServer webServer;

    public static void main(String[] args) throws Exception {

        final Options options = createCliOption();
        try {
            // Parse command-line parameters.
            final CommandLineParser parser = new DefaultParser();
            final CommandLine cmd = parser.parse(options, args);

            // Get command-line parameters.
            final String clientId = cmd.getOptionValue(OPTION_CLIENTID);
            final String secret = cmd.getOptionValue(OPTION_SECRET);
            final String url = cmd.getOptionValue(OPTION_URL, URL);

            System.out.println("OAuth 2.0 client ID: " + clientId);
            System.out.println("url: " + url);

            // Start app.
            final App app = new App(clientId, secret, url);
            app.loop();
            System.exit(EXIT_CODE_OK);

        } catch (final ParseException ex) {
            System.out.println(ex.getMessage());
            // Automatically generate the help statement.
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar tool.jar ", options);
            System.exit(EXIT_CODE_ERROR);
        }
    }

    private static Options createCliOption() throws IllegalArgumentException {
        final Options options = new Options();
        options.addOption(OptionBuilder.isRequired().hasArg().withArgName("id")
                .withDescription("OAuth 2.0 client ID")
                .create(OPTION_CLIENTID));
        options.addOption(OptionBuilder.isRequired().hasArg().withArgName("secret")
                .withDescription("OAuth 2.0 secret")
                .create(OPTION_SECRET));
        options.addOption(OptionBuilder.hasArg().withArgName("url")
                .withDescription("(Optional) Default is https://api.nedapretail.com")
                .create(OPTION_URL));
        options.addOption(OPTION_HELP, "help", false, "URI_LOGIN");
        return options;
    }

    public App(final String clientId, final String secret, final String url) throws IOException {

        apiClient = new Client(url, clientId, secret);
        webServer = new WebServer();
    }

    private void loop() throws IOException {
        try {
            // @todo make web server bind address configurable.
            final String bind_address = "http://127.0.0.1:" + WEB_SERVER_PORT + "/";
            System.out.println("web server binding at: " + bind_address);
            webServer.start(bind_address);

            printMenu();
            final Scanner scanner = new Scanner(System.in);

            boolean quit = false;
            while (!quit) {
                System.out.print("> ");
                final String cmd = scanner.next();

                try {
                    if (cmd.equals("l")) {
                        systemList();
                    } else if (cmd.equals("s")) {
                        systemStatus();
                    } else if (cmd.equals("ss")) {
                        subscribe();
                    } else if (cmd.equals("su")) {
                        unsubscribe();
                    } else if (cmd.equals("sl")) {
                        subscriptionList();
                    } else if (cmd.equals("fl")) {
                        firmwareList(scanner);
                    } else if (cmd.equals("fu")) {
                        firmwareUpgrade(scanner);
                    } else if (cmd.equals("q")) {
                        quit = true;
                    } else if (cmd.equals("?")) {
                        printMenu();
                    }
                } catch (final InvalidMessage ex) {
                    System.out.println("error status: " + ex.getStatus() + " message: " + ex.getMessage());
                }
            }
        } finally {
            System.out.println("stopping....");
            webServer.destroy();
            apiClient.destroy();
        }
    }

    private void systemList() throws InvalidMessage {
        final List<SystemListPayload> list = apiClient.getSystemList();
        if (list.size() > 0) {
            System.out.printf("%-40s %-20s %s\n", "system ID", "name", "location");
            System.out.printf("---------------------------------------------------------------------------------------\n");
            for (final SystemListPayload system : list) {
                System.out.printf("%-40s %-20s %s\n", system.getSystemId(), system.getName(), system.getLocation());
            }
            System.out.printf("---------------------------------------------------------------------------------------\n");
        }
        System.out.println("number of systems: " + list.size());
        System.out.println();
    }

    private void systemStatus() throws InvalidMessage {
        final List<SystemStatusPayload> list = apiClient.getSystemStatus();
        if (list.size() > 0) {
            System.out.printf("%-40s %-20s %s\n", "system ID", "firmware version", "status");
            System.out.printf("---------------------------------------------------------------------------------------\n");
            for (final SystemStatusPayload system : list) {
                final String status;
                final String detailed;
                if (system.getStatus() == null) {
                    status = "";
                    detailed = "";
                } else if (system.getStatus() == Status.OFFLINE) {
                    status = "OFFLINE since " + system.getOfflineSince();
                    detailed = "";
                } else {
                    status = system.getStatus().toString();
                    detailed = system.getDetailedStatus().toString();
                }
                System.out.printf("%-40s %-20s %-20s %s\n", system.getSystemId(), system.getFirmwareVersion(), status, detailed);
            }
            System.out.printf("---------------------------------------------------------------------------------------\n");
        }
        System.out.println("number of systems: " + list.size());
        System.out.println();
    }

    private void subscriptionList() throws InvalidMessage {
        final List<Subscription> list = apiClient.getSubscriptionList();
        if (list.size() > 0) {
            System.out.printf("%-20s %s\n", "topic", "callback");
            System.out.printf("---------------------------------------------------------------------------------------\n");
            for (final Subscription subscription : list) {
                System.out.printf("%-20s %s\n", subscription.getTopic(), subscription.getCallback());
            }
            System.out.printf("---------------------------------------------------------------------------------------\n");
        }
        System.out.println("number of subscriptions: " + list.size());
        System.out.println();
    }

    private void subscribe() throws InvalidMessage {

        // @todo: read parameters from console.
        final String topic = "metrics";
        final String callback = "http://127.0.0.1:" + WEB_SERVER_PORT + "/";
        final String hub_secret = "some_hub_secret";
        final int lease_seconds = 100000;

        System.out.printf("subscribing topic: %s, callback: %s, secret: %s, lease_seconds: %d\n", topic, callback,
                hub_secret, lease_seconds);

        apiClient.subscribe(topic, callback, hub_secret, lease_seconds);
        System.out.println("ok");
    }

    private void unsubscribe() throws InvalidMessage {

        // @todo: read from console.
        final String topic = "metrics";
        System.out.printf("unsubscribing topic: %s...\n", topic);

        apiClient.unsubscribe(topic);
        System.out.println("ok");
    }

    private void firmwareList(final Scanner scanner) throws InvalidMessage {
        final String systemId = scanner.next();
        final List<String> list = apiClient.getFirmwareList(systemId);
        for (final String version : list) {
            System.out.printf(" %s\n", version);
        }
    }

    private void firmwareUpgrade(final Scanner scanner) throws InvalidMessage {
        final String systemId = scanner.next();
        final String version = scanner.next();
        apiClient.triggerFirmwareUpgrade(systemId, version);
    }

    private void printMenu() {
        System.out.println("Menu");
        System.out.println("l       : system list");
        System.out.println("s       : system status");
        System.out.println("ss      : subscription subscribe");
        System.out.println("su      : subscription unsubscribe");
        System.out.println("sl      : subscription list");
        System.out.println("fl <id> : available firmware versions for given system-id");
        System.out.println("fu <id> <version> : upgrade given sytem-d");
        System.out.println("q = quit");
    }
}
