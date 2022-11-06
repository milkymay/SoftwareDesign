package ru.akirakozov.ermishina.mocks.stock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @author akirakozov
 */
public class TwitterManagerTest {

    private TwitterManager stockManager;

    @Mock
    private TwitterClient client;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        stockManager = new TwitterManager(client);
    }

    @Test
    public void getTweetsWithHashtagCounts() {
        String hashtag = "comedy";
        when(client.getInfo(hashtag))
                .thenReturn(createAnswer());

        List<Long> names = stockManager.getTweetsWithHashtagCount(hashtag, "2022-10-30T16:00:00.000Z");
        Assert.assertEquals(
                Arrays.asList(199L, 164L),
                names);
    }

    private List<TweetInfo> createAnswer() {
        return Arrays.asList(
                new TweetInfo("2022-10-30T16:00:00.000Z", "2022-10-30T15:00:00.000Z", 187),
                new TweetInfo("2022-10-30T17:00:00.000Z", "2022-10-30T16:00:00.000Z", 199),
                new TweetInfo("2022-10-30T18:00:00.000Z", "2022-10-30T17:00:00.000Z", 164)
        );
    }

}
