package ru.akirakozov.mockexample.stock;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akirakozov
 */
public class StockManager {
    private final StockClient client;

    public StockManager(StockClient client) {
        this.client = client;
    }

    public List<String> getCompanyNamesWithGrowingPrice(List<String> symbols) {
        return client.getInfo(symbols)
                .stream()
                .filter(s -> s.priceChange > 0)
                .map(s -> s.name)
                .collect(Collectors.toList());
    }
}
