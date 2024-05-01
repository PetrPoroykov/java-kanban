import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTests {

    TaskManager inMemoryTaskManager = Managers.getDefault();


    @Test
    void shoudBeAssignValueSubTaskId123() {
        Epic epic = new Epic("СБОРНАЯ ЗАДАЧА 1", "Сборная задача 1", 0);
        inMemoryTaskManager.createEpic(epic);
        SubTask subTask = new SubTask("ПОДЗАДАЧА 1", "один",
                0, Status.NEW, 0);
        subTask.setId(123);
        assertEquals(123, subTask.getId());
    }

    @Test
    void taskInstancesShouldBeEqualWhenTheirIdAreEqual() { //проверьте, что экземпляры класса Task равны друг другу, если равен их id
        Task task = new Task("ЗАДАЧА 1", "Просто задача один", Status.NEW);
        inMemoryTaskManager.createTask(task);
        Task task2 = new Task("ЗАДАЧА 2", "Просто задача два", Status.NEW);
        inMemoryTaskManager.createTask(task2);
        Task task3 = new Task("ЗАДАЧА 3", "Просто задача три", Status.NEW);
        inMemoryTaskManager.createTask(task3);
        assertEquals(inMemoryTaskManager.getTask(task2.getId()), inMemoryTaskManager.getTask(task2.getId()));
    }

    @Test
    void epicInstancesShouldBeEqualWhenTheirIdAreEqual() { //проверьте, что наследники класса Task равны друг другу, если равен их id
        Epic epic = new Epic("СБОРНАЯ ЗАДАЧА 1", "Сборная задача 1", 0);
        inMemoryTaskManager.createEpic(epic);
        Epic epic2 = new Epic("СБОРНАЯ ЗАДАЧА 2 ", "Сборная задача 2", 0);
        inMemoryTaskManager.createEpic(epic2);
        assertEquals(inMemoryTaskManager.getEpic(epic2.getId()), inMemoryTaskManager.getEpic(epic2.getId()));
    }

    @Test
    void taskCannotBeAdded() {
        Task task = new Task("ЗАДАЧА 1", "Просто задача один", Status.NEW);
        Task savedTask = inMemoryTaskManager.createTask(task);
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = inMemoryTaskManager.getAllTask();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void epicCannotBeAdded() {
        Epic epic = new Epic("СБОРНАЯ ЗАДАЧА 1", "Сборная задача 1", 0);
        Epic savedEpic = inMemoryTaskManager.createEpic(epic);
        assertNotNull(savedEpic, "Эпик  не найден.");
        assertEquals(epic, savedEpic, "Эпики  не совпадают.");

        final List<Epic> epics = inMemoryTaskManager.getAllEpic();

        assertNotNull(epics, "Эпики не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество эпиков.");
        assertEquals(epic, epics.get(0), "Эпики не совпадают.");
    }

    @Test
    void subTaskCannotBeAdded() {
        Epic epic = new Epic("СБОРНАЯ ЗАДАЧА 1", "Сборная задача 1", 0);
        inMemoryTaskManager.createEpic(epic);
        SubTask subTask = new SubTask("ПОДЗАДАЧА 1", "один",
                0, Status.NEW, 0);
        SubTask savedSubTask = inMemoryTaskManager.createSubTask(subTask);

        assertNotNull(savedSubTask, "Подзадача  не найдена.");
        assertEquals(subTask, savedSubTask, "Подзадачи  не совпадают.");

        final List<SubTask> subTasks = inMemoryTaskManager.getAllSubtask();

        assertNotNull(subTasks, "Подзадачи не возвращаются.");
        assertEquals(1, subTasks.size(), "Неверное количество Подзадач.");
        assertEquals(subTask, subTasks.get(0), "Подзадачи не совпадают.");
    }

    @Test
    void epicShouldChangeStatusToInProgressWhenOneOfSubTasksIsDone() {
        Epic epic = new Epic("СБОРНАЯ ЗАДАЧА 1", "Сборная задача 1", 0);
        inMemoryTaskManager.createEpic(epic);
        assertEquals(Status.NEW, epic.getStatus(), "У нового эпика статус не равен NEW");
        SubTask subTask = new SubTask("ПОДЗАДАЧА 1", "один",
                0, Status.DONE, 0);
        inMemoryTaskManager.createSubTask(subTask);
        SubTask subTask1 = new SubTask("ПОДЗАДАЧА 1", "один",
                0, Status.NEW, 0);
        inMemoryTaskManager.createSubTask(subTask1);
        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "У эпика статус не поменялся на IN_PROGRESS");
    }

    @Test
    void whenTaskIsCalledItShouldBeAddedToBrowsingHistory() {
        Task task = new Task("ЗАДАЧА 1", "Просто задача один", Status.NEW);
        inMemoryTaskManager.createTask(task);
        Task task2 = new Task("ЗАДАЧА 2", "Просто задача два", Status.NEW);
        inMemoryTaskManager.createTask(task2);
        inMemoryTaskManager.getTask(1);
        List<Task> history = inMemoryTaskManager.getHistory();
        assertEquals(1, history.size(), "Размер истории неверный");
    }
}


