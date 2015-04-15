package com.nedap.retail.services.examples;

import static com.nedap.retail.services.examples.PrintHelper.NEW_LINE;

import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.ClientException;

/**
 * This tool can be used to test the !D Cloud APIs.
 *
 * usage: java -jar examples.jar -clientid <CLIENTID> -secret <SECRET> [-url <url>]
 *
 * Exit codes: 0: successfull. 1: not successfull.
 */
public class App {

    private static final int EXIT_CODE_OK = 0;
    private static final int EXIT_CODE_ERROR = 1;
    private static final String OPTION_CLIENTID = "clientid";
    private static final String OPTION_SECRET = "secret";
    private static final String OPTION_URL = "url";
    private static final String URL = "https://api.nedapretail.com";
    private final Client apiClient;

    public static void main(final String[] args) throws Exception {

        final Options options = createCliOption();
        try {
            // Parse command-line parameters.
            final CommandLineParser parser = new BasicParser();
            final CommandLine cmd = parser.parse(options, args);

            // Get command-line parameters.
            final String clientId = cmd.getOptionValue(OPTION_CLIENTID);
            final String secret = cmd.getOptionValue(OPTION_SECRET);
            final String url = cmd.getOptionValue(OPTION_URL, URL);

            System.out.println(NEW_LINE + "OAuth 2.0 client ID: " + clientId);
            System.out.println("url: " + url);

            // Start app.
            final App app = new App(clientId, secret, url);
            app.loop();
            System.exit(EXIT_CODE_OK);

        } catch (final ParseException ex) {
            System.out.println(ex.getMessage());
            // Automatically generate the help statement.
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar erparticle.jar ", options);
            System.exit(EXIT_CODE_ERROR);
        }
    }

    private static Options createCliOption() throws IllegalArgumentException {
        final Options options = new Options();
        options.addOption(OptionBuilder.isRequired().hasArg().withArgName("id").withDescription("OAuth 2.0 client ID")
                .create(OPTION_CLIENTID));
        options.addOption(OptionBuilder.isRequired().hasArg().withArgName("secret").withDescription("OAuth 2.0 secret")
                .create(OPTION_SECRET));
        options.addOption(OptionBuilder.hasArg().withArgName("url")
                .withDescription("(Optional) Default is https://api.nedapretail.com").create(OPTION_URL));
        return options;
    }

    public App(final String clientId, final String secret, final String url) throws IOException {
        apiClient = new Client(url, clientId, secret);
    }

    private void loop() throws IOException {
        try {
            final Scanner scanner = new Scanner(System.in);
            boolean quit = false;

            while (!quit) {
                printMenu();
                final String cmd = scanner.next();

                try {
                    switch (cmd) {
                        case "1":
                            ArticleExample.runExample(apiClient);
                            break;
                        case "2":
                            EpcExample.runExample(apiClient);
                            break;
                        case "3":
                            EpcisExample.runExample(apiClient);
                            break;
                        case "4":
                            ErpExample.runExample(apiClient);
                            break;
                        case "5":
                            SystemExample.runExample(apiClient);
                            break;
                        case "6":
                            WorkflowExample.runExample(apiClient);
                            break;
                        case "0":
                            quit = true;
                            break;
                    }
                } catch (final ClientException ex) {
                    System.err.println(ex.getMessage());
                }
            }
            scanner.close();

        } finally {
            apiClient.destroy();
        }
    }

    private void printMenu() {
        final StringBuilder sb = new StringBuilder();

        sb.append(NEW_LINE + "*** Nedap Retail API examples ***");
        sb.append(NEW_LINE + "Choose example you want to run");
        sb.append(NEW_LINE + "1 : Article API");
        sb.append(NEW_LINE + "2 : EPC API");
        sb.append(NEW_LINE + "3 : EPCIS API");
        sb.append(NEW_LINE + "4 : ERP API");
        sb.append(NEW_LINE + "5 : System API");
        sb.append(NEW_LINE + "6 : Workflow API");
        sb.append(NEW_LINE + "0 : Quit");
        sb.append(NEW_LINE);

        System.out.println(sb);
    }
}
