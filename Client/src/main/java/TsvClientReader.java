import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TsvClientReader {

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
}
