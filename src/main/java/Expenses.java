import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

@ToString
public class Expenses {
    @JsonProperty("expenses")
    private Map<String, Long> expenses;

    public Expenses(Map<String, Long> expenses) {
        this.expenses = expenses;
    }

    public Expenses() {
    }

    public Map<String, Long> getExpenses() {
        return expenses;
    }

    public void setExpenses(Map<String, Long> expenses) {
        this.expenses = expenses;
    }

    public static Map<String, Long> initHashMap(Map<String, String> categories) {
        Map<String, Long> expenses = new HashMap<>();
        for (Map.Entry<String, String> category : categories.entrySet()) {
            expenses.put(category.getValue(), 0L);
        }
        expenses.put("другое", 0L);
        return expenses;
    }

    public static Map<String, Long> loadDataFromJson() {
        try {
            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.USE_LONG_FOR_INTS, true);
            Expenses exp = mapper.readValue(new File("expenses.json"), Expenses.class);
            return exp.getExpenses();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void calcNewExpenses(Map<String, String> categories, RequestDto dto) {
        Optional<String> categoryOpt = categories.keySet().stream()
                .filter(value -> dto.getTitle().equals(value))
                .findFirst();
        if (categoryOpt.isPresent()) {
            String category = categories.get(categoryOpt.get());
            long price = (expenses.get(category) + dto.getSum());
            expenses.put(category, price);
        } else {
            String category = "другое";
            long price = expenses.get(category) + dto.getSum();
            expenses.put(category, price);
        }
    }


    public void saveExpensesJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File("expenses.json"), this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String sendJsonMaxCategory() {
        String category = null;
        long sum = 0L;
         Optional<Map.Entry<String, Long>> maxCategory = expenses.entrySet().stream().max(Map.Entry.comparingByValue());
        if (maxCategory.isPresent()) {
            sum = maxCategory.get().getValue();
            category = maxCategory.get().getKey();
        }
        return "{\"MaxCategory\": " +
                "{\"category\": " + "\"" + category + "\", " +
                "\"sum\": " + sum + "}" +
                "}";
    }

}
