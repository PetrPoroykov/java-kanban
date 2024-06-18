package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;


public class Task {
    private String title;
    private TypesOfTasks typeTask;
    private String description;
    private int id;
    private Status status;
    private LocalDateTime startTime = LocalDateTime.now();
    private Duration duration = Duration.ZERO;

    private LocalDateTime endTime = startTime.plus(duration);


    public Task(String title, TypesOfTasks typeTask, String description, Status status, LocalDateTime startTime, Duration duration) {
        this.title = title;
        this.typeTask = typeTask;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = startTime.plus(duration);

    }

    public Task(String title, String description, int id, Status status) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public Task(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Task() {
    }

    public void setEndTime() {
        this.endTime = this.startTime.plus(this.duration);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getTitle() {
        return title;
    }

    public TypesOfTasks getTypeTask() {
        return typeTask;
    }

    public void setTypeTask(TypesOfTasks typeTask) {
        this.typeTask = typeTask;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return getId() == task.getId() && Objects.equals(getTitle(),
                task.getTitle()) && Objects.equals(getDescription(),
                task.getDescription()) && Objects.equals(getStatus(),
                task.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getDescription(), getId(), getStatus());
    }

    @Override
    public String toString() {
        return "ЗАДАЧА  \n  {" +
                "ID=" + id +
                " title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}

