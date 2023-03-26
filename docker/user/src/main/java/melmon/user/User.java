package melmon.user;

import java.util.HashMap;
import java.util.Map;

public class User {
    private final int id;
    private final Map<String, Integer> stocks;
    private int balance;

    public User(int id) {
        this.id = id;
        this.stocks = new HashMap<>();
        this.balance = 0;
    }

    public int getId() {
        return id;
    }

    public Map<String, Integer> getAllStocks() {
        return Map.copyOf(stocks);
    }

    public int getBalance() {
        return balance;
    }

    public void addStock(String stock, int quantity) {
        int newQuantity = stocks.getOrDefault(stock, 0) + quantity;
        if (newQuantity < 0) {
            throw new RuntimeException("Quantity can't be negative. (melmon.user.User " + id + ")");
        }
        if (newQuantity == 0) {
            stocks.remove(stock);
        } else {
            stocks.put(stock, newQuantity);
        }
    }

    public void addBalance(int additionalMoney) {
        int newBalance = balance + additionalMoney;
        if (newBalance < 0) {
            throw new RuntimeException("Balance can't be negative. (melmon.user.User " + id + ")");
        }
        balance = newBalance;
    }


}
