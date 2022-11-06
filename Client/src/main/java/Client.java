import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
//        File file = new File("categories.tsv");
//        List<String> products = TsvClientReader.readTsvProducts(file);
//        System.out.println("Список продуктов: ");
//        printProducts(products);
//        System.out.println("Введите номер продукта и стоимость:");
//        String[] input = scanner.nextLine().split(" ");
//        int numOfProduct = Integer.parseInt(input[0]);
//        int sum = Integer.parseInt(input[1]);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название товара и стоимость: ");
        String[] input = scanner.nextLine().split(" ");
        String product = input[0];
        long sum = Long.parseLong(input[1]);
        Request request = new Request(product, sum);
//        if (numOfProduct < 9) {
//            request = new Request(products.get(numOfProduct - 1), sum);
//        } else {
//            Scanner scan = new Scanner(System.in);
//            System.out.println("Введите наименование товара и стоимость через пробел: ");
//            String[] parts = scan.nextLine().split(" ");
//            String otherProduct = parts[0];
//            long price = Long.parseLong(parts[1]);
//            request = new Request(otherProduct, price);
//        }


        request.toJs(request.toJson());

        try (Socket socket = new Socket("127.0.0.1", 8989);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out.println(request.toJson());
            String response = in.readLine();
            System.out.println(response);
        }
    }

//    private static void printProducts(List<String> product) {
//        for (int i = 0; i < product.size(); i++) {
//            System.out.println((i + 1) + ". " + product.get(i));
//        }
//    }
}
