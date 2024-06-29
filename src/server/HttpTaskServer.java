package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import manager.Managers;
import manager.TaskManager;
import tasks.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {
    public static final int PORT = 8080;
    protected static HttpServer server;
    protected static Gson gson;
    protected static TaskManager taskManager;

    public HttpTaskServer() throws IOException {
        taskManager = Managers.getDefault();
        gson = Managers.getGeson();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", new TaskHandle());
        server.createContext("/subtasks", new SubTaskHandle());
        server.createContext("/epics", new EpicHandle());
        server.createContext("/history", new HistoryHandle());
        server.createContext("/prioritized", new PrioritizedHandle());
    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        Task task1 = new Task("ЗАДАЧА1", TypesOfTasks.TASK, "Просто задача один", Status.NEW,
                (LocalDateTime.of(2025, 1, 1, 10, 0)), (Duration.ofMinutes(30)));
        taskManager.createTask(task1);
        Task task2 = new Task("ЗАДАЧА2", TypesOfTasks.TASK, "Просто задача два", Status.NEW,
                (LocalDateTime.of(2025, 1, 1, 11, 0)), (Duration.ofMinutes(30)));
        taskManager.createTask(task2);
        Task task3 = new Task("ЗАДАЧА3", TypesOfTasks.TASK, "Просто задача три", Status.NEW,
                (LocalDateTime.of(2025, 1, 1, 12, 0)), (Duration.ofMinutes(30)));
        taskManager.createTask(task3);
        Epic epic1 = new Epic("Эпик1", "Просто эпик один ", Status.NEW);
        taskManager.createEpic(epic1);
        Epic epic2 = new Epic("Эпик2", "Просто эпик два ", Status.NEW);
        taskManager.createEpic(epic2);
        SubTask subTask1 = new SubTask("СУБТАСК1", TypesOfTasks.SUBTASK, "Просто субтаск один", Status.NEW,
                (LocalDateTime.of(2025, 1, 2, 10, 0)), (Duration.ofMinutes(30)),
                epic1.getId());
        taskManager.createSubTask(subTask1);
        SubTask subTask2 = new SubTask("СУБТАСК2", TypesOfTasks.SUBTASK, "Просто субтаск два", Status.NEW,
                (LocalDateTime.of(2025, 1, 2, 11, 0)), (Duration.ofMinutes(30)),
                epic1.getId());
        taskManager.createSubTask(subTask2);
        SubTask subTask3 = new SubTask("СУБТАСК3", TypesOfTasks.SUBTASK, "Просто субтаск три", Status.NEW,
                (LocalDateTime.of(2025, 1, 2, 12, 0)), (Duration.ofMinutes(30)),
                epic1.getId());
        taskManager.createSubTask(subTask3);
        SubTask subTask4 = new SubTask("СУБТАСК4", TypesOfTasks.SUBTASK, "Просто субтаск четыре", Status.NEW,
                (LocalDateTime.of(2025, 1, 3, 13, 0)), (Duration.ofMinutes(30)),
                epic2.getId());
        taskManager.createSubTask(subTask4);

        start();
    }

    public static void start() {
        System.out.println("Стартовал сервер на " + PORT + " порту.");
        server.start();
    }

    public static void stop() {
        server.stop(0);
        System.out.println("Сервер на " + HttpTaskServer.PORT + " порту остановлен.");
    }

    public static TaskManager getTaskManager() {
        return taskManager;
    }

    public static HttpServer getServer() {
        return server;
    }

    public static Gson getGson() {
        return gson;
    }
}
