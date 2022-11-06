package ru.akirakozov.ermishina.mocks.stock;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author akirakozov
 */
public class TwitterResponseParserTest {
    private final static String testResponse = """
            {"data":[{"end":"2022-10-30T15:00:00.000Z","start":"2022-10-30T14:28:16.000Z","tweet_count":88},
            {"end":"2022-10-30T16:00:00.000Z","start":"2022-10-30T15:00:00.000Z","tweet_count":187},
            {"end":"2022-10-31T01:00:00.000Z","start":"2022-10-31T00:00:00.000Z","tweet_count":187}],
            "meta":{"total_tweet_count":30398}}
            """;

    @Test
    public void parseResponse() {
        ResponseParser parser = new ResponseParser();
        List<TweetInfo> info = parser.parseResponse(testResponse);

        Assert.assertEquals(3, info.size());

        Assert.assertEquals(info.get(0), new TweetInfo("2022-10-30T15:00:00.000Z", "2022-10-30T14:28:16.000Z", 88));
        Assert.assertEquals(info.get(1), new TweetInfo("2022-10-30T16:00:00.000Z", "2022-10-30T15:00:00.000Z", 187));
        Assert.assertEquals(info.get(2), new TweetInfo("2022-10-31T01:00:00.000Z", "2022-10-31T00:00:00.000Z", 187));
    }
}
