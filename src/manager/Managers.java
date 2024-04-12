package manager;

public  class Managers {

    public  TaskManager getDefault() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        return inMemoryTaskManager;
    }

    public  HistoryManager getDefaultHistory(){
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        return  inMemoryHistoryManager;
    }
}
