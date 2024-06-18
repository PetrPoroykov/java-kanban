import manager.FileBackedTaskManager;
import manager.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tasks.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import static manager.FileBackedTaskManager.loadFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTaskManager> {

    @BeforeEach
    public void createManager() {
        taskManager = new FileBackedTaskManager();
        taskManager.inMemoryHistoryManager = new InMemoryHistoryManager();
    }

    @DisplayName("Создание новой задачи")
    @Override
    @Test
    public void shouldCreateTask() {
        super.shouldCreateTask();
    }

    @DisplayName("Обновление существующей задачи по валидному ID")
    @Override
    @Test
    public void shouldUpdateTask() {
        super.shouldUpdateTask();
    }

    @DisplayName("Создание новой  сборной задачи")
    @Override
    @Test
    public void shouldCreateEpic() {
        super.shouldCreateEpic();
    }

    @DisplayName("Обновление существующей сборной задачи по валидному ID")
    @Override
    @Test
    public void shouldUpdataEpic() {
        super.shouldUpdataEpic();
    }

    @DisplayName("Создание новой  подзадачи")
    @Override
    @Test
    public void shouldCreateSubTask() {
        super.shouldCreateSubTask();
    }

    @DisplayName("Обновление существующей  подзадачи по валидному ID")
    @Override
    @Test
    public void shouldUpdataSubTask() {
        super.shouldUpdataSubTask();
    }

    @DisplayName("Обновление cтатуса эпика")
    @Override
    @Test
    public void shouldUpdateEpicStatus() {
        super.shouldUpdateEpicStatus();
    }

    @DisplayName("Обновление времени эпика")
    @Override
    @Test
    public void shouldUpdateEpicTime() {
        super.shouldUpdateEpicTime();
    }

    @DisplayName("Должно выбрасываться ValidationException при пересечении по времени")
    @Override
    @Test
    public void exceptionShouldBeThrownAtTimeIntersection() {
        super.exceptionShouldBeThrownAtTimeIntersection();
    }

    @DisplayName("Задачи и подзадачи должны быть отсортированы по времени")
    @Override
    @Test
    public void shouldGetPrioritizedTasks() {
        super.shouldGetPrioritizedTasks();
    }

    @DisplayName("Просмотренные задачи должны помещаться в историю просмотров")
    @Override
    @Test
    public void viewedStoriesShouldFormBrowsingHistory() {
        super.viewedStoriesShouldFormBrowsingHistory();
    }

    @DisplayName("Все задачи, эпики и подзадачи должны быть записаны в файл и восстановлены из файла")
    @Test
    public void shouldSaveAndLoad() {
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

        FileBackedTaskManager curFileBackedTasksManager = loadFromFile("data/test.csv");

        assertEquals(taskManager.getAllTask().size(), curFileBackedTasksManager.getAllTask().size(),
                "Количество записанных в файл задач и количество востановленных из файла не совпадают");
        assertEquals(taskManager.getAllTask(), curFileBackedTasksManager.getAllTask(),
                "Списоки записанных и востановленных задач не совпадают");

        assertEquals(taskManager.getAllEpic().size(), curFileBackedTasksManager.getAllEpic().size(),
                "Количество записанных в файл эпиков и количество востановленных из файла не совпадают");
        assertEquals(taskManager.getAllEpic(), curFileBackedTasksManager.getAllEpic(),
                "Списоки записанных и востановленных задач не совпадают");

        assertEquals(taskManager.getAllSubtask().size(), curFileBackedTasksManager.getAllSubtask().size(),
                "Количество записанных в файл задач и количество востановленных из файла не совпадают");
        assertEquals(taskManager.getAllSubtask(), curFileBackedTasksManager.getAllSubtask(),
                "Списоки записанных и востановленных задач не совпадают");

        assertEquals(taskManager.getTask(1), curFileBackedTasksManager.getTask(1),
                "Записанная и восстановленная  из файла  задачи не совпадают");

        assertEquals(taskManager.getEpic(4), curFileBackedTasksManager.getEpic(4),
                "Записанная и восстановленная  из файла  'эпики не совпадают");

        assertEquals(taskManager.getSubTask(5), curFileBackedTasksManager.getSubTask(5),
                "Записанная и восстановленная  из файла  подзадачи не совпадают");
    }
}

