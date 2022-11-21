import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExpensesTest {

    private Expenses expensesClass;

    private Map<String, String> categories;
    private RequestDto dto;
    private static String date;


    @BeforeEach
    void load() {
        categories = TsvReader.readTsvCategories(new File("categories.tsv"));
        expensesClass = new Expenses(Expenses.initHashMap(categories));
        date = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        dto = new RequestDto("хлеб", date, 50);
    }

    @Test
    void calcExpenses() {
        expensesClass.calcNewExpenses(categories, dto);
        Map<String, Long> expectedExpenses = new HashMap<>();
        for (Map.Entry<String, String> category : categories.entrySet()) {
            expectedExpenses.put(category.getValue(), 0L);
        }
        expectedExpenses.put("другое", 50L);
        Assertions.assertEquals(expectedExpenses, expensesClass.getExpenses());
    }

    @Test
    void testOfSendMaxCategory() {
        String expectedResult = "{\"MaxCategory\": {\"category\": \"другое\", \"sum\": 50}}";
        expensesClass.calcNewExpenses(categories, dto);
        Assertions.assertEquals(expectedResult, expensesClass.sendJsonMaxCategory());
    }

}
