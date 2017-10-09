import java.sql.Date;

public class Note {
    private int id;
    private Date date;
    private String info;

    public Note() {}

    public Note(int id, Date date, String info) {
        this.id = id;
        this.date = date;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
