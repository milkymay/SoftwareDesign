package ru.akirakozov.mockexample.stock;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akirakozov
 */
public class StockResponseParser {
    public List<StockInfo> parseResponse(String response) {
        String[] parts = response.split("//");

        JsonArray entries = (JsonArray) new JsonParser().parse(parts[1]);
        List<StockInfo> infos = new ArrayList<>(entries.size());
        for (JsonElement e : entries) {
            JsonObject d = (JsonObject) e;
            infos.add(new StockInfo(
                    d.get("t").getAsString(),
                    d.get("l").getAsDouble(),
                    d.get("c").getAsDouble()));
        }

        return infos;
    }

}
