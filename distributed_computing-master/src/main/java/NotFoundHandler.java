import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import java.io.PrintWriter;
import java.net.Socket;

public class NotFoundHandler {

    public void handle(HttpRequest req, Socket socket) {
        socketOutput(socket);
    }

    private void socketOutput(Socket socket) {
        try {
            PrintWriter output = new PrintWriter(socket.getOutputStream());

            // We are returning a simple web page now.
            output.println("HTTP/1.1 404 NOT_FOUND");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<body><p>Not found</p></body>");
            output.println("</html>");
            output.flush();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
