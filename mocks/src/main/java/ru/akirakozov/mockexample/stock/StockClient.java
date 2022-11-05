package ru.akirakozov.mockexample.stock;

import org.apache.commons.lang3.StringUtils;
import ru.akirakozov.mockexample.http.UrlReader;

import java.util.List;

/**
 * @author akirakozov
 */
public class StockClient {
    private final String host;
    private final int port;
    private final StockResponseParser parser;
    private final UrlReader reader;
    
    public StockClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.parser = new StockResponseParser();
        this.reader = new UrlReader();
    }

    public List<StockInfo> getInfo(List<String> stockSymbols) {
        String response = reader.readAsText(createUrl(stockSymbols));
        return parser.parseResponse(response);
    }

    private String createUrl(List<String> stockSymbols) {
        String symbols = StringUtils.join(stockSymbols, ",");
        return "http://" + host + ":" + port + "/finance/info?q=NASDAQ:" + symbols;
    }
}
