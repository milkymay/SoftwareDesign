package ru.akirakozov.ermishina.mocks.stock;

import ru.akirakozov.ermishina.mocks.http.UrlReader;

import java.io.IOException;
import java.util.List;

/**
 * @author akirakozov
 */
public class TwitterClient {
    private final String prefix;
    private final ResponseParser parser;
    private final UrlReader reader;

    public TwitterClient(String host) {
        this.prefix = "https://" + host + "/2/tweets/counts/recent?query=%23";
        this.parser = new ResponseParser();
        this.reader = new UrlReader();
    }

    public List<TweetInfo> getInfo(String hashtag) {
        String response = null;
        try {
            response = reader.readAsText(createUrl(hashtag));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parser.parseResponse(response);
    }

    private String createUrl(String hashtag) {
        return prefix + hashtag;
    }
}
