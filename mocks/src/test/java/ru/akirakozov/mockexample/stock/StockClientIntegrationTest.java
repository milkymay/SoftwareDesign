package ru.akirakozov.mockexample.stock;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import ru.akirakozov.mockexample.rule.HostReachableRule;

import java.util.Arrays;
import java.util.List;

/**
 * @author akirakozov
 */
@HostReachableRule.HostReachable(StockClientIntegrationTest.HOST)
public class StockClientIntegrationTest {
    public static final String HOST = "finance.google.com";

    @ClassRule
    public static final HostReachableRule rule = new HostReachableRule();

    @Test
    public void getInfo() {
        StockClient client = new StockClient(HOST, 80);
        List<StockInfo> infos = client.getInfo(Arrays.asList("GOOG", "YNDX"));
        Assert.assertEquals(2, infos.size());
    }
}

