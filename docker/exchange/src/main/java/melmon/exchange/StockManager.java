package melmon.exchange;

import java.util.HashMap;
import java.util.Map;

public class StockManager {
    private final Map<String, Stock> stocks;

    public StockManager() {
        this.stocks = new HashMap<>();
    }

    private void checkStock(String name) {
        if (!stocks.containsKey(name)) {
            throw new RuntimeException("Stock " + name + " does not exist");
        }
    }

    public void addStock(String name, int quantity, int price) {
        Stock stock = new Stock(name, quantity, price);
        if (stocks.containsKey(name)) {
            stock.add(stocks.get(name).getQuantity());
        }
        stocks.put(name, stock);
    }

    public void buyStock(String name, int quantity) {
        checkStock(name);
        int curQuantity = stocks.get(name).getQuantity();
        if (curQuantity < quantity) {
            throw new RuntimeException("Not enough stock " + name + " to buy");
        }
        stocks.get(name).add(-quantity);
    }

    public void sellStock(String name, int quantity) {
        checkStock(name);
        stocks.get(name).add(quantity);
    }

    public int getPrice(String name) {
        checkStock(name);
        return stocks.get(name).getPrice();
    }


    public void updatePrice(String name, int newPrice) {
        checkStock(name);
        stocks.get(name).updatePrice(newPrice);
    }

}
