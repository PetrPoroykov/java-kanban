import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        Managers managers = new Managers();
        TaskManager inMemoryTaskManager = managers.getDefault();

        Task task = new Task("ЗАДАЧА 1", "Просто задача один", Status.NEW);
        inMemoryTaskManager.createTask(task);
        Task task2 = new Task("ЗАДАЧА 2", "Просто задача два", Status.NEW);
        inMemoryTaskManager.createTask(task2);
        Task task3 = new Task("ЗАДАЧА 3", "Просто задача три", Status.NEW);
        inMemoryTaskManager.createTask(task3);
        Task task4 = new Task("ЗАДАЧА 4-2", "Просто задача четыре-два", 2, Status.NEW);
        inMemoryTaskManager.updateTask(task4);

        Epic epic = new Epic("СБОРНАЯ ЗАДАЧА 1", "Сборная задача 1", 0);
        inMemoryTaskManager.createEpic(epic);
        Epic epic2 = new Epic("СБОРНАЯ ЗАДАЧА 2 ", "Сборная задача 2", 0);
        inMemoryTaskManager.createEpic(epic2);
        Epic epic3 = new Epic("СБОРНАЯ ЗАДАЧА 3", "Сборная задача 3", 0);
        inMemoryTaskManager.createEpic(epic3);
        Epic epic4 = new Epic("СБОРНАЯ ЗАДАЧА 4", "Сборная задача 4", 0);
        inMemoryTaskManager.createEpic(epic4);

        SubTask subTask = new SubTask("ПОДЗАДАЧА 1", "один",
                0, Status.NEW, 5);
        inMemoryTaskManager.createSubTask(subTask);
        SubTask subTask2 = new SubTask("ПОДЗАДАЧА 2", "два",
                0, Status.NEW, 5);
        inMemoryTaskManager.createSubTask(subTask2);
        SubTask subTask3 = new SubTask("ПОДЗАДАЧА 3", "три",
                0, Status.NEW, 5);
        inMemoryTaskManager.createSubTask(subTask3);
        SubTask subTask4 = new SubTask("ПОДЗАДАЧА 4", "четыре",
                0, Status.NEW, 6);
        inMemoryTaskManager.createSubTask(subTask4);
        SubTask subTask5 = new SubTask("ПОДЗАДАЧА 5", "пять",
                0, Status.NEW, 6);
        inMemoryTaskManager.createSubTask(subTask5);
        SubTask subTask6 = new SubTask("ПОДЗАДАЧА 6", "шесть",
                0, Status.NEW, 6);
        inMemoryTaskManager.createSubTask(subTask6);
        SubTask subTask7 = new SubTask("ПОДЗАДАЧА 7", "7",
                0, Status.DONE, 6);
        inMemoryTaskManager.createSubTask(subTask7);

        System.out.println("Задачи");
        System.out.println(inMemoryTaskManager.getAllTask());
        System.out.println("Эпики");
        System.out.println(inMemoryTaskManager.getAllEpic());
        System.out.println("Подзадачи");
        System.out.println(inMemoryTaskManager.getAllSubtask());

        inMemoryTaskManager.deleteTaskById(1);
        System.out.println("");
        System.out.println(">>>>>     inMemoryTaskManager.deleteTaskById(1)     <<<<<");
        System.out.println("Задачи");
        System.out.println(inMemoryTaskManager.getAllTask());
        System.out.println("Эпики");
        System.out.println(inMemoryTaskManager.getAllEpic());
        System.out.println("Подзадачи");
        System.out.println(inMemoryTaskManager.getAllSubtask());

        inMemoryTaskManager.deleteSubTaskById(13);
        System.out.println("");
        System.out.println(">>>>>      inMemoryTaskManager.deleteSubTaskById(13)     <<<<<");
        System.out.println("Задачи");
        System.out.println(inMemoryTaskManager.getAllTask());
        System.out.println("Эпики");
        System.out.println(inMemoryTaskManager.getAllEpic());
        System.out.println("Подзадачи");
        System.out.println(inMemoryTaskManager.getAllSubtask());

        inMemoryTaskManager.deleteEpicById(5);
        System.out.println("");
        System.out.println(">>>>>      inMemoryTaskManager.deleteEpicById(5)     <<<<<");
        System.out.println("Задачи");
        System.out.println(inMemoryTaskManager.getAllTask());
        System.out.println("Эпики");
        System.out.println(inMemoryTaskManager.getAllEpic());
        System.out.println("Подзадачи");
        System.out.println(inMemoryTaskManager.getAllSubtask());


        System.out.println("История просмотров. Последнии 10 просмотренных задач");
        inMemoryTaskManager.getTask(0);
        inMemoryTaskManager.getTask(2);
        inMemoryTaskManager.getEpic(3);
        inMemoryTaskManager.getEpic(6);
        inMemoryTaskManager.getSubTask(10);
        inMemoryTaskManager.getSubTask(11);
        inMemoryTaskManager.getSubTask(12);
        inMemoryTaskManager.getEpic(4);
        inMemoryTaskManager.getSubTask(10);
        inMemoryTaskManager.getSubTask(11);
        inMemoryTaskManager.getSubTask(12);
        inMemoryTaskManager.getTask(0);
        inMemoryTaskManager.getTask(2);
        inMemoryTaskManager.getEpic(3);
        inMemoryTaskManager.getEpic(6);
        System.out.println(inMemoryTaskManager.getHistory());

        inMemoryTaskManager.deleteAllSubtask();
        System.out.println("");
        System.out.println(">>>>>       inMemoryTaskManager.deleteAllSubtask()     <<<<<");
        System.out.println("Задачи");
        System.out.println(inMemoryTaskManager.getAllTask());
        System.out.println("Эпики");
        System.out.println(inMemoryTaskManager.getAllEpic());
        System.out.println("Подзадачи");
        System.out.println(inMemoryTaskManager.getAllSubtask());
    }
}
