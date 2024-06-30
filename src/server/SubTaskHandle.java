package server;

import com.sun.net.httpserver.HttpExchange;
import exceotion.ValidationException;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.util.regex.Pattern;

public class SubTaskHandle extends BaseHttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            String method = httpExchange.getRequestMethod();
            String path = httpExchange.getRequestURI().getPath();
            String response = "";
            switch (method) {
                case "GET": {

                    if (Pattern.matches("^/subtasks/\\d+$", path)) {
                        String pathId = path.replaceFirst("/subtasks/", "");
                        int id = parsePathId(pathId);
                        response = HttpTaskServer.gson.toJson(HttpTaskServer.taskManager.getSubTask(id));
                        if (response.equals("null")) {
                            System.out.println("Нет подзадачи с таким номером");
                            send(httpExchange, "", 404);
                            break;
                        } else {
                            send(httpExchange, response, 200);
                        }
                    } else {
                        response = HttpTaskServer.gson.toJson(HttpTaskServer.taskManager.getAllSubtask());
                        send(httpExchange, response, 200);
                    }
                    break;
                }
                case "POST": {
                    String body = readText(httpExchange);
                    SubTask subTask = HttpTaskServer.gson.fromJson(body, SubTask.class);
                    if (Pattern.matches("^/subtasks/\\d+$", path)) {
                        String pathId = path.replaceFirst("/subtasks/", "");
                        int id = parsePathId(pathId);
                        boolean isPresentId = false;
                        for (Task task1 : HttpTaskServer.taskManager.getAllSubtask()) {
                            if (task1.getId() == id) {
                                isPresentId = true;
                                break;
                            }
                        }
                        if (isPresentId) {
                            subTask.setId(id);
                            try {
                                HttpTaskServer.taskManager.updateSubtask(subTask);
                            } catch (ValidationException exception) {
                                response = "Подзадача не может быть добавлена. Это время занято другой задачей или подзадачей!";
                                send(httpExchange, response, 406);
                                break;
                            }
                            response = HttpTaskServer.getGson().toJson(subTask, SubTask.class);
                            send(httpExchange, response, 201);
                            break;
                        } else {
                            response = "Нет подзадачи с ID " + id;
                            send(httpExchange, response, 404);
                        }
                    } else {
                        try {
                            HttpTaskServer.taskManager.createSubTask(subTask);
                        } catch (ValidationException exception) {
                            response = "Задачи пересекаются по времени ";
                            send(httpExchange, response, 406);
                            break;
                        }

                        response =  HttpTaskServer.getGson().toJson(subTask, SubTask.class);;
                        send(httpExchange, response, 201);
                    }
                    break;
                }
                case "DELETE": {
                    if (Pattern.matches("^/subtasks/\\d+$", path)) {
                        String pathId = path.replaceFirst("/subtasks/", "");
                        int id = parsePathId(pathId);
                        HttpTaskServer.taskManager.deleteSubTaskById(id);
                        response = "Удалили задачу номер " + id;
                        send(httpExchange, response, 200);
                    } else {
                        send(httpExchange, "Введен некоректный номер подзадачи для удаления", 405);
                    }
                    break;
                }
                default: {
                    response = "Метод " + method + " не обрабатывается";
                    System.out.println("Метод " + method + " не обрабатывается");
                    send(httpExchange, response, 405);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

