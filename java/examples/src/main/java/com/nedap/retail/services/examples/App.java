package com.nedap.retail.services.examples;

import com.nedap.retail.client.*;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.Scanner;

import static com.nedap.retail.services.examples.PrintHelper.NEW_LINE;

/**
 * This tool can be used to test the !D Cloud APIs.
 */
public class App {

    private static final int EXIT_CODE_OK = 0;
    private static final int EXIT_CODE_ERROR = 1;
    private static final String OPTION_TOKEN= "token";
    private static final String OPTION_URL_API = "url";
    private static final String DEFAULT_URL_API = "https://api.nedapretail.com";
    private final ApiClient apiClient;

    public App(final String urlApi, final String accessToken) {
        apiClient = createApiClient(urlApi, accessToken);
    }

    public static void main(final String[] args) throws Exception {

        final Options options = createCliOption();
        try {
            // Parse command-line parameters.
            final CommandLineParser parser = new BasicParser();
            final CommandLine cmd = parser.parse(options, args);

            // Get command-line parameters.
            final String urlApi = cmd.getOptionValue(OPTION_URL_API, DEFAULT_URL_API);
            final String accessToken = cmd.getOptionValue(OPTION_TOKEN, "");

            // Start app.
            final App app = new App(urlApi, accessToken);
            app.loop();
            System.exit(EXIT_CODE_OK);

        } catch (final ParseException ex) {
            System.out.println(ex.getMessage());
            // Automatically generate the help statement.
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar examples.jar ", options);
            System.exit(EXIT_CODE_ERROR);
        }
    }

    @SuppressWarnings("static-access")
    private static Options createCliOption() {
        final Options options = new Options();
        options.addOption(OptionBuilder.isRequired().hasArg().withArgName(OPTION_TOKEN).withDescription("OAuth 2.0 access token")
                .create(OPTION_TOKEN));
        options.addOption(OptionBuilder.hasArg().withArgName(OPTION_URL_API)
                .withDescription("(Optional) Default is " + DEFAULT_URL_API).create(OPTION_URL_API));
        return options;
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
                            EpcisExample.runExample(apiClient);
                            break;
                        case "3":
                            ErpExample.runExample(apiClient);
                            break;
                        case "4":
                            WorkflowExample.runExample(apiClient);
                            break;
                        case "0":
                            quit = true;
                            break;
                        default:
                            quit = true;
                            break;
                    }
                } catch (final Throwable ex) {
                    System.err.println(ex.getMessage());
                }
            }
            scanner.close();

        } finally {

        }
    }

    private static void printMenu() {
        final StringBuilder sb = new StringBuilder();

        sb.append(NEW_LINE).append("*** Nedap Retail !D Cloud API examples ***");
        sb.append(NEW_LINE).append("Choose example you want to run");
        sb.append(NEW_LINE).append("1 : Article API");
        sb.append(NEW_LINE).append("2 : EPCIS API");
        sb.append(NEW_LINE).append("3 : ERP API");
        sb.append(NEW_LINE).append("4 : Workflow API");
        sb.append(NEW_LINE).append("0 : Quit");
        sb.append(NEW_LINE);

        System.out.println(sb);
    }

    public static ApiClient createApiClient(final String apiUrl, final String accessToken) {
        final ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(apiUrl);
        apiClient.setAccessToken(accessToken);
        return apiClient;
    }
}