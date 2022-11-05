package ru.akirakozov.mockexample.stock;

/**
 * @author akirakozov
 */
public class StockInfo {
    public final String name;
    public final double price;
    public final double priceChange;

    public StockInfo(String name, double price, double priceChange) {
        this.name = name;
        this.price = price;
        this.priceChange = priceChange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockInfo stockInfo = (StockInfo) o;

        if (Double.compare(stockInfo.price, price) != 0) return false;
        if (Double.compare(stockInfo.priceChange, priceChange) != 0) return false;
        return !(name != null ? !name.equals(stockInfo.name) : stockInfo.name != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(priceChange);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
