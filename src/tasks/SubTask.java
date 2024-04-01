package tasks;

public class SubTask extends Task{

    private int idEpic;

    public SubTask(String title, String description, int id, String status, int idEpic) {
        super(title, description, id, status);
        this.idEpic = idEpic;
    }

    public int getIdEpic() {
        return idEpic;
    }

    @Override
    public String toString() {

        return super.toString() + " НОМЕР EPIC: " + idEpic;
    }
}

