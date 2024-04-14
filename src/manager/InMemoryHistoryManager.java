package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private List<Task> lastViewedTasks = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        List trantransitList = new ArrayList();
        for (Task lastViewedTask : lastViewedTasks) {
            trantransitList.add(lastViewedTask);
        }
        return trantransitList;
    }

    @Override
    public List<Task> add(Task task) {
        if (task != null) {
            if (lastViewedTasks.contains(task)) {
                lastViewedTasks.remove(task);
            }
            lastViewedTasks.add(task);
            if (lastViewedTasks.size() > 10) {
                lastViewedTasks.remove(0);
            }
        }
        return lastViewedTasks;
    }
}
