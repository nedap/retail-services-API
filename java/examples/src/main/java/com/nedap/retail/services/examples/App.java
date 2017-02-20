package com.nedap.retail.services.examples;

import com.nedap.retail.client.*;
import com.nedap.retail.messages.ClientException;
import com.nedap.retail.client.model.*;
import org.apache.commons.cli.*;
import com.nedap.retail.messages.AccessTokenResolver;
import com.nedap.retail.messages.AuthorizationClientFilter;
import com.nedap.retail.messages.IAccessTokenResolver;

import java.io.IOException;
import java.util.Scanner;

import static com.nedap.retail.services.examples.PrintHelper.NEW_LINE;

/**
 * This tool can be used to test the !D Cloud APIs.
 *
 * usage: java -jar examples.jar -clientid CLIENTID -secret SECRET [-url URL]
 *
 * Exit codes: 0: successfull. 1: not successfull.
 */
public class App {

    private static final int EXIT_CODE_OK = 0;
    private static final int EXIT_CODE_ERROR = 1;
    private static final String OPTION_CLIENTID = "clientid";
    private static final String OPTION_SECRET = "secret";
    private static final String OPTION_URL_API = "api_url";
    private static final String OPTION_URL_OAUTH = "oauth_url";
    private static final String DEFAULT_URL_API = "https://api.nedapretail.com";
    private static final String DEFAULT_URL_OAUTH = "/login/oauth/token";
    private final ApiClient apiClient;

    public App(final String clientId, final String secret, final String urlApi, final String urlOAuth) {
        apiClient = createApiClient(urlApi, urlOAuth, clientId, secret);
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
            final String urlApi = cmd.getOptionValue(OPTION_URL_API, DEFAULT_URL_API);
            final String urlOAuth = cmd.getOptionValue(OPTION_URL_OAUTH, urlApi + DEFAULT_URL_OAUTH);

            System.out.println(NEW_LINE + "OAuth 2.0 client ID: " + clientId);
            System.out.println("api url: " + urlApi);
            System.out.println("oauth url: " + urlOAuth);

            // Start app.
            final App app = new App(clientId, secret, urlApi, urlOAuth);
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
        options.addOption(OptionBuilder.isRequired().hasArg().withArgName(OPTION_CLIENTID).withDescription("OAuth 2.0 client ID")
                .create(OPTION_CLIENTID));
        options.addOption(OptionBuilder.isRequired().hasArg().withArgName(OPTION_SECRET).withDescription("OAuth 2.0 secret")
                .create(OPTION_SECRET));
        options.addOption(OptionBuilder.hasArg().withArgName(OPTION_URL_API)
                .withDescription("(Optional) Default is " + DEFAULT_URL_API).create(OPTION_URL_API));
        options.addOption(OptionBuilder.hasArg().withArgName(OPTION_URL_OAUTH)
                .withDescription("(Optional) Default is " + DEFAULT_URL_API + DEFAULT_URL_OAUTH).create(OPTION_URL_OAUTH));
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
                            //ErpExample.runExample(apiClient);
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

    public static ApiClient createApiClient(final String apiUrl, final IAccessTokenResolver accessTokenResolver) {
        final ApiClient apiClient = createApiClient(apiUrl);
        registerAuthorizationClientFilter(apiClient, accessTokenResolver);

        return apiClient;
    }

    public static ApiClient createApiClient(final String apiUrl, final String oauthAccessTokenUri, final String
            clientId, final String secret) {
        final ApiClient apiClient = createApiClient(apiUrl);

        final IAccessTokenResolver accessTokenResolver = new AccessTokenResolver(oauthAccessTokenUri, clientId, secret,
                apiClient.getHttpClient());
        registerAuthorizationClientFilter(apiClient, accessTokenResolver);

        return apiClient;
    }

    private static ApiClient createApiClient(final String apiUrl) {
        final ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(apiUrl);
        return apiClient;
    }

    private static void registerAuthorizationClientFilter(final ApiClient apiClient,
                                                          final IAccessTokenResolver accessTokenResolver) {
        // This Client filter automatically adds the "Authorization" header to the HTTP request.
        // The OAuth access token to be added as message header is received from the IAccessTokenResolver.
        final AuthorizationClientFilter authorizationClientFilter = new AuthorizationClientFilter(accessTokenResolver);
        apiClient.getHttpClient().register(authorizationClientFilter);
    }
}
