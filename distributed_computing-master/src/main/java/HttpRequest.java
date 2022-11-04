import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final Map<String, String> headers;
    private final String messageBody;
    private final String path;
    private final String method;

    public HttpRequest(Map<String, String> headers, String messageBody, String path, String method) {
        this.headers = headers;
        this.messageBody = messageBody;
        this.path = path;
        this.method = method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getMessageBody() {
        return messageBody;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String key: headers.keySet()) {
            builder.append(key).append(": ").append(headers.get(key)).append("\n");
        }
        if (messageBody != null && !messageBody.isEmpty()) {
            builder.append("\r\n").append(messageBody);
        }
        return builder.toString();
    }

    public static HttpRequest parse(BufferedReader reader) throws IOException {
        String requestLine = reader.readLine();
        if (requestLine == null || requestLine.isEmpty()) {
            throw new IOException("Invalid Request-Line: " + requestLine);
        }

        Map<String, String> headers = new HashMap<>();
        while (reader.ready()) {
            String header = reader.readLine();
            if (header.isEmpty()) {
                break;
            }
            int idx = header.indexOf(":");
            if (idx == -1) {
                throw new IOException("Invalid Header Parameter: " + header);
            }
            headers.put(header.substring(0, idx), header.substring(idx + 2));
        }

        StringBuilder messageBody = new StringBuilder();
        while (reader.ready()) {
            String bodyLine = reader.readLine();
            messageBody.append(bodyLine).append("\r\n");
        }

        String path = requestLine.substring(
            requestLine.indexOf("/"),
            requestLine.indexOf("HTTP")-1
        );

        String method = requestLine.substring(
            0,
            requestLine.indexOf(" ")
        );

        return new HttpRequest(headers, messageBody.toString(), path, method);
    }
}