package melmon.exchange;

public class Stock {
    private final String name;
    private int quantity;
    private int price;

    public Stock(String name, int quantity, int price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public void add(int additionalQuantity) {
        if (quantity + additionalQuantity < 0) {
            throw new RuntimeException("Stock quantity can't be negative (Stock " + name + ")");
        }
        quantity += additionalQuantity;
    }

    public void updatePrice(int price) {
        if (price < 0) {
            throw new RuntimeException("Stock price can't be negative (Stock " + name + ")");
        }
        this.price = price;
    }
}
