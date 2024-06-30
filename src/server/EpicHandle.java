package server;

import com.sun.net.httpserver.HttpExchange;
import tasks.Epic;
import tasks.Status;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class EpicHandle extends BaseHttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            String method = httpExchange.getRequestMethod();
            String path = httpExchange.getRequestURI().getPath();
            String response = "";
            boolean isListSudtasks = false;
            String[] paths = path.split("/");
            if (paths.length >= 4 && paths[3].equals("subtasks")) isListSudtasks = true;
            switch (method) {
                case "GET": {
                    if (isListSudtasks) {
                        path = path.replace("/subtasks", "");
                    }
                    if (Pattern.matches("^/epics/\\d+$", path)) {
                        String pathId = path.replaceFirst("/epics/", "");
                        int id = parsePathId(pathId);
                        response = HttpTaskServer.gson.toJson(HttpTaskServer.taskManager.getEpic(id));
                        if (response.equals("null")) {

                            send(httpExchange, "Запрошен эпик с несуществующим ID " + id, 404);
                            break;
                        } else {
                            if (!isListSudtasks) {
                                send(httpExchange, response, 200);
                            } else {
                                response = HttpTaskServer.gson.toJson(HttpTaskServer.taskManager.getSubtasksListByIdEpic(id));
                                send(httpExchange, response, 200);
                            }
                        }
                    } else {
                        response = HttpTaskServer.gson.toJson(HttpTaskServer.taskManager.getAllEpic());
                        send(httpExchange, response, 200);
                    }
                    break;
                }
                case "POST": {
                    String body = readText(httpExchange);
                    Epic epic = HttpTaskServer.gson.fromJson(body, Epic.class);
                    Epic epicProcessed = new Epic(epic.getTitle(), epic.getDescription(), Status.NEW);
                    epicProcessed.setStartTime(LocalDateTime.now());
                    epicProcessed.setDuration(Duration.ZERO);
                    epicProcessed.setEndTime();
                    HttpTaskServer.taskManager.createEpic(epicProcessed);
                    response = HttpTaskServer.getGson().toJson(epicProcessed, Epic.class);
                    send(httpExchange, response, 201);
                    break;
                }
                case "DELETE": {
                    if (Pattern.matches("^/epics/\\d+$", path)) {
                        String pathId = path.replaceFirst("/epics/", "");
                        int id = parsePathId(pathId);
                        HttpTaskServer.taskManager.deleteEpicById(id);
                        response = "Удалили эпик номер " + id;
                        send(httpExchange, response, 200);
                    } else {
                        send(httpExchange, "Введен некоректный номер эпика' для удаления", 405);
                    }
                    break;
                }
                default: {
                    response = "Метод " + method + " не обрабатывается";
                    send(httpExchange, response, 405);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
