package manager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;


public interface TaskManager {


    Task createTask(Task task);

    Task updateTask(Task task);

    Epic createEpic(Epic epic);

    Epic updateEpic(Epic epic);

    SubTask createSubTask(SubTask subTask);

    SubTask updateSubtask(SubTask subTask);

    void updateEpicStatus(Epic epic);

    void deleteAllTask();

    void deleteAllEpic();

    void deleteAllSubtask();

    void deleteTaskById(int id);

    void deleteSubTaskById(Integer id);

    void deleteEpicById(int id);

    ArrayList<Task> getAllTask();

    ArrayList<Epic> getAllEpic();

    ArrayList<SubTask> getAllSubtask();

    ArrayList<SubTask> getSubtasksListByIdEpic(int id);

    List<Task> getHistory();

    Task getTask(int id);

    public SubTask getSubTask(int id);

    public SubTask getSubTaskUtil(int id);

    public Epic getEpic(int id);

    void updateEpicTime(Epic epic);

    public List<Task> getPrioritizedTasks();

    void doVerificationTask(Task task);
}