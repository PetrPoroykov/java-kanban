import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.HttpTaskServer;
import tasks.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {

    @BeforeEach
    void init() throws IOException {

        HttpTaskServer httpTaskServer = new HttpTaskServer();


        Task task1 = new Task("ЗАДАЧА1", TypesOfTasks.TASK, "Просто задача один", Status.NEW,
                (LocalDateTime.of(2025, 1, 1, 10, 0)), (Duration.ofMinutes(30)));
        HttpTaskServer.getTaskManager().createTask(task1);
        Task task2 = new Task("ЗАДАЧА2", TypesOfTasks.TASK, "Просто задача два", Status.NEW,
                (LocalDateTime.of(2025, 1, 1, 11, 0)), (Duration.ofMinutes(30)));
        HttpTaskServer.getTaskManager().createTask(task2);
        Task task3 = new Task("ЗАДАЧА3", TypesOfTasks.TASK, "Просто задача три", Status.NEW,
                (LocalDateTime.of(2025, 1, 1, 12, 0)), (Duration.ofMinutes(30)));
        HttpTaskServer.getTaskManager().createTask(task3);
        Epic epic1 = new Epic("Эпик1", "Просто эпик один ", Status.NEW);
        HttpTaskServer.getTaskManager().createEpic(epic1);
        Epic epic2 = new Epic("Эпик2", "Просто эпик два ", Status.NEW);
        HttpTaskServer.getTaskManager().createEpic(epic2);
        SubTask subTask1 = new SubTask("СУБТАСК1", TypesOfTasks.SUBTASK, "Просто субтаск один", Status.NEW,
                (LocalDateTime.of(2025, 1, 2, 10, 0)), (Duration.ofMinutes(30)),
                epic1.getId());
        HttpTaskServer.getTaskManager().createSubTask(subTask1);
        SubTask subTask2 = new SubTask("СУБТАСК2", TypesOfTasks.SUBTASK, "Просто субтаск два", Status.NEW,
                (LocalDateTime.of(2025, 1, 2, 11, 0)), (Duration.ofMinutes(30)),
                epic1.getId());
        HttpTaskServer.getTaskManager().createSubTask(subTask2);
        SubTask subTask3 = new SubTask("СУБТАСК3", TypesOfTasks.SUBTASK, "Просто субтаск три", Status.NEW,
                (LocalDateTime.of(2025, 1, 2, 12, 0)), (Duration.ofMinutes(30)),
                epic1.getId());
        HttpTaskServer.getTaskManager().createSubTask(subTask3);
        SubTask subTask4 = new SubTask("СУБТАСК4", TypesOfTasks.SUBTASK, "Просто субтаск четыре", Status.NEW,
                (LocalDateTime.of(2024, 12, 31, 18, 0)), (Duration.ofMinutes(30)),
                epic2.getId());
        HttpTaskServer.getTaskManager().createSubTask(subTask4);

        HttpTaskServer.start();
    }

    @DisplayName("Получение всех задач")
    @Test
    void shouldGetTasks() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        List<Task> spisok = HttpTaskServer.getGson().fromJson(response.body(), new TypeToken<ArrayList<Task>>() {
        }.getType());
        assertEquals(200, response.statusCode(), "Код ответа не соответствует ожидаемому");
        assertEquals(3, spisok.size(),
                "Количество полученных  задач не соответствует количеству задач которое храниться в менеджере");
    }

    @DisplayName("Получение всех подзадач")
    @Test
    void shouldGetSubTasks() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        List<SubTask> spisok = HttpTaskServer.getGson().fromJson(response.body(), new TypeToken<ArrayList<SubTask>>() {
        }.getType());
        assertEquals(200, response.statusCode(), "Код ответа не соответствует ожидаемому");
        assertEquals(4, spisok.size(),
                "Количество полученных  подзадач не соответствует количеству подзадач которое храниться в менеджере");
    }

    @DisplayName("Получение всех эпиков")
    @Test
    void shouldGetEpics() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        List<Epic> spisok = HttpTaskServer.getGson().fromJson(response.body(), new TypeToken<ArrayList<Epic>>() {
        }.getType());
        assertEquals(200, response.statusCode(), "Код ответа не соответствует ожидаемому");
        assertEquals(2, spisok.size(),
                "Количество полученных эпиков не соответствует количеству эпиков которое храниться в менеджере");
    }

    @DisplayName("Получение задачи по корректному ID")
    @Test
    void shouldGetTaskById() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/1");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Task task = HttpTaskServer.getGson().fromJson(response.body(), Task.class);
        assertEquals(200, response.statusCode(), "Код ответа не соответствует ожидаемому");
        assertEquals(1, task.getId(), "ID полученной задачи  не соответствует ID запрошенной задачи");
    }

    @DisplayName("Запрос задачи по  несуществующему ID")
    @Test
    void shouldNotGetTaskByMissingId() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/121");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response.statusCode(), "Код ответа не соответствует ожидаемому");
        assertTrue(response.body().equals(""), "Непустой ответ");
    }

    @DisplayName("Запрос подзадачи по  несуществующему ID")
    @Test
    void shouldNotGetSubTaskByMissingId() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/subtasks/121");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response.statusCode(), "Код ответа не соответствует ожидаемому");
        assertTrue(response.body().equals(""), "Непустой ответ");
    }

    @DisplayName("Запрос эпика по  несуществующему ID")
    @Test
    void shouldNotGetEpicByMissingId() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/epics/121");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        assertEquals(404, response.statusCode(), "Код ответа не соответствует ожидаемому");
        assertTrue(response.body().equals("Запрошен эпик с несуществующим ID 121"), "Ответ отличается от ожидаемого");
    }

    @DisplayName("Добавление новой задачи")
    @Test
    void shouldPostTask() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks");
        Task task4 = new Task("ЗАДАЧА3", TypesOfTasks.TASK, "Просто задача три", Status.NEW,
                (LocalDateTime.of(2025, 2, 1, 12, 0)), (Duration.ofMinutes(30)));
        task4.setEndTime();
        String task = HttpTaskServer.getGson().toJson(task4, Task.class);
        int countTask = HttpTaskServer.getTaskManager().getAllTask().size();

        HttpRequest request = HttpRequest.newBuilder().uri(uri)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(task)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int countTaskNew = HttpTaskServer.getTaskManager().getAllTask().size();

        assertEquals(201, response.statusCode(), "Код ответа не соответствует ожидаемому");
        assertEquals(countTaskNew, countTask + 1, "Количество задач не изменилось или выросло больше чем на одну");
    }

    @DisplayName("Обновление задачи по ID")
    @Test
    void shouldPostTaskById() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/2");
        Task task2 = new Task("ЗАДАЧА 2 ОБНОВЛЕННАЯ", TypesOfTasks.TASK, "Просто задача два", Status.NEW,
                (LocalDateTime.of(2025, 1, 1, 11, 0)), (Duration.ofMinutes(30)));
        task2.setEndTime();
        String task = HttpTaskServer.getGson().toJson(task2, Task.class);
        int countTask = HttpTaskServer.getTaskManager().getAllTask().size();
        HttpRequest request = HttpRequest.newBuilder().uri(uri)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(task)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int countTaskNew = HttpTaskServer.getTaskManager().getAllTask().size();
        assertEquals(201, response.statusCode(), "Код ответа не соответствует ожидаемому");
        assertEquals(countTask, countTaskNew, "Количество задач  изменилось");
    }

    @DisplayName("Обновление задачи по несущесуществующему  ID")
    @Test
    void shouldPostTaskByIncorrectId() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/22");
        Task task2 = new Task("ЗАДАЧА 2 ОБНОВЛЕННАЯ", TypesOfTasks.TASK, "Просто задача два", Status.NEW,
                (LocalDateTime.of(2025, 1, 1, 11, 0)), (Duration.ofMinutes(30)));
        task2.setEndTime();
        String task = HttpTaskServer.getGson().toJson(task2, Task.class);
        int countTask = HttpTaskServer.getTaskManager().getAllTask().size();
        HttpRequest request = HttpRequest.newBuilder().uri(uri)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(task)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int countTaskNew = HttpTaskServer.getTaskManager().getAllTask().size();;
        assertEquals(404, response.statusCode(), "Код ответа не соответствует ожидаемому");
        assertTrue((response.body().equals("Нет задачи с ID 22")), "Тело ответа не соответствует ожидаемому");

    }

    @DisplayName("Добавление новой подзадачи")
    @Test
    void shouldPostSubTask() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/subtasks");
        SubTask subTask = new SubTask("СУБТАСК4", TypesOfTasks.SUBTASK, "Просто субтаск четыре", Status.NEW,
                (LocalDateTime.of(2024, 12, 31, 20, 30)), (Duration.ofMinutes(30)),
                4);
        subTask.setEndTime();
        String task = HttpTaskServer.getGson().toJson(subTask, SubTask.class);
        int countTask = HttpTaskServer.getTaskManager().getAllSubtask().size();

        HttpRequest request = HttpRequest.newBuilder().uri(uri)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(task)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int countTaskNew = HttpTaskServer.getTaskManager().getAllSubtask().size();
        assertEquals(201, response.statusCode(), "Код ответа не соответствует ожидаемому");
        assertEquals(countTaskNew, countTask + 1, "Количество задач не изменилось или выросло больше чем на одну");
    }

    @DisplayName("Обновление подзадачи по ID")
    @Test
    void shouldPostSubTaskById() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/subtasks/9");
        SubTask subTask = new SubTask("СУБТАСК4", TypesOfTasks.SUBTASK, "Просто субтаск четыре", Status.NEW,
                (LocalDateTime.of(2024, 12, 31, 20, 30)), (Duration.ofMinutes(30)),
                4);
        subTask.setEndTime();

        String task = HttpTaskServer.getGson().toJson(subTask, Task.class);
        int countTask = HttpTaskServer.getTaskManager().getAllSubtask().size();

        HttpRequest request = HttpRequest.newBuilder().uri(uri)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(task)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int countTaskNew = HttpTaskServer.getTaskManager().getAllSubtask().size();
        assertEquals(201, response.statusCode(), "Код ответа не соответствует ожидаемому");
        assertEquals(countTask, countTaskNew, "Количество задач изменилось");
    }

    @DisplayName("Обновление подзадачи по несущестующему ID")
    @Test
    void shouldPostSubTaskByIncorrectId() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/subtasks/99");
        SubTask subTask = new SubTask("СУБТАСК4", TypesOfTasks.SUBTASK, "Просто субтаск четыре", Status.NEW,
                (LocalDateTime.of(2024, 12, 31, 20, 30)), (Duration.ofMinutes(30)),
                4);
        subTask.setEndTime();

        String task = HttpTaskServer.getGson().toJson(subTask, Task.class);
        int countTask = HttpTaskServer.getTaskManager().getAllSubtask().size();

        HttpRequest request = HttpRequest.newBuilder().uri(uri)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(task)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int countTaskNew = HttpTaskServer.getTaskManager().getAllSubtask().size();
        System.out.println("response.body " + response.body());
        assertEquals(404, response.statusCode(), "Код ответа не соответствует ожидаемому");
        assertTrue((response.body().equals("Нет подзадачи с ID 99")), "Тело ответа не соответствует ожидаемому");
    }

    @DisplayName("Добавление нового эпика")
    @Test
    void shouldPostEpic() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/epics");
        Epic epic0 = new Epic("Эпик1", "Просто эпик один ", Status.NEW);
        epic0.setEndTime();
        String epic = HttpTaskServer.getGson().toJson(epic0, Epic.class);
        int countTask = HttpTaskServer.getTaskManager().getAllEpic().size();
        HttpRequest request = HttpRequest.newBuilder().uri(uri)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(epic)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int countTaskNew = HttpTaskServer.getTaskManager().getAllEpic().size();
        assertEquals(201, response.statusCode(), "Код ответа не соответствует ожидаемому");
        assertEquals(countTaskNew, countTask + 1, "Количество эпиков не изменилось или выросло больше чем на один");
    }

    @DisplayName("Получение списка подзаадч по ID эпикa")
    @Test
    void shouldGetSubtasksByEpicID() throws IOException, InterruptedException {
        List<Integer> listSubtaskID = HttpTaskServer.getTaskManager().getEpic(4).getSubTaskIds();
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/epics/4/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        List<Epic> listSubtask = HttpTaskServer.getGson().fromJson(response.body(), new TypeToken<ArrayList<Epic>>() {
        }.getType());
        List<Integer> newListSubtaskID = new ArrayList<>();
        for (Epic epic : listSubtask) {
            newListSubtaskID.add(epic.getId());
        }
        assertEquals(200, response.statusCode(), "Код ответа не соответствует ожидаемому");
        assertArrayEquals(new List[]{listSubtaskID}, new List[]{newListSubtaskID}, "Списки подзадач не совпадают");
    }

    @DisplayName("Получение списка подзаадч по ID эпикa")
    @Test
    void shouldGetHistory() throws IOException, InterruptedException {
        HttpTaskServer.getTaskManager().getTask(3);
        HttpTaskServer.getTaskManager().getSubTask(8);
        HttpTaskServer.getTaskManager().getEpic(5);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/history");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        List<Integer> listGet = new ArrayList<>(Arrays.asList(3, 8, 5));
        List<Task> listHistory = HttpTaskServer.getGson().fromJson(response.body(), new TypeToken<ArrayList<Task>>() {
        }.getType());
        List<Integer>  histjryList = new ArrayList<>();
        for (Task task : listHistory) {
            histjryList.add(task.getId());
        }
        assertArrayEquals(new List[]{listGet}, new List[]{histjryList}, "Списки подзадач не совпадают");
        assertEquals(200, response.statusCode(), "Код ответа не соответствует ожидаемому");
    }

    @DisplayName("Получение списка по приоритету времени")
    @Test
    void shouldGetPrioritized() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/prioritized");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        List<Integer> savePrioritizedList = new ArrayList<>(Arrays.asList(9, 1, 2, 3, 6, 7, 8));
        List<Task> listPrioritized = HttpTaskServer.getGson().fromJson(response.body(), new TypeToken<ArrayList<Task>>() {
        }.getType());
        List<Integer>  listPrioritizedId = new ArrayList<>();
        for (Task task : listPrioritized) {
            listPrioritizedId.add(task.getId());
        }
        assertArrayEquals(new List[]{savePrioritizedList}, new List[]{listPrioritizedId}, "Списки подзадач не совпадают");
        assertEquals(200, response.statusCode(), "Код ответа не соответствует ожидаемому");
    }

    @DisplayName("Добавление новой задачи не валидной по времени")
    @Test
    void shouldNotPostTaskIncjrrectTime() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks");
        Task task4 = new Task("ЗАДАЧА3", TypesOfTasks.TASK, "Просто задача три", Status.NEW,
                (LocalDateTime.of(2025, 1, 1, 12, 0)), (Duration.ofMinutes(30)));
        task4.setEndTime();
        String task = HttpTaskServer.getGson().toJson(task4, Task.class);
        int countTask = HttpTaskServer.getTaskManager().getAllTask().size();

        HttpRequest request = HttpRequest.newBuilder().uri(uri)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(task)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int countTaskNew = HttpTaskServer.getTaskManager().getAllTask().size();

        assertEquals(406, response.statusCode(), "Код ответа не соответствует ожидаемому");
        assertEquals(countTaskNew, countTask , "Количество задач  изменилось");
    }


    @AfterEach
    void tearDown() {
        HttpTaskServer.stop();
    }
}