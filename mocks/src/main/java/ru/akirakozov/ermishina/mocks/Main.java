package ru.akirakozov.ermishina.mocks;

import ru.akirakozov.ermishina.mocks.stock.TwitterClient;
import ru.akirakozov.ermishina.mocks.stock.TwitterManager;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String hashtag = "comedy";
        int timeSpan = 10;
        List<Long> info = getTwitterStats(hashtag, timeSpan);

        System.out.println("Statistics for tweets with hashtag " + hashtag +
                " for the last " + timeSpan + " hours is: " + info);
    }

    public static List<Long> getTwitterStats(String hashtag, int timeSpan) {
        TwitterClient client = new TwitterClient("api.twitter.com");
        TwitterManager manager = new TwitterManager(client);

        Instant now = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant from = now.minus(timeSpan, ChronoUnit.HOURS).truncatedTo(ChronoUnit.SECONDS);
        return manager.getTweetsWithHashtagCount(hashtag, from.toString());
    }
}
