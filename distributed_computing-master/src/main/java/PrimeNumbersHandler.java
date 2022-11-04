import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class PrimeNumbersHandler {

    public void handle(HttpRequest req, Socket socket) {

        int primes = 0;
        for(int i = 1; i <= 1000000; i++) {
            boolean prime = true;
            for(int j = 2; j < Math.sqrt(i); j++) {
                if (i % j == 0) {
                    prime = false;
                }
            }
            if (prime) primes++;
        }

        socketOutput(socket, primes);
    }

    private void socketOutput(Socket socket, int primes) {
        try {
            PrintWriter output = new PrintWriter(socket.getOutputStream());

            // We are returning a simple web page now.
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<body><p>There is " + primes + " prime numbers between 1 and 1000000</p></body>");
            output.println("</html>");
            output.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
