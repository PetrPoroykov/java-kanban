package manager;

import exceotion.ValidationException;
import tasks.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import tasks.Status;

public class InMemoryTaskManager implements TaskManager {
    private int nextId = 1;

    protected Map<Integer, Task> tasks = new HashMap<>();
    protected Map<Integer, SubTask> subTasks = new HashMap<>();
    protected Map<Integer, Epic> epics = new HashMap<>();

    public HistoryManager inMemoryHistoryManager;

    public InMemoryTaskManager() {
    }

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.inMemoryHistoryManager = historyManager;
    }

    @Override
    public Task createTask(Task task) {// Создание Task
        doVerificationTask(task);
        task.setId(nextId++);
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Task updateTask(Task task) { // Обновление Task
        doVerificationTask(task);
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
            updateEpicTime(epic);
        }
        return epic;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) { // Создание subTask
        doVerificationTask(subTask);
        if (epics.containsKey(subTask.getEpicId())) {
            subTask.setId(nextId++);
            subTasks.put(subTask.getId(), subTask);
            Epic epic = epics.get(subTask.getEpicId());
            epic.getSubTaskIds().add(subTask.getId());
            updateEpicStatus(epic);
            updateEpicTime(epic);
        } else return null;
        return subTask;
    }

    @Override
    public SubTask updateSubtask(SubTask subTask) {  // обновление SubTask
        doVerificationTask(subTask);
        if (subTasks.containsKey(subTask.getId())) {
            if (epics.containsKey(subTask.getEpicId())) {
                subTasks.put(subTask.getId(), subTask);
                Epic epic = epics.get(subTask.getEpicId());
                updateEpicStatus(epic);
                updateEpicTime(epic);
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
        for (Integer id : tasks.keySet()) {
            inMemoryHistoryManager.removeView(id);
        }
        tasks.clear();
    }

    @Override
    public void deleteAllEpic() { // удаление всех Epic
        for (Integer id : epics.keySet()) {
            inMemoryHistoryManager.removeView(id);
        }
        for (Integer id : subTasks.keySet()) {
            inMemoryHistoryManager.removeView(id);
        }
        epics.clear();
        subTasks.clear();
    }

    @Override
    public void deleteAllSubtask() { // удаление всех SubTask
        for (Integer id : subTasks.keySet()) {
            inMemoryHistoryManager.removeView(id);
        }
        for (SubTask subTask : subTasks.values()) {
            ArrayList<Integer> emptySubTaskIds = new ArrayList<>();
            epics.get(subTask.getEpicId()).setSubTaskIds(emptySubTaskIds);
            updateEpicStatus(epics.get(subTask.getEpicId()));
            updateEpicTime(epics.get(subTask.getEpicId()));
            epics.get(subTask.getEpicId()).setStartTime(LocalDateTime.now());

        }
        subTasks.clear();
    }

    @Override
    public void deleteTaskById(int id) { // удаление Task по номеру
        inMemoryHistoryManager.removeView(id);
        tasks.remove(id);
    }

    @Override
    public void deleteSubTaskById(Integer id) { // удаление SubTask  по номеру
        inMemoryHistoryManager.removeView(id);
        SubTask subTask = subTasks.get(id);
        Epic epic = epics.get(subTask.getEpicId());
        subTasks.remove(id);
        epic.getSubTaskIds().remove(id);
        updateEpicStatus(epic);
        updateEpicTime(epic);
    }

    @Override
    public void deleteEpicById(int id) { // удаление  Epic по номеру
        inMemoryHistoryManager.removeView(id);
        Epic epic = epics.get(id);
        ArrayList<Integer> listSubTaskId = epic.getSubTaskIds();
        epics.remove(id);
        for (int i = 0; i < listSubTaskId.size(); i++) {
            subTasks.remove(listSubTaskId.get(i));
            inMemoryHistoryManager.removeView(listSubTaskId.get(i));
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

    public SubTask getSubTaskUtil(int id) {
        SubTask subTask = subTasks.get(id);
        return subTask;
    }

    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        inMemoryHistoryManager.add(epic);
        return epic;
    }

    @Override
    public void updateEpicTime(Epic epic) {
        ArrayList<Integer> subtasks = epic.getSubTaskIds();
        if (!subtasks.isEmpty()) {
            LocalDateTime firstSubTaskStart = LocalDateTime.MAX;
            LocalDateTime lastSubTaskEnd = LocalDateTime.MIN;
            Duration sumDuration = Duration.ZERO;
            for (Integer subtaskId : subtasks) {
                SubTask actualSubTask = getSubTaskUtil(subtaskId);
                sumDuration = sumDuration.plus(actualSubTask.getDuration());
                if (actualSubTask.getStartTime() != null) {
                    if (actualSubTask.getStartTime().isBefore(firstSubTaskStart))
                        firstSubTaskStart = actualSubTask.getStartTime();
                    if (actualSubTask.getEndTime().isAfter(lastSubTaskEnd)) {
                        lastSubTaskEnd = actualSubTask.getEndTime();
                    }
                }
            }
            epic.setDuration(sumDuration);
            if (firstSubTaskStart.isBefore(LocalDateTime.MAX)) {
                epic.setStartTime(firstSubTaskStart);
                epic.setEndTime(lastSubTaskEnd);

            } else {
                epic.setStartTime(null);
                epic.setEndTime(null);

            }
        } else {
            epic.setDuration(Duration.ZERO);
            epic.setStartTime(null);
            epic.setEndTime(null);
        }
    }

    class TasksSorted implements Comparator<Task> {
        @Override
        public int compare(Task o1, Task o2) {

            if (o1.getStartTime() != null && o2.getStartTime() == null) {
                return -1;
            }
            if (o1.getStartTime() == null && o2.getStartTime() != null) {
                return 1;
            }
            if (o1.getStartTime() == null && o2.getStartTime() == null) {
                return o1.getId() - o2.getId();
            }
            if (o1.getStartTime().isBefore(o2.getStartTime())) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    public List<Task> getPrioritizedTasks() {
        TasksSorted tasksSorted = new TasksSorted();
        Set<Task> setTasks = new TreeSet<>(tasksSorted);
        setTasks.addAll(tasks.values());
        setTasks.addAll(subTasks.values());
        return new ArrayList<Task>(setTasks);
    }

    public void doVerificationTask(Task task) throws ValidationException {
        HashMap<Integer, Task> tasksAndSubTasks = new HashMap<>();
        tasksAndSubTasks.putAll(tasks);
        tasksAndSubTasks.putAll(subTasks);
        for (Task value : tasksAndSubTasks.values()) {
            if (value.getId() == task.getId()) continue;

            if (task.getEndTime().isAfter(value.getStartTime()) & task.getStartTime().isBefore(value.getEndTime())) {
                throw new ValidationException("Задачи пересекаются по времени!");
            }
        }
    }


    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager1 = new InMemoryTaskManager();
        Task task1 = new Task("таск1", TypesOfTasks.TASK, "описание таск1", Status.NEW,
                LocalDateTime.of(2024, Month.JANUARY, 1, 10, 0), Duration.ofMinutes(30));
        inMemoryTaskManager1.createTask(task1);
        Task task2 = new Task("таск2", TypesOfTasks.TASK, "описание таск2", Status.NEW,
                LocalDateTime.of(2024, Month.JANUARY, 1, 10, 0), Duration.ofMinutes(30));
        inMemoryTaskManager1.createTask(task2);
        Task task3 = new Task("таск3", TypesOfTasks.TASK, "описание таск3", Status.NEW,
                LocalDateTime.of(2024, Month.JANUARY, 1, 12, 0), Duration.ofMinutes(30));
        inMemoryTaskManager1.createTask(task3);
        Task task4 = new Task();
        inMemoryTaskManager1.createTask(task4);

        for (Task task : inMemoryTaskManager1.getAllTask()) {
            System.out.println(task.toString() + "  " + task.getStartTime() + "  " + task.getEndTime());
        }
    }
}


