package manager;

import tasks.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private static File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public FileBackedTaskManager() {
    }

    private int nextId = 1;

    public void save() throws ManagerSaveException {
        try {
            Writer fileWriter = new FileWriter("data/test.csv");
            fileWriter.write("id,type,name,status,description,epic\n");
            for (Task task : getAllTask()) {
                task.setTypeTask(TypesOfTasks.TASK);
                String st = toStringToWrite(task);
                fileWriter.write(st + "\n");
            }
            for (Epic epic : getAllEpic()) {
                epic.setTypeTask(TypesOfTasks.EPIC);
                String st = toStringToWrite(epic);
                fileWriter.write(st + "\n");
            }
            for (SubTask subTask : getAllSubtask()) {
                subTask.setTypeTask(TypesOfTasks.SUBTASK);
                String st = toStringToWrite(subTask) + subTask.getEpicId();
                fileWriter.write(st + "\n");
            }

            fileWriter.close();

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при записи в файл");
        }
    }


    public static FileBackedTaskManager loadFromFile(File file) throws ManagerSaveException {
        FileBackedTaskManager newFileBackedTasksManager = new FileBackedTaskManager(file);
        try {
            int shift = 3;
            List<String> lines = Files.readAllLines(file.toPath());
            if (lines.size() <= 1) {
                newFileBackedTasksManager = null;
                return newFileBackedTasksManager;
            }
            if (!lines.get(lines.size() - 2).isEmpty()) {
                shift = 1;
            }
            for (int i = 1; i <= lines.size() - shift; i++) {
                String[] parts = lines.get(i).split(", ");
                switch (parts[1]) {
                    case ("TASK"):
                        Task task = new Task();
                        task.setId(parseInt(parts[0]));
                        task.setTitle(parts[2]);
                        task.setStatus(Status.valueOf(parts[3]));
                        task.setDescription(parts[4]);
                        newFileBackedTasksManager.tasks.put(task.getId(), task);
                        break;
                    case ("EPIC"):
                        Epic epic = new Epic();
                        epic.setId(parseInt(parts[0]));
                        epic.setTitle(parts[2]);
                        epic.setStatus(Status.valueOf(parts[3]));
                        epic.setDescription(parts[4]);
                        newFileBackedTasksManager.epics.put(epic.getId(), epic);
                        break;
                    case ("SUBTASK"):
                        SubTask subTask = new SubTask();
                        subTask.setId(parseInt(parts[0]));
                        subTask.setTitle(parts[2]);
                        subTask.setStatus(Status.valueOf(parts[3]));
                        subTask.setEpicId(parseInt(parts[5]));
                        newFileBackedTasksManager.subTasks.put(subTask.getId(), subTask);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + parts[1]);
                }
                if (newFileBackedTasksManager.nextId <= parseInt(parts[0]))
                    newFileBackedTasksManager.nextId = parseInt(parts[0]) + 1;
            }
            if (lines.get(lines.size() - 2).isEmpty()) {
                String[] savedHistoryLine = lines.get(lines.size() - 1).split(", ");
                for (String part : savedHistoryLine) {
                    for (Task task : newFileBackedTasksManager.getAllTask()) {
                        if (Integer.parseInt(part) == task.getId()) {
                            newFileBackedTasksManager.inMemoryHistoryManager.add(newFileBackedTasksManager.tasks.get(Integer.parseInt(part)));
                            break;
                        }
                    }
                    for (Epic epic : newFileBackedTasksManager.getAllEpic()) {
                        if (Integer.parseInt(part) == epic.getId()) {
                            newFileBackedTasksManager.inMemoryHistoryManager.add(newFileBackedTasksManager.epics.get(Integer.parseInt(part)));
                            break;
                        }
                    }
                    for (SubTask subTask : newFileBackedTasksManager.getAllSubtask()) {
                        if (Integer.parseInt(part) == subTask.getId()) {
                            newFileBackedTasksManager.inMemoryHistoryManager.add(newFileBackedTasksManager.subTasks.get(Integer.parseInt(part)));
                        }
                    }
                }
            }
        } catch (
                IOException e) {
            throw new ManagerSaveException("Ошибка при чтении из файла");

        }
        return newFileBackedTasksManager;
    }

    public FileBackedTaskManager(HistoryManager historyManager) {
        super(historyManager);
    }

    @Override
    public Task createTask(Task task) {
        super.createTask(task);
        save();
        return task;
    }

    @Override
    public Task updateTask(Task task) {
        super.updateTask(task);
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        super.createEpic(epic);
        return epic;
    }

    @Override
    public Epic updateEpic(Epic epic) {
        super.updateEpic(epic);
        return epic;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        super.createSubTask(subTask);
        return subTask;
    }

    @Override
    public SubTask updateSubtask(SubTask subTask) {
        super.updateSubtask(subTask);
        return subTask;
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        super.updateEpicStatus(epic);
        save();
    }

    @Override
    public void deleteAllTask() {
        super.deleteAllTask();
        save();
    }

    @Override
    public void deleteAllEpic() {
        super.deleteAllEpic();
        save();
    }

    @Override
    public void deleteAllSubtask() {
        super.deleteAllSubtask();
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteSubTaskById(Integer id) {
        super.deleteSubTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public ArrayList<Task> getAllTask() {
        return super.getAllTask();
    }

    @Override
    public ArrayList<Epic> getAllEpic() {
        return super.getAllEpic();
    }

    @Override
    public ArrayList<SubTask> getAllSubtask() {
        return super.getAllSubtask();
    }

    @Override
    public ArrayList<SubTask> getSubtasksListByIdEpic(int id) {
        return super.getSubtasksListByIdEpic(id);
    }

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }

    @Override
    public Task getTask(int id) {
        return super.getTask(id);
    }

    @Override
    public SubTask getSubTask(int id) {
        return super.getSubTask(id);
    }

    @Override
    public Epic getEpic(int id) {
        return super.getEpic(id);
    }

    public String toStringToWrite(Task task) {
        return task.getId() + ", " + task.getTypeTask() + ", " + task.getTitle() + ", " + task.getStatus()
                + ", " + task.getDescription() + ", ";
    }

    public static void main(String[] args) {

        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Что вы хотите сделать? ");
        System.out.println("1 - Записать в файл тестовый набор данных");
        System.out.println("2 - Востановить данные из файла");
        int command = scanner.nextInt();

        if (command == 1) {


            Task task1 = new Task("Первая задача", "Просто задача один", Status.NEW);
            fileBackedTaskManager.createTask(task1);
            Task task2 = new Task("Вторая задача", "Просто задача два", Status.NEW);
            fileBackedTaskManager.createTask(task2);
            Task task3 = new Task("Третья задача", "Просто задача три", Status.NEW);
            fileBackedTaskManager.createTask(task3);
            Epic epic1 = new Epic("СБОРНАЯ ЗАДАЧА ОДИН", "Сборная задача 1", 0);
            fileBackedTaskManager.createEpic(epic1);
            Epic epic2 = new Epic("СБОРНАЯ ЗАДАЧА ДВА ", "Сборная задача 2", 0);
            fileBackedTaskManager.createEpic(epic2);
            SubTask subTask = new SubTask("ПОДЗАДАЧА 1", "один",
                    0, Status.NEW, 4);
            fileBackedTaskManager.createSubTask(subTask);


            System.out.println("СПИСОК ВСЕХ ЗАДАЧ(Task)");
            for (Task t : fileBackedTaskManager.getAllTask()) {
                System.out.println(t);
            }
            System.out.println("");
            System.out.println("СПИСОК ВСЕХ СБОРНЫХ ЗАДАЧ (Epic)");
            for (Epic e : fileBackedTaskManager.getAllEpic()) {
                System.out.println(e);
            }
            System.out.println("");
            System.out.println("СПИСОК ВСЕХ ПОДЗАДАЧ (SubTask)");
            for (SubTask st : fileBackedTaskManager.getAllSubtask()) {
                System.out.println(st);
            }
        }
        if (command == 2) {
            FileBackedTaskManager fileBackedTaskManagerTest = loadFromFile(new File("data/test.csv"));


            System.out.println("ВОСТАНОВЛЕННЫ СПИСОК ВСЕХ ЗАДАЧ(Task)");
            for (Task t : fileBackedTaskManagerTest.getAllTask()) {
                System.out.println(t);
            }
            System.out.println("");
            System.out.println("ВОСТАНОВЛЕННЙ СПИСОК ВСЕХ СБОРНЫХ ЗАДАЧ (Epic)");
            for (Epic e : fileBackedTaskManagerTest.getAllEpic()) {
                System.out.println(e);
            }
            System.out.println("");
            System.out.println("ВОСТАНОВЛЕННЙ СПИСОК ВСЕХ ПОДЗАДАЧ (SubTask)");
            for (SubTask st : fileBackedTaskManagerTest.getAllSubtask()) {
                System.out.println(st);
            }
        }
    }
}
