import java.net.Socket;
import java.util.Objects;

public class RequestRouter {

    WeatherHandler weatherHandler = new WeatherHandler();
    PrimeNumbersHandler primeNumbersHandler = new PrimeNumbersHandler();
    SleepHandler sleepHandler = new SleepHandler();
    NotFoundHandler notFoundHandler = new NotFoundHandler();

    public void route(HttpRequest req, Socket socket) {

        if (Objects.equals(req.getPath(), "/weather") && Objects.equals(req.getMethod(), "GET")) {
            weatherHandler.handle(req, socket);
        } else if(Objects.equals(req.getPath(), "/primes") && Objects.equals(req.getMethod(), "GET")) {
            primeNumbersHandler.handle(req, socket);
        } else if(Objects.equals(req.getPath(), "/sleep") && Objects.equals(req.getMethod(), "GET")) {
            sleepHandler.handle(req, socket);
        } else {
            notFoundHandler.handle(req, socket);
        }
    }

}
