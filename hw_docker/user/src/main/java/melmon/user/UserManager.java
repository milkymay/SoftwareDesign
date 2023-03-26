package melmon.user;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UserManager {
    private final Map<Integer, User> users;
    private final ExchangeProvider exchange;

    public UserManager() {
        users = new HashMap<>();
        exchange = new ExchangeProvider("localhost:8080");
    }

    public boolean registerUser(int userId) {
        if (users.containsKey(userId)) {
            return false;
        }
        users.put(userId, new User(userId));
        return true;
    }

    public void buyStock(int userId, String stock, int quantity) {
        checkUser(userId);
        int totalPrice = quantity * exchange.getStockPrice(stock);
        checkBalance(userId, totalPrice);
        exchange.buyStock(stock, quantity);
        User user = users.get(userId);
        user.addBalance(-totalPrice);
        user.addStock(stock, quantity);
    }

    public void sellStock(int userId, String stock, int quantity) {
        checkUser(userId);
        User user = users.get(userId);
        int curQuantity = user.getAllStocks().getOrDefault(stock, 0);
        if (curQuantity < quantity) {
            throw new RuntimeException("Not enough stocks to sell. (User " + userId + ")");
        }
        int totalPrice = quantity * exchange.getStockPrice(stock);
        exchange.sellStock(stock, quantity);
        user.addBalance(totalPrice);
        user.addStock(stock, quantity);
    }

    public void addUserBalance(int userId, int additionalMoney) {
        checkUser(userId);
        User user = users.get(userId);
        user.addBalance(additionalMoney);
    }

    public int getUserValue(int userId) {
        checkUser(userId);
        User user = users.get(userId);
        int value = user.getBalance();
        for (Map.Entry<String, Integer> stockWithQuantity : user.getAllStocks().entrySet()) {
            value += exchange.getStockPrice(stockWithQuantity.getKey()) * stockWithQuantity.getValue();
        }
        return value;
    }

    public String getUserInfo(int userId) {
        checkUser(userId);
        User user = users.get(userId);
        return user.getAllStocks().entrySet().stream()
                .map(e -> e.getKey() + ": quantity = " + e.getValue() + ", price = " + exchange.getStockPrice(e.getKey()))
                .collect(Collectors.joining("\n"));
    }

    public void checkUser(int userId) {
        if (!users.containsKey(userId)) {
            throw new RuntimeException("User " + userId + " does not exist");
        }
    }

    public void checkBalance(int userId, int requiredBalance) {
        if (users.get(userId).getBalance() < requiredBalance) {
            throw new RuntimeException("Not enough balance. (User " + userId + ")");
        }
    }
}
