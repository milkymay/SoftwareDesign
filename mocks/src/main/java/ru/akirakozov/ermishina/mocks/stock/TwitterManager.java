package ru.akirakozov.ermishina.mocks.stock;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akirakozov
 */
public class TwitterManager {
    private final TwitterClient client;

    public TwitterManager(TwitterClient client) {
        this.client = client;
    }

    public List<Long> getTweetsWithHashtagCount(String hashtag, String from) {

        return client.getInfo(hashtag)
                .stream()
                .filter(s -> s.start.compareTo(from) >= 0)
                .map(s -> s.tweet_count)
                .collect(Collectors.toList());
    }
}
