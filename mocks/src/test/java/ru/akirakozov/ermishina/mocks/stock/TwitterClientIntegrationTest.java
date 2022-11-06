package ru.akirakozov.ermishina.mocks.stock;

import org.junit.Assert;
import org.junit.Test;
import ru.akirakozov.ermishina.mocks.rule.HostReachableRule;

import java.util.List;

/**
 * @author akirakozov
 */
@HostReachableRule.HostReachable(TwitterClientIntegrationTest.HOST)
public class TwitterClientIntegrationTest {
    public static final String HOST = "api.twitter.com";

    @Test
    public void getInfo() {
        TwitterClient client = new TwitterClient(HOST);
        List<TweetInfo> infos = client.getInfo("any");
        Assert.assertEquals(169, infos.size());
    }
}

