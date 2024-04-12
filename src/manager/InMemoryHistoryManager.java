package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    public List<Task> lastTasks = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        return lastTasks;
    }

    @Override
    public List<Task> addView(Task task) {
        if (task != null) {
            lastTasks.add(task);
            if (lastTasks.size() > 10) {
                lastTasks.remove(0);
            }
        }
        return lastTasks;
    }
}
