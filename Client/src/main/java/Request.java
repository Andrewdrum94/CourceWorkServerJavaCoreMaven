import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Request {

    private String title;
    private Date date;
    private long sum;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

    public Request(String title, long sum) {
        this.title = title;
        this.date = new Date();
        this.sum = sum;
    }

    public String toJson() {
        return "{\"title\": " + "\"" + title + "\", \"date\": " + "\"" + dateFormat.format(date) + "\", \"sum\": " + sum + "}";
    }

    public void toJs(String s) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter("request.json")) {
            out.println(s);
        }
    }



}
