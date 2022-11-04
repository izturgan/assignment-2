import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Simple web server.
 */
public class WebServer {

    private static final int PARALLELISM_LEVEL = 7;

    public static void main(String[] args) {
        // Port number for http request
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 8080;
        // The maximum queue length for incoming connection
        int queueLength = args.length > 2 ? Integer.parseInt(args[2]) : 50;;

        try (ServerSocket serverSocket = new ServerSocket(port, queueLength)) {
            System.out.println("Web Server is starting up, listening at port " + port + ".");
            System.out.println("You can access http://localhost:" + port + " now.");

            ExecutorService executor = Executors.newFixedThreadPool(PARALLELISM_LEVEL);

            while (true) {
                executor.submit(new RequestHandler(serverSocket.accept()));
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            System.out.println("Server has been shutdown!");
        }
    }
}