package API;

import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class FakeAPI {
    abstract public String getName();

    public List<String> requestTop(String query, int responseTime) {
        URL url = buildURL(query, responseTime);
        if (url == null) {
            return List.of();
        }
        String response = null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            StringBuilder buffer = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                buffer.append(inputLine).append("\n");
            }
            response = buffer.toString();
        } catch (IOException ignored) {
        }
        return parseResponse(response);
    }

    private URL buildURL(String query, int responseTime) {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http");
        builder.setHost("localhost");
        builder.setPort(11111);
        builder.setPath(getName());
        builder.setParameter("query", query);
        builder.setParameter("responseTime", String.valueOf(responseTime));
        try {
            return builder.build().toURL();
        } catch (MalformedURLException | URISyntaxException ignored) {
        }
        return null;
    }


    private List<String> parseResponse(String response) {
        Map<Integer, String> responseMap = new HashMap<>();
        if (response == null || response.isEmpty()) {
            return List.of();
        }
        JSONArray jsonResponse = new org.json.JSONArray(response);
        for (int i = 0; i < jsonResponse.length(); i++) {
            JSONObject cur = jsonResponse.optJSONObject(i);
            for (String key : cur.keySet()) {
                responseMap.put(Integer.parseInt(key), (String) cur.get(key));
            }
        }
        List<String> res = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            res.add(responseMap.get(i));
        }
        return res;
    }

}
