import java.io.*;
import java.util.*;

public class TsvReader {

    public TsvReader() {

    }

    public static List<String> readTsvProducts(File file) {
        List<String> prod = new ArrayList<>();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                String[] lineItems = line.split("\t");
                prod.add(lineItems[0]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return prod;
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
