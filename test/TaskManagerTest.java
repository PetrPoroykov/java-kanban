import exceotion.ValidationException;
import tasks.Status;
import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TypesOfTasks;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;


abstract class TaskManagerTest<T extends TaskManager> {

    T taskManager;


    public void shouldCreateTask() {
        Task task1 = new Task("таск1", TypesOfTasks.TASK, "описание таск1", Status.NEW,
                LocalDateTime.of(2024, Month.JANUARY, 1, 12, 0), Duration.ofMinutes(33));
        taskManager.createTask(task1);
        Task task2 = new Task("таск2", TypesOfTasks.TASK, "описание таск2", Status.NEW,
                LocalDateTime.of(2024, Month.JANUARY, 1, 12, 33), Duration.ofMinutes(30));
        taskManager.createTask(task2);
        Task task3 = new Task("таск3", TypesOfTasks.TASK, "описание таск3", Status.NEW,
                LocalDateTime.of(2024, Month.JANUARY, 2, 12, 0), Duration.ofMinutes(30));
        taskManager.createTask(task3);

        Task savedTask = taskManager.getTask(1);

        assertNotNull(savedTask, "Задача не найдена");
        assertEquals(task1, savedTask, "Задачи не совпадают");
        assertNotEquals(0, taskManager.getAllTask().size(), "Задачи не попадают в список");
        assertEquals(3, taskManager.getAllTask().size(), "Неверное количество задач в списке");
    }

    public void shouldUpdateTask() {
        Task task1 = new Task("таск1", TypesOfTasks.TASK, "описание таск1", Status.NEW,
                LocalDateTime.of(2024, Month.JANUARY, 1, 12, 0), Duration.ofMinutes(33));
        taskManager.createTask(task1);

        Task task2 = new Task("таск2", TypesOfTasks.TASK, "описание таск2", Status.NEW,
                LocalDateTime.of(2024, Month.JANUARY, 1, 13, 0), Duration.ofMinutes(33));
        taskManager.createTask(task2);
        Task task3 = new Task("таск3", TypesOfTasks.TASK, "описание таск3", Status.NEW,
                LocalDateTime.of(2024, Month.JANUARY, 1, 14, 0), Duration.ofMinutes(33));
        task3.setId(1);
        taskManager.updateTask(task3);

        assertEquals("описание таск3", taskManager.getTask(1).getDescription(), "Данные не обновились");
    }

    public void shouldCreateEpic() {
        Epic epic1 = new Epic("Сборная задача 1", "Описание сборной задачи 1 ", Status.NEW);
        taskManager.createEpic(epic1);
        Epic epic2 = new Epic("Сборная задача 2", "Описание сборной задачи 2 ", Status.NEW);
        taskManager.createEpic(epic2);
        assertNotNull(taskManager.getAllEpic(), "Задача не создана");
        assertEquals("Описание сборной задачи 1 ", taskManager.getEpic(1).getDescription(), "Данные не совпадают");
        assertNotNull("Задачи не попадают в список");
        assertEquals(2, taskManager.getAllEpic().size(), "Неверное количество задач в списке");
    }

    public void shouldUpdataEpic() {
        Epic epic1 = new Epic("Сборная задача 1", "Описание сборной задачи 1 ", Status.NEW);
        taskManager.createEpic(epic1);
        Epic epic2 = new Epic("Сборная задача 2", "Описание сборной задачи 2 ", Status.NEW);
        epic2.setId(1);
        taskManager.updateEpic(epic2);

        final Epic savedEpic = taskManager.getEpic(1);

        assertEquals("Описание сборной задачи 2 ", taskManager.getEpic(1).getDescription(), "Данные не обновились");
    }

    public void shouldCreateSubTask() {
        Epic epic1 = new Epic("Сборная задача 1", "Описание сборной задачи 1 ", Status.NEW);
        taskManager.createEpic(epic1);
        Epic epic2 = new Epic("Сборная задача 2", "Описание сборной задачи 2 ", Status.NEW);
        taskManager.createEpic(epic2);
        SubTask subTask1 = new SubTask("Подзадача 1", TypesOfTasks.SUBTASK, "Описание подзадачи 1",
                Status.NEW, LocalDateTime.of(2024, Month.JANUARY, 1, 18, 0),
                Duration.ofMinutes(20), 2);
        taskManager.createSubTask(subTask1);
        SubTask subTask2 = new SubTask("Подзадача 2", TypesOfTasks.SUBTASK, "Описание подзадачи 2",
                Status.NEW, LocalDateTime.of(2024, Month.JANUARY, 1, 19, 0),
                Duration.ofMinutes(20), 2);
        taskManager.createSubTask(subTask2);
        assertNotNull(taskManager.getAllSubtask(), "Задача не создана");
        assertEquals("Описание подзадачи 2", taskManager.getSubTask(4).getDescription(), "Данные не совпадают");
        assertEquals(2, taskManager.getAllSubtask().size(), "Неверное количество задач в списке");
    }

