package manager;

import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private int nextId = 0;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();

    public Task createTask(Task task) { // Создание Task
        task.setId(nextId++);
        tasks.put(task.getId(), task);
        return task;
    }

    public Task updateTask(Task task) { // Обновление Task
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
        return task;
    }

    public Epic createEpic(Epic epic) { // Создание Epic
        epic.setId(nextId++);
        epics.put(epic.getId(), epic);
        return epic;
    }

    public Epic updateEpic(Epic epic) { // Обновление  Epic
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        }
        return epic;
    }

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

    public void deleteAllTask() { // удаление всех Task
        tasks.clear();
    }

    public void deleteAllEpic() { // удаление всех Epic
        epics.clear();
        subTasks.clear();
    }

    public void deleteAllSubtask() { // удаление всех SubTask
        for (SubTask subTask : subTasks.values()) {
            ArrayList<Integer> subTaskIds = new ArrayList<>();
            epics.get(subTask.getEpicId()).setSubTaskIds(subTaskIds);
            updateEpicStatus(epics.get(subTask.getEpicId()));
        }
        subTasks.clear();
    }

    public void deleteTaskById(int id) { // удаление Task по номеру
        tasks.remove(id);
    }

    public void deleteSubTaskById(Integer id) { // удаление SubTask  по номеру
        SubTask subTask = subTasks.get(id);
        Epic epic = epics.get(subTask.getEpicId());
        subTasks.remove(id);
        epic.getSubTaskIds().remove(id);
        updateEpicStatus(epic);
    }

    public void deleteEpicById(int id) { // удаление  Epic по номеру
        Epic epic = epics.get(id);
        ArrayList<Integer> listId = epic.getSubTaskIds();
        epics.remove(id);
        for (int i = 0; i < listId.size(); i++) {
            subTasks.remove(listId.get(i));
        }
    }

    public ArrayList<Task> getAllTask() {  // получение списка всех Task
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getAllEpic() { // получение списка всех Epic
        return new ArrayList<>(epics.values());
    }

    public ArrayList<SubTask> getAllSubtask() { // получение списка всех SubTask
        return new ArrayList<>(subTasks.values());
    }

    public ArrayList<SubTask> getSubtasksListByIdEpic(int id) {  // список всех Подзадач по номеру Эпика
        ArrayList<SubTask> epicSubTasks = new ArrayList<>();
        for (int subtaskId : epics.get(id).getSubTaskIds()) {
            epicSubTasks.add(subTasks.get(subtaskId));
        }
        return epicSubTasks;
    }
}

