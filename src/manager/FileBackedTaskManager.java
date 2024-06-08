package manager;

import exceotion.ManagerIOException;
import exceotion.ValidationException;
import tasks.*;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class FileBackedTaskManager extends InMemoryTaskManager {

    static String location = "data/test.csv";


    public FileBackedTaskManager() {
    }

    private int nextId = 1;

    public void save() throws ManagerIOException {
        try {
            Writer fileWriter = Files.newBufferedWriter(Paths.get(location), StandardCharsets.UTF_8);
            fileWriter.write("id, type, name, status, description, epicID, startTime, duration\n");
            for (Task task : getAllTask()) {
                task.setTypeTask(TypesOfTasks.TASK);
                String st = toStringToWrite(task);
                fileWriter.write(st + "такой переменной нет, " + task.getStartTime().toString() + ", " + task.getDuration().toString() + "\n");
            }
            for (Epic epic : getAllEpic()) {
                epic.setTypeTask(TypesOfTasks.EPIC);
                String st = toStringToWrite(epic);
                fileWriter.write(st + "такой переменной нет, " + epic.getStartTime() + ", " + epic.getDuration() + "\n");
            }
            for (SubTask subTask : getAllSubtask()) {
                subTask.setTypeTask(TypesOfTasks.SUBTASK);
                String st = toStringToWrite(subTask);
                fileWriter.write(st + subTask.getEpicId() + ", " + subTask.getStartTime() + ", " + subTask.getDuration() + "\n");
            }

            fileWriter.close();

        } catch (IOException e) {
            throw new ManagerIOException("Ошибка при записи в файл");
        }
    }

    public static FileBackedTaskManager loadFromFile(String location) throws ManagerIOException {
        FileBackedTaskManager newFileBackedTasksManager = new FileBackedTaskManager();
        newFileBackedTasksManager.inMemoryHistoryManager = new InMemoryHistoryManager();
        try {
            List<String> lines = Files.readAllLines(Paths.get(location), StandardCharsets.UTF_8);
            if (lines.size() <= 1) {
                newFileBackedTasksManager = null;
                return newFileBackedTasksManager;
            }
            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(", ");
                switch (parts[1]) {
                    case ("TASK"):
                        Task task = new Task();
                        task.setId(parseInt(parts[0]));
                        task.setTitle(parts[2]);
                        task.setStatus(Status.valueOf(parts[3]));
                        task.setDescription(parts[4]);
                        task.setStartTime(LocalDateTime.parse(parts[6]));
                        task.setDuration(Duration.parse(parts[7]));

                        newFileBackedTasksManager.tasks.put(task.getId(), task);

                        break;
                    case ("EPIC"):
                        Epic epic = new Epic();
                        epic.setId(parseInt(parts[0]));
                        epic.setTitle(parts[2]);
                        epic.setStatus(Status.valueOf(parts[3]));
                        epic.setDescription(parts[4]);
                        epic.setStartTime(LocalDateTime.parse(parts[6]));
                        newFileBackedTasksManager.epics.put(epic.getId(), epic);
                        break;
                    case ("SUBTASK"):
                        SubTask subTask = new SubTask();
                        subTask.setId(parseInt(parts[0]));
                        subTask.setTitle(parts[2]);
                        subTask.setStatus(Status.valueOf(parts[3]));
                        subTask.setDescription(parts[4]);
                        subTask.setEpicId(parseInt(parts[5]));
                        subTask.setStartTime(LocalDateTime.parse(parts[6]));
                        subTask.setDuration(Duration.parse(parts[7]));
                        newFileBackedTasksManager.subTasks.put(subTask.getId(), subTask);
                        newFileBackedTasksManager.getEpic(subTask.getEpicId()).getSubTaskIds().add(subTask.getId());
                        newFileBackedTasksManager.updateEpicTime(newFileBackedTasksManager.getEpic(subTask.getEpicId()));
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
            throw new ManagerIOException("Ошибка при чтении из файла");

        }
        return newFileBackedTasksManager;
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
        save();
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        super.createEpic(epic);
        save();
        return epic;
    }

    @Override
    public Epic updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
        return epic;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        super.createSubTask(subTask);
        save();
        return subTask;
    }

    @Override
    public SubTask updateSubtask(SubTask subTask) {
        super.updateSubtask(subTask);
        save();
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

    @Override
    public void updateEpicTime(Epic epic) {
        super.updateEpicTime(epic);
    }

    public FileBackedTaskManager(HistoryManager historyManager) {
        super(historyManager);
    }

    @Override
    public SubTask getSubTaskUtil(int id) {
        return super.getSubTaskUtil(id);
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return super.getPrioritizedTasks();
    }

    @Override
    public void doVerificationTask(Task task) throws ValidationException {
        super.doVerificationTask(task);
    }

}
