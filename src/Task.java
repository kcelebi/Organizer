import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {

    private String taskName;
    private int status; //0: incomplete, 1: complete
    private String strdate;
    private Date taskDate;
    private String link;

    public Task(String task, int stat, String date,String url, int mode) throws ParseException {
        taskName = task;
        status = stat;
        taskDate = new SimpleDateFormat("MM/dd/yyyy").parse(date);
        link = url;
        strdate = date;
        if(mode == 0)
            addtoFile();
    }

    public String getTask() {
        return taskName;
    }

    public int getStatus() {
        return status;
    }

    public Date getDate() {
        return taskDate;
    }

    public String getLink() {
        return link;
    }

    public void setTaskName(String task) {
        taskName = task;
    }

    public void setDate(String date) throws ParseException {
        String[] dateArr = date.split("/"); //input should be mm/dd/yyyy
        taskDate = new SimpleDateFormat("MM/dd/yyyy").parse(date);
    }

    public void setLink(String url) {
        link= url;
    }

    public void addtoFile() {
        String add = getTask().replace(" ","_") + "      "+getStatus()+ "      "+strdate + "      "+getLink() + "\n";
        try {
            Files.write(Paths.get("~/Projects/Organizer/data/tasks.txt"), add.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {}
    }
}
