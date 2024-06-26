package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {


    private ArrayList<Integer> subTaskIds = new ArrayList<>();

//    public Epic(String title, String description, int id) {
//        super(title, description, id, Status.NEW);
//    }

    public Epic(String title, String description, Status status) {
        super(title, description, status);
    }

    public Epic(String title, TypesOfTasks typeTask, String description, Status status, LocalDateTime startTime, Duration duration) {
        super(title, typeTask, description, status, startTime, duration);
    }

    public Epic() {
    }


    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void setSubTaskIds(ArrayList<Integer> subTaskIds) {
        this.subTaskIds = subTaskIds;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Epic epic)) return false;
        if (!super.equals(o)) return false;
        return subTaskIds.equals(epic.subTaskIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTaskIds);
    }

    @Override
    public String toString() {
        return super.toString() + " ВХОДЯТ ПОДЗАДАЧИ: " + subTaskIds.toString();
    }


}