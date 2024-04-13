package manager;

import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int nextId = 0;
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, SubTask> subTasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();

    HistoryManager inMemoryHistoryManager;

    public InMemoryTaskManager(HistoryManager inMemoryHistoryManager) {
        this.inMemoryHistoryManager = inMemoryHistoryManager;
    }

    @Override
    public Task createTask(Task task) { // Создание Task
        task.setId(nextId++);
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Task updateTask(Task task) { // Обновление Task
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) { // Создание Epic
        epic.setId(nextId++);
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public Epic updateEpic(Epic epic) { // Обновление  Epic
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        }
        return epic;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) { // Создание subTask
        if (epics.containsKey(subTask.getEpicId())) {
            subTask.setId(nextId++);
            subTasks.put(subTask.getId(), subTask);
            Epic epic = epics.get(subTask.getEpicId());
            epic.getSubTaskIds().add(subTask.getId());
            updateEpicStatus(epic);
        } else return null;
        return subTask;
    }

    @Override
    public SubTask updateSubtask(SubTask subTask) {  // обновление SubTask
        if (subTasks.containsKey(subTask.getId())) {
            if (epics.containsKey(subTask.getEpicId())) {
                subTasks.put(subTask.getId(), subTask);
                Epic epic = epics.get(subTask.getEpicId());
                updateEpicStatus(epic);
            }
        }
        return subTask;
    }

    @Override
    public void updateEpicStatus(Epic epic) { // установка статуса Epic
        int quantNew = 0;
        int quantDone = 0;
        ArrayList<Integer> subtasksIds = epic.getSubTaskIds();
        for (int id : subtasksIds) {
            SubTask subtask = subTasks.get(id);
            if (subtask.getStatus() == Status.NEW) {
                quantNew++;
            } else if (subtask.getStatus() == Status.DONE) {
                quantDone++;
            }
        }
        if (quantNew == subtasksIds.size()) {
            epic.setStatus(Status.NEW);
        } else if (quantDone == subTasks.size()) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public void deleteAllTask() { // удаление всех Task
        tasks.clear();
    }

    @Override
    public void deleteAllEpic() { // удаление всех Epic
        epics.clear();
        subTasks.clear();
    }

    @Override
    public void deleteAllSubtask() { // удаление всех SubTask
        for (SubTask subTask : subTasks.values()) {
            ArrayList<Integer> emptySubTaskIds = new ArrayList<>();
            epics.get(subTask.getEpicId()).setSubTaskIds(emptySubTaskIds);
            updateEpicStatus(epics.get(subTask.getEpicId()));
        }
        subTasks.clear();
    }

    @Override
    public void deleteTaskById(int id) { // удаление Task по номеру
        tasks.remove(id);
    }

    @Override
    public void deleteSubTaskById(Integer id) { // удаление SubTask  по номеру
        SubTask subTask = subTasks.get(id);
        Epic epic = epics.get(subTask.getEpicId());
        subTasks.remove(id);
        epic.getSubTaskIds().remove(id);
        updateEpicStatus(epic);
    }

    @Override
    public void deleteEpicById(int id) { // удаление  Epic по номеру
        Epic epic = epics.get(id);
        ArrayList<Integer> listSubTaskId = epic.getSubTaskIds();
        epics.remove(id);
        for (int i = 0; i < listSubTaskId.size(); i++) {
            subTasks.remove(listSubTaskId.get(i));
        }
    }

    @Override
    public ArrayList<Task> getAllTask() {  // получение списка всех Task
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getAllEpic() { // получение списка всех Epic
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<SubTask> getAllSubtask() { // получение списка всех SubTask
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public ArrayList<SubTask> getSubtasksListByIdEpic(int id) {  // список всех Подзадач по номеру Эпика
        ArrayList<SubTask> epicSubTasks = new ArrayList<>();
        for (int subtaskId : epics.get(id).getSubTaskIds()) {
            epicSubTasks.add(subTasks.get(subtaskId));
        }
        return epicSubTasks;
    }

    @Override
    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    public Task getTask(int id) {
        Task task = tasks.get(id);
        inMemoryHistoryManager.add(task);
        return task;
    }

    public SubTask getSubTask(int id) {
        SubTask subTask = subTasks.get(id);
        inMemoryHistoryManager.add(subTask);
        return subTask;
    }

    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        inMemoryHistoryManager.add(epic);
        return epic;
    }
}


