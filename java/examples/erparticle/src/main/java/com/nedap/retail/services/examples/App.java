package com.nedap.retail.services.examples;

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
import com.sun.jersey.api.client.UniformInterfaceException;

/**
 * This tool can be used to test the !D Cloud ERP and Article APIs.
 *
 * usage: java -jar erparticle.jar -clientid <CLIENTID> -secret <SECRET> [-url <url>]
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
    private static final String URL = "https://api.nedapretail.com";
    private final Client apiClient;

    public static void main(String[] args) throws Exception {

        final Options options = createCliOption();
        try {
            // Parse command-line parameters.
            final CommandLineParser parser = new BasicParser();
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
            formatter.printHelp("java -jar erparticle.jar ", options);
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
                System.out.println("Menu");
                System.out.println("e       : run ERP API example");
                System.out.println("a       : run Article API example");
                System.out.println("q = quit");
                System.out.print("> ");
                final String cmd = scanner.next();

                try {
                    switch (cmd) {
                        case "e":
                            ErpExample.runExample(apiClient);
                            break;
                        case "a":
                            ArticleExample.runExample(apiClient);
                            break;
                        case "q":
                            quit = true;
                            break;
                    }
                } catch (final UniformInterfaceException ex) {
                    System.out.println(ex.getResponse().toString());
                }
            }
        } finally {
            apiClient.destroy();
        }
    }
}
