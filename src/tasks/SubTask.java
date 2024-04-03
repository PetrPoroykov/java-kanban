package tasks;

import java.util.Objects;

public class SubTask extends Task {

    private int EpicId;

    public SubTask(String title, String description, int id, Status status, int EpicId) {
        super(title, description, id, status);
        this.EpicId = EpicId;
    }


    public int getEpicId() {
        return EpicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return EpicId == subTask.EpicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), EpicId);
    }

    @Override
    public String toString() {

        return super.toString() + " НОМЕР EPIC: " + EpicId;
    }
}

