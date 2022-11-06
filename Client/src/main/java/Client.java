import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название товара и стоимость: ");
        String[] input = scanner.nextLine().split(" ");
        String product = input[0];
        long sum = Long.parseLong(input[1]);
        Request request = new Request(product, sum);
        String message = request.stringToJson();
        request.toJs(message);

        try (Socket socket = new Socket("127.0.0.1", 8989);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out.println(message);
            String response = in.readLine();
            System.out.println(response);
        }
    }
}
