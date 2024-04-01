package tasks;

import java.util.ArrayList;

public class Epic extends Task{
    private ArrayList<Integer> subTaskIds = new ArrayList<>();

    public Epic(String title, String description, int id) {
        super(title, description, id, "NEW");
    }

    @Override
    public String toString() {
        return super.toString() + " ВХОДЯТ ПОДЗАДАЧИ: " + subTaskIds.toString();
    }

    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public  void cleanSubtaskIds() {
        subTaskIds.clear();
    }
    public void removeSubtaskId(Integer id) { //удаляем когда удаляется по id подзадача
        subTaskIds.remove(id);
    }
}