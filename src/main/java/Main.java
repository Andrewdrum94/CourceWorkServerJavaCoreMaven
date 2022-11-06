import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Optional;

public class Main {
    private static final Map<String, String> categories = TsvReader.readTsvCategories(new File("categories.tsv"));
    private static final Map<String, Long> expenses;

    static {
        if (!(new File("expenses.json").exists())) {
            expenses = Expenses.initHashMap(categories);
        } else {
            expenses = Expenses.loadDataFromJson();
        }

    }


    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8989)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                    String input = in.readLine();
                    RequestDto dto = RequestDto.parseInputJson(input);
                    if (dto.getTitle().equals("end")) {
                        out.println("Работа сервера завершена");
                        break;
                    }
                    if (dto.getSum() < 0) {
                        out.println("Вы ввели отрицательную сумму");
                    } else {
                        calcNewExpenses(dto);
                        System.out.println(expenses);
                        Expenses expensesClass = new Expenses(expenses);
                        expensesClass.saveExpensesJson();
                        out.println(expensesClass.sendJsonMaxCategory());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }

    private static void calcNewExpenses(RequestDto dto) {
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


}

