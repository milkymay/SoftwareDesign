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

    public int getStockPrice(String name) {
        URL url = buildUrl("/get_price", Map.of("name", name));
        return Integer.parseInt(sendThenReceive(url));
    }

    public void buyStock(String name, int quantity) {
        URL url = buildUrl("/buy", Map.of("name", name, "quantity", quantity));
        sendThenReceive(url);
    }

    public void sellStock(String name, int quantity) {
        URL url = buildUrl("/sell", Map.of("name", name, "quantity", quantity));
        sendThenReceive(url);
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
