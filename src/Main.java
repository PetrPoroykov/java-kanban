import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import manager.Manager;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        Manager manager = new Manager();

        Task task = new Task("ЗАДАЧА 1", "Просто задача один", "NEW");
        manager.createTask(task);
        Task task2 = new Task("ЗАДАЧА 2", "Просто задача два", "NEW");
        manager.createTask(task2);
        Task task3 = new Task("ЗАДАЧА 3", "Просто задача три", "NEW");
        manager.createTask(task3);
        Task task4 = new Task("ЗАДАЧА 4-2", "Просто задача четыре-два", 2, "NEW");
        manager.updateTask(task4);

        Epic epic = new Epic("СБОРНАЯ ЗАДАЧА 1", "Сборная задача 1", 0);
        manager.createEpic(epic);
        Epic epic2 = new Epic("СБОРНАЯ ЗАДАЧА 2 ", "Сборная задача 2", 0);
        manager.createEpic(epic2);
        Epic epic3 = new Epic("СБОРНАЯ ЗАДАЧА 3", "Сборная задача 3", 0);
        manager.createEpic(epic3);
        Epic epic4 = new Epic("СБОРНАЯ ЗАДАЧА 4", "Сборная задача 4", 0);
        manager.createEpic(epic4);

        SubTask subTask = new SubTask("ПОДЗАДАЧА 1", "один",
                0, "NEW", 5);
        manager.createSubTask(subTask);
        SubTask subTask2 = new SubTask("ПОДЗАДАЧА 2", "два",
                0, "NEW", 5);
        manager.createSubTask(subTask2);
        SubTask subTask3 = new SubTask("ПОДЗАДАЧА 3", "три",
                0, "NEW", 5);
        manager.createSubTask(subTask3);
        SubTask subTask4 = new SubTask("ПОДЗАДАЧА 4", "четыре",
                0, "NEW", 6);
        manager.createSubTask(subTask4);
        SubTask subTask5 = new SubTask("ПОДЗАДАЧА 5", "пять",
                0, "NEW", 6);
        manager.createSubTask(subTask5);
        SubTask subTask6 = new SubTask("ПОДЗАДАЧА 6", "шесть",
                0, "NEW", 6);
        manager.createSubTask(subTask6);
        SubTask subTask7 = new SubTask("ПОДЗАДАЧА 7", "7",
                0, "DONE", 6);
        manager.createSubTask(subTask7);

        System.out.println(manager.getAllTask());
        manager.deleteTaskById(1);
        System.out.println("");
        System.out.println(manager.getAllTask());

//        System.out.println(manager.getSubtasksListByIdEpic(6));
        manager.deleteSubTaskById(10);
    }
}
