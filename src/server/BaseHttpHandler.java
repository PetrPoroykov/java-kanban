package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class BaseHttpHandler implements HttpHandler {

    protected static void send(HttpExchange h, String text, int cod) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(cod, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected static String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    protected static int parsePathId(String pathId) {
        try {
            return Integer.parseInt(pathId);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
