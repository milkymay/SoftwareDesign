import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Utils {
    public static String readAsText(String sourceUrl) {
        URL url = toUrl(sourceUrl);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            StringBuilder buffer = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                buffer.append(inputLine);
                buffer.append("\n");
            }
            return buffer.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static URL toUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed url: " + url);
        }
    }


    public static String generateAddUrl(String name, String price) {
        name = name.replaceAll(" ", "%20");
        return String.format("http://localhost:8081/add-product?name=%1$s&price=%2$s", name, price);
    }

    public static String generateQueryUrl(String command) {
        return String.format("http://localhost:8081/query?command=%1$s", command);
    }
}