    public void shouldUpdataSubTask() {
        Epic epic1 = new Epic("Сборная задача 1", "Описание сборной задачи 1 ", Status.NEW);
        taskManager.createEpic(epic1);
        Epic epic2 = new Epic("Сборная задача 2", "Описание сборной задачи 2 ", Status.NEW);
        taskManager.createEpic(epic2);
        SubTask subTask1 = new SubTask("Подзадача 1", TypesOfTasks.SUBTASK, "Описание подзадачи 1",
                Status.NEW, LocalDateTime.of(2024, Month.JANUARY, 1, 18, 0),
                Duration.ofMinutes(20), 2);
        taskManager.createSubTask(subTask1);
        SubTask subTask2 = new SubTask("Подзадача 2", TypesOfTasks.SUBTASK, "Описание подзадачи 2",
                Status.NEW, LocalDateTime.of(2024, Month.JANUARY, 1, 19, 0),
                Duration.ofMinutes(20), 2);
        subTask2.setId(3);
        taskManager.updateSubtask(subTask2);

        assertEquals("Описание подзадачи 2", taskManager.getSubTask(3).getDescription(), "Данные не обновились");
    }

    public void shouldUpdateEpicStatus() {
        Epic epic1 = new Epic("Сборная задача 1", "Описание сборной задачи 1 ", Status.NEW);
        taskManager.createEpic(epic1);
        Epic epic2 = new Epic("Сборная задача 2", "Описание сборной задачи 2 ", Status.NEW);
        taskManager.createEpic(epic2);
        SubTask subTask1 = new SubTask("Подзадача 1", TypesOfTasks.SUBTASK, "Описание подзадачи 1",
                Status.NEW, LocalDateTime.of(2024, Month.JANUARY, 1, 18, 20),
                Duration.ofMinutes(20), 2);
        taskManager.createSubTask(subTask1);
        SubTask subTask2 = new SubTask("Подзадача 2", TypesOfTasks.SUBTASK, "Описание подзадачи 2",
                Status.NEW, LocalDateTime.of(2024, Month.JANUARY, 1, 18, 40),
                Duration.ofMinutes(20), 2);
        subTask2.setStatus(Status.IN_PROGRESS);
        taskManager.createSubTask(subTask2);
        ;

        assertEquals(Status.IN_PROGRESS, taskManager.getEpic(2).getStatus(),
                "Статус эпика не меняется при добавлении новой подзадачи");
    }

    public void shouldUpdateEpicTime() {
        Epic epic1 = new Epic("Сборная задача 1", "Описание сборной задачи 1 ", Status.NEW);
        taskManager.createEpic(epic1);
        Epic epic2 = new Epic("Сборная задача 2", "Описание сборной задачи 2 ", Status.NEW);
        taskManager.createEpic(epic2);
        SubTask subTask1 = new SubTask("Подзадача 1", TypesOfTasks.SUBTASK, "Описание подзадачи 1",
                Status.NEW, LocalDateTime.of(2024, Month.JANUARY, 1, 18, 0),
                Duration.ofMinutes(20), 2);
        taskManager.createSubTask(subTask1);
        assertEquals(LocalDateTime.of(2024, Month.JANUARY, 1, 18, 0),
                taskManager.getEpic(2).getStartTime(),
                "Время начала эпика не соответствует времени начала подзадачи");
        SubTask subTask2 = new SubTask("Подзадача 2", TypesOfTasks.SUBTASK, "Описание подзадачи 2",
                Status.NEW, LocalDateTime.of(2024, Month.JANUARY, 1, 19, 0),
                Duration.ofMinutes(21), 2);
        taskManager.createSubTask(subTask2);
        assertEquals(LocalDateTime.of(2024, Month.JANUARY, 1, 19, 21),
                taskManager.getEpic(2).getEndTime(),
                "Время окончания  эпика не соответствует времени окончания самой поздней  подзадачи");
        assertEquals(Duration.ofMinutes(41), taskManager.getEpic(2).getDuration(),
                "Суммарная длительность  эпика не равна сумме длительностей подзадач");
        taskManager.deleteSubTaskById(3);
        assertEquals(Duration.ofMinutes(21), taskManager.getEpic(2).getDuration(),
                "Суммарная длительность  эпика не равна сумме длительностей подзадач после удаления подзадачи");
        taskManager.deleteAllSubtask();
        assertEquals(Duration.ofMinutes(0), taskManager.getEpic(2).getDuration(),
                "Суммарная длительность  эпика не равна 0  после удаления всех подзадач");
    }

