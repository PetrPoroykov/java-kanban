package manager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private int nextId = 1;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();

    public Task createTask(Task task) { // Создание Task
        task.setId(nextId);
        nextId++;
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
        epic.setId(nextId);
        nextId++;
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
        if (epics.containsKey(subTask.getIdEpic())) {
            subTask.setId(nextId);
            nextId++;
            subTasks.put(subTask.getId(), subTask);
            Epic epic = epics.get(subTask.getIdEpic());
            epic.getSubTaskIds().add(subTask.getId());
            updateEpicStatus(epic);
        } else return null;
        return subTask;
    }

    public SubTask updateSubtask(SubTask subTask) {  // обновление SubTask
        if (subTasks.containsKey(subTask.getId())) {
            if (epics.containsKey(subTask.getIdEpic())) {
                subTasks.put(subTask.getId(), subTask);
                Epic epic = epics.get(subTask.getIdEpic());
                updateEpicStatus(epic);
            }
        }
        return subTask;
    }

    public void updateEpicStatus(Epic epic) { // установка статуса Epic
        int quantNew = 0;
        int quantDone = 0;
        ArrayList<Integer> subtasks = epic.getSubTaskIds();
        for (int list : subtasks) {
            SubTask subtask = subTasks.get(list);
            if (subtask.getStatus() == "NEW") {
                quantNew++;
            }  else if (subtask.getStatus() == "DONE") {
                quantDone++;
            }
        }
        if (quantNew == subtasks.size()) {
            epic.setStatus("NEW");
        } else if (quantDone == subTasks.size()) {
            epic.setStatus("DONE");
        } else {
            epic.setStatus("IN_PROGRESS");
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
            Epic curEpic = epics.get(subTask.getIdEpic());
            curEpic.cleanSubtaskIds();
            curEpic.setStatus("NEW");
        }
        subTasks.clear();
    }

    public void deleteTaskById (int id) { // удаление Task по номеру
        tasks.remove(id);
    }

    public void deleteSubTaskById(int id){ // удаление SubTask  по номеру
        SubTask subTask = subTasks.get(id);
        Epic epic = epics.get(subTask.getIdEpic());
        subTasks.remove(id);
        epic.removeSubtaskId(subTask.getId());
        updateEpicStatus(epic);
    }

    public void deleteEpicById(int id) { // удаление  Epic по номеру
        Epic epic = epics.get(id);
        ArrayList<Integer> list = epic.getSubTaskIds();
        epics.remove(id);
        for (int i = 0; i < list.size(); i++) {
            subTasks.remove(list.get(i));
        }
        updateEpicStatus(epic);
    }

    public ArrayList<Task> getAllTask() {  // получение списка всех Task
        ArrayList<Task> listTask = new ArrayList<Task>(tasks.values());
        return listTask;
    }

    public ArrayList<Epic> getAllEpic() { // получение списка всех Epic
        ArrayList<Epic> listEpic = new ArrayList<Epic>(epics.values());
        return listEpic;
    }

    public ArrayList<SubTask> getAllSubtask() { // получение списка всех SubTask
        ArrayList<SubTask> listSubTask = new ArrayList<SubTask>(subTasks.values());
        return listSubTask;
    }

    public ArrayList<SubTask> getSubtasksListByIdEpic (int id) {  // список всех Подзадач по номеру Эпика
        ArrayList<SubTask> epicSubTasks = new ArrayList<>();
        Epic epic = epics.get(id);
        for(int subtaskId : epic.getSubTaskIds()) {
            epicSubTasks.add(subTasks.get(subtaskId));
        }
        return epicSubTasks;
    }
}

