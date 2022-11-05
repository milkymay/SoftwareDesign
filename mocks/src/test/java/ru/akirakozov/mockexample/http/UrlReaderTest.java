package ru.akirakozov.mockexample.http;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import ru.akirakozov.mockexample.rule.HostReachableRule;

/**
 * @author akirakozov
 */
@HostReachableRule.HostReachable("finance.google.com")
public class UrlReaderTest {

    @ClassRule
    public static final HostReachableRule rule = new HostReachableRule();

    @Test
    public void read() {
        String result = new UrlReader()
                .readAsText("http://finance.google.com/finance/info?client=ig&q=NASDAQ:GOOG,YNDX");
        Assert.assertTrue(result.length() > 0);
    }
}
