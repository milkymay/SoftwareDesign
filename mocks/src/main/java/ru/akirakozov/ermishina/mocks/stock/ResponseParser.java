package ru.akirakozov.ermishina.mocks.stock;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akirakozov
 */
public class ResponseParser {
    public List<TweetInfo> parseResponse(String response) {
        JsonElement a = new JsonParser().parse(response);
        JsonArray entries = ((JsonObject) a).getAsJsonArray("data");
        List<TweetInfo> infos = new ArrayList<>(entries.size());
        for (JsonElement e : entries) {
            JsonObject d = (JsonObject) e;
            infos.add(new TweetInfo(
                    d.get("end").getAsString(),
                    d.get("start").getAsString(),
                    d.get("tweet_count").getAsLong()));
        }

        return infos;
    }

}
