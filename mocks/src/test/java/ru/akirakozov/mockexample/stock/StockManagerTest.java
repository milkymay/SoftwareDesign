package ru.akirakozov.mockexample.stock;

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
public class StockManagerTest {

    private StockManager stockManager;

    @Mock
    private StockClient client;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        stockManager = new StockManager(client);
    }

    @Test
    public void getCompanyNamesWithGrowingPrice() {
        List<String> symbols = Arrays.asList("GOOG", "YNDX", "MSFT");
        when(client.getInfo(symbols))
                .thenReturn(createAnswer());

        List<String> names = stockManager.getCompanyNamesWithGrowingPrice(symbols);
        Assert.assertEquals(Arrays.asList("GOOG", "YNDX"), names);
    }

    private List<StockInfo> createAnswer() {
        return Arrays.asList(
            new StockInfo("GOOG", 720.3, 1.3),
            new StockInfo("YNDX", 20.33, 0.23),
            new StockInfo("MSFT", 300.5, -3.2)
        );
    }

}
