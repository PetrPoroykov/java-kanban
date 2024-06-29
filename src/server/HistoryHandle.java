package server;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
public class HistoryHandle extends BaseHttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        try {
            String method = httpExchange.getRequestMethod();
            String response = "";
            if (!method.equals("GET")) {
                response = "Метод " + method + " не обрабатывается";
                System.out.println("Метод " + method + " не обрабатывается");
                send(httpExchange, response, 405);
            } else {
                response = HttpTaskServer.gson.toJson(HttpTaskServer.taskManager.getHistory());
                send(httpExchange, response, 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
