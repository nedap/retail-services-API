package com.nedap.retail.services.examples;

import com.nedap.retail.client.ApiClient;
import com.nedap.retail.client.Configuration;
import com.nedap.retail.client.api.DefaultApi;
import com.nedap.retail.messages.AccessTokenResolver;
import com.nedap.retail.messages.AuthorizationClientFilter;
import com.nedap.retail.messages.ClientException;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.Scanner;

import static com.nedap.retail.services.examples.PrintHelper.NEW_LINE;

/**
 * This tool can be used to test the !D Cloud APIs.
 * <p>
 * usage: java -jar examples.jar -clientid CLIENTID -secret SECRET [-url URL]
 * <p>
 * Exit codes: 0: successfull. 1: not successfull.
 */
public class App {

    private static final int EXIT_CODE_OK = 0;
    private static final int EXIT_CODE_ERROR = 1;
    private static final String OPTION_CLIENTID = "clientid";
    private static final String OPTION_SECRET = "secret";
    private static final String OPTION_URL = "url";
    private static final String URL = "https://api.nedapretail.com";
    private final DefaultApi apiClient;

    public App(final String clientId, final String secret, final String url) {
        final ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:9090");
        final javax.ws.rs.client.Client httpClient = defaultClient.getHttpClient();
        final AccessTokenResolver accessTokenResolver = new AccessTokenResolver("http://localhost:9091/login/oauth/token",
                clientId, secret, httpClient);
        httpClient.register(new AuthorizationClientFilter(accessTokenResolver));
        apiClient = new DefaultApi(defaultClient);
    }

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

    @SuppressWarnings("static-access")
    private static Options createCliOption() {
        final Options options = new Options();
        options.addOption(OptionBuilder.isRequired().hasArg().withArgName("id").withDescription("OAuth 2.0 client ID")
                .create(OPTION_CLIENTID));
        options.addOption(OptionBuilder.isRequired().hasArg().withArgName("secret").withDescription("OAuth 2.0 secret")
                .create(OPTION_SECRET));
        options.addOption(OptionBuilder.hasArg().withArgName("url")
                .withDescription("(Optional) Default is https://api.nedapretail.com").create(OPTION_URL));
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
//                        case "2":
//                            EpcExample.runExample(apiClient);
//                            break;
                        case "3":
                            EpcisExample.runExample(apiClient);
                            break;
                        case "4":
                            ErpExample.runExample(apiClient);
                            break;
//                        case "5":
//                            WorkflowExample.runExample(apiClient);
//                            break;
                        case "0":
                            quit = true;
                            break;
                        default:
                            quit = true;
                            break;
                    }
                } catch (final ClientException ex) {
                    System.err.println(ex.getMessage());
                }
            }
            scanner.close();

        } finally {
//            apiClient.destroy();
        }
    }

    private static void printMenu() {
        final StringBuilder sb = new StringBuilder();

        sb.append(NEW_LINE).append("*** Nedap Retail API examples ***");
        sb.append(NEW_LINE).append("Choose example you want to run");
        sb.append(NEW_LINE).append("1 : Article API");
        sb.append(NEW_LINE).append("2 : EPC API");
        sb.append(NEW_LINE).append("3 : EPCIS API");
        sb.append(NEW_LINE).append("4 : ERP API");
        sb.append(NEW_LINE).append("5 : Workflow API");
        sb.append(NEW_LINE).append("0 : Quit");
        sb.append(NEW_LINE);

        System.out.println(sb);
    }
}
