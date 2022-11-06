import java.io.*;
import java.util.*;

public class TsvReader {

    public TsvReader() {

    }

    public static Map<String, String> readTsvCategories(File file) {
        Map<String, String> categories = new HashMap<>();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                String[] lineItems = line.split("\t");
                String product = lineItems[0];
                String category = lineItems[1];
                categories.put(product, category);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }
}
