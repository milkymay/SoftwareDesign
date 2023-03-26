package melmon.user;

import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class ExchangeProvider {
    private final String host;

    public ExchangeProvider(String host) {
        this.host = host;
    }

    public int getStockPrice(String stock) {
        URL url = buildUrl("/get_price", Map.of("name", stock));
        return Integer.parseInt(sendThenReceive(url));
    }

    public int buyStock(String stock, int number) {
        URL url = buildUrl("/buy", Map.of("name", stock, "number", number));
        return requestAndParse(url);
    }

    public int sellStock(String stock, int number) {
        URL url = buildUrl("/sell", Map.of("name", stock, "number", number));
        return Integer.parseInt(sendThenReceive(url));
    }

    private int requestAndParse(URL url) {
        String receive = sendThenReceive(url);
        try {
            return Integer.parseInt(receive);
        } catch (Exception e) {
            throw new RuntimeException(receive);
        }
    }

    private URL buildUrl(String path, Map<String, Object> params) {
        UriComponentsBuilder urlCB = UriComponentsBuilder.newInstance().scheme("http").host(host).path(path);
        for (String key : params.keySet()) {
            urlCB.queryParam(key, params.get(key));
        }
        String uriStr = urlCB.build().toUriString();
        URL url;
        try {
            url = new URL(uriStr);
        } catch (MalformedURLException e) {
            throw new UncheckedIOException(e);
        }
        return url;
    }

    private static String sendThenReceive(URL url) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            StringBuilder buffer = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                buffer.append(inputLine);
            }
            return buffer.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
