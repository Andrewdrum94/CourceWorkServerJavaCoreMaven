import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Request {

    private final String title;
    private final Date date;
    private final long sum;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

    public Request(String title, long sum) {
        this.title = title;
        this.date = new Date();
        this.sum = sum;
    }

    public String stringToJson() {
        return "{\"title\": " + "\"" + title + "\", \"date\": " + "\"" + dateFormat.format(date) + "\", \"sum\": " + sum + "}";
    }

    public void toJs(String s) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter("request.json")) {
            out.println(s);
        }
    }
}
