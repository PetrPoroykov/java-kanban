package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class SubTask extends Task {

    private int epicId;

    public SubTask(String title, String description, int id, Status status, int epicId) {
        super(title, description, id, status);
        this.epicId = epicId;
    }

    public SubTask() {
    }

    public SubTask(String title, TypesOfTasks typeTask, String description, Status status, LocalDateTime startTime, Duration duration, int epicId) {
        super(title, typeTask, description, status, startTime, duration);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return epicId == subTask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    @Override
    public String toString() {

        return super.toString() + " НОМЕР EPIC: " + epicId;
    }
}

