package ru.akirakozov.ermishina.mocks.stock;

import java.util.Objects;

/**
 * @author akirakozov
 */
public class TweetInfo {
    public final String end;
    public final String start;
    public final long tweet_count;

    public TweetInfo(String end, String start, long tweet_count) {
        this.end = end;
        this.start = start;
        this.tweet_count = tweet_count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TweetInfo tweetInfo = (TweetInfo) o;
        return Double.compare(tweetInfo.tweet_count, tweet_count) == 0 && Objects.equals(end, tweetInfo.end) && Objects.equals(start, tweetInfo.start);
    }

    @Override
    public int hashCode() {
        return Objects.hash(end, start, tweet_count);
    }
}