    public void exceptionShouldBeThrownAtTimeIntersection() {
        ValidationException exc = Assertions.assertThrows(ValidationException.class, () -> {

            Task task1 = new Task("таск1", TypesOfTasks.TASK, "описание таск1", Status.NEW,
                    LocalDateTime.of(2024, Month.JANUARY, 1, 12, 0), Duration.ofMinutes(33));
            taskManager.createTask(task1);
            Task task2 = new Task("таск2", TypesOfTasks.TASK, "описание таск2", Status.NEW,
                    LocalDateTime.of(2024, Month.JANUARY, 1, 12, 32), Duration.ofMinutes(30));
            taskManager.createTask(task2);
            Task task3 = new Task("таск3", TypesOfTasks.TASK, "описание таск3", Status.NEW,
                    LocalDateTime.of(2024, Month.JANUARY, 2, 12, 0), Duration.ofMinutes(30));
            taskManager.createTask(task3);
        });
        assertEquals("Задачи пересекаются по времени!", exc.getMessage());
    }

    public void shouldGetPrioritizedTasks() {

        Task task1 = new Task("таск1", TypesOfTasks.TASK, "описание таск1", Status.NEW,
                LocalDateTime.of(2024, Month.JANUARY, 2, 12, 0), Duration.ofMinutes(33));
        taskManager.createTask(task1);
        Task task2 = new Task("таск2", TypesOfTasks.TASK, "описание таск2", Status.NEW,
                LocalDateTime.of(2024, Month.JANUARY, 3, 14, 33), Duration.ofMinutes(30));
        taskManager.createTask(task2);
        Task task3 = new Task("таск3", TypesOfTasks.TASK, "описание таск3", Status.NEW,
                LocalDateTime.of(2024, Month.JANUARY, 2, 10, 0), Duration.ofMinutes(30));
        taskManager.createTask(task3);
        Epic epic1 = new Epic("Сборная задача 1", "Описание сборной задачи 1 ", Status.NEW);
        taskManager.createEpic(epic1);
        SubTask subTask1 = new SubTask("Подзадача 1", TypesOfTasks.SUBTASK, "Описание подзадачи 1",
                Status.NEW, LocalDateTime.of(2024, Month.JANUARY, 3, 12, 20),
                Duration.ofMinutes(20), 4);
        taskManager.createSubTask(subTask1);
        SubTask subTask2 = new SubTask("Подзадача 2", TypesOfTasks.SUBTASK, "Описание подзадачи 2",
                Status.NEW, LocalDateTime.of(2024, Month.JANUARY, 3, 16, 40),
                Duration.ofMinutes(20), 4);
        taskManager.createSubTask(subTask2);
        List<Task> testListTask = Arrays.asList(task3, task1, subTask1, task2, subTask2);
        List<Task> listPrioritizedTasks = new ArrayList<>(taskManager.getPrioritizedTasks());
        assertEquals(testListTask, taskManager.getPrioritizedTasks(), "Неверный список задач и подзадач по  временному приоритету");
    }

    public void viewedStoriesShouldFormBrowsingHistory() {
        Task task1 = new Task("таск1", TypesOfTasks.TASK, "описание таск1", Status.NEW,
                LocalDateTime.of(2024, Month.JANUARY, 2, 12, 0), Duration.ofMinutes(33));
        taskManager.createTask(task1);
        Task task2 = new Task("таск2", TypesOfTasks.TASK, "описание таск2", Status.NEW,
                LocalDateTime.of(2024, Month.JANUARY, 3, 14, 33), Duration.ofMinutes(30));
        taskManager.createTask(task2);
        Task task3 = new Task("таск3", TypesOfTasks.TASK, "описание таск3", Status.NEW,
                LocalDateTime.of(2024, Month.JANUARY, 2, 10, 0), Duration.ofMinutes(30));
        taskManager.createTask(task3);
        Epic epic1 = new Epic("Сборная задача 1", "Описание сборной задачи 1 ", Status.NEW);
        taskManager.createEpic(epic1);
        SubTask subTask1 = new SubTask("Подзадача 1", TypesOfTasks.SUBTASK, "Описание подзадачи 1",
                Status.NEW, LocalDateTime.of(2024, Month.JANUARY, 3, 12, 20),
                Duration.ofMinutes(20), 4);
        taskManager.createSubTask(subTask1);
        SubTask subTask2 = new SubTask("Подзадача 2", TypesOfTasks.SUBTASK, "Описание подзадачи 2",
                Status.NEW, LocalDateTime.of(2024, Month.JANUARY, 3, 16, 40),
                Duration.ofMinutes(20), 4);
        taskManager.createSubTask(subTask2);

        taskManager.getTask(1);
        taskManager.getTask(3);
        taskManager.getTask(2);
        taskManager.getEpic(4);
        taskManager.getTask(3);
        taskManager.getSubTask(5);
        List<Task> testListTask = Arrays.asList(task1, task2, epic1, task3, subTask1);
        assertEquals(testListTask, taskManager.getHistory(), "Неверный вывод истории просмотров");
    }
}