package server;

import com.sun.net.httpserver.HttpExchange;
import exceotion.ValidationException;
import tasks.Task;

import java.io.IOException;
import java.util.regex.Pattern;

public class TaskHandle extends BaseHttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            String method = httpExchange.getRequestMethod();
            String path = httpExchange.getRequestURI().getPath();
            String response = "";
            switch (method) {
                case "GET": {
                    if (Pattern.matches("^/tasks/\\d+$", path)) {
                        String pathId = path.replaceFirst("/tasks/", "");
                        int id = parsePathId(pathId);
                        response = HttpTaskServer.gson.toJson(HttpTaskServer.taskManager.getTask(id));
                        if (response.equals("null")) {
                            System.out.println("Запршена задача с несуществующим ID");
                            send(httpExchange, "", 404);
                            break;
                        } else {
                            send(httpExchange, response, 200);
                        }
                    } else {
                        response = HttpTaskServer.gson.toJson(HttpTaskServer.taskManager.getAllTask());
                        send(httpExchange, response, 200);
                    }
                    break;
                }
                case "POST": {
                    String body = readText(httpExchange);
                    Task task = HttpTaskServer.gson.fromJson(body, Task.class);
                    if (Pattern.matches("^/tasks/\\d+$", path)) {
                        String pathId = path.replaceFirst("/tasks/", "");
                        int id = parsePathId(pathId);
                        boolean isPresentId = false;
                        for (Task task1 : HttpTaskServer.taskManager.getAllTask()) {
                            if (task1.getId() == id) {
                                isPresentId = true;
                                break;
                            }
                        }
                        if (isPresentId) {
                            task.setId(id);
                            try {
                                HttpTaskServer.taskManager.updateTask(task);
                            } catch (ValidationException exception) {
                                response = "Задача не может быть добавлена. Это время занято другой задачей или подзадачей!";
                                send(httpExchange, response, 406);
                                break;
                            }
                            response = HttpTaskServer.getGson().toJson(task, Task.class);
                            send(httpExchange, response, 201);
                            break;
                        } else {
                        response = "Нет задачи с ID " + id;
                        send(httpExchange, response, 404);
                        }

                    } else {
                        try {
                            task.setId(0);
                            HttpTaskServer.taskManager.createTask(task);
                        } catch (ValidationException exception) {
                            response = "Задача не может быть добавлена. Это время занято!";
                            send(httpExchange, response, 406);
                            break;
                        }
                        response = HttpTaskServer.getGson().toJson(task, Task.class);
                        send(httpExchange, response, 201);
                    }
                    break;
                }
                case "DELETE": {
                    if (Pattern.matches("^/tasks/\\d+$", path)) {
                        String pathId = path.replaceFirst("/tasks/", "");
                        int id = parsePathId(pathId);
                        HttpTaskServer.taskManager.deleteTaskById(id);
                        response = "Удалили задачу номер " + id;
                        send(httpExchange, response, 200);
                    } else {
                        send(httpExchange, "Введен некоректный номер задачи для удаления", 405);
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
