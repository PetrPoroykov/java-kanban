package manager;

public class Managers {

    public TaskManager getDefault() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager(getDefaultHistory());
        return inMemoryTaskManager;
    }

    public HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
