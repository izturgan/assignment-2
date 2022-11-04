import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {

    private Socket socket;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
            );

            HttpRequest request = HttpRequest.parse(input);
            Processor proc = new Processor(socket, request);
            proc.process();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
