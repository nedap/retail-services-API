package com.nedap.retail.services.tool;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Web server.
 */
public class WebServer implements HttpHandler {

    private HttpServer httpServer; // It will receive the HTTP callback's.

    public void start(final String bindAddress) throws IOException {
        httpServer = HttpServerFactory.create(bindAddress, this);
        httpServer.start();
    }

    public void destroy() {
        httpServer.stop(0);
    }

    @Override
    public void handle(final HttpExchange exchange) {
        try {
            System.out.println("received: " + exchange.getRequestURI());
            final String query = exchange.getRequestURI().getQuery();
            if (query != null && query.startsWith("hub.mode=subscribe")) {
                handleSubscribe(exchange);
            } else {
                handleNotification(exchange);
            }
            exchange.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Put the notification on the queue so it can be examined later.
     */
    private void handleNotification(final HttpExchange exchange) throws IOException {
        final String body = readBody(exchange);
        System.out.println(body);
        exchange.sendResponseHeaders(200, 0);
    }

    /**
     * Echo hub.challenge in the response.
     */
    private void handleSubscribe(final HttpExchange exchange) throws IOException {
        final Map<String, String> map = queryToMap(exchange.getRequestURI().getQuery());
        final String challege = map.get("hub.challenge");
        System.out.println("subscribe challege = " + challege);

        exchange.sendResponseHeaders(200, challege.length());
        exchange.getResponseBody().write(challege.getBytes());
        exchange.getResponseBody().close();
    }

    private static String readBody(final HttpExchange exchange) {
        final Scanner scanner = new Scanner(exchange.getRequestBody());
        try {
            final StringBuilder text = new StringBuilder();
            while (scanner.hasNextLine()) {
                text.append(scanner.nextLine());
            }
            return text.toString();
        } finally {
            scanner.close();
        }
    }

    private static Map<String, String> queryToMap(final String query) {
        final Map<String, String> result = new HashMap();
        for (final String param : query.split("&")) {
            final String pair[] = param.split("=");
            result.put(pair[0], pair[1]);
        }
        return result;
    }
}
