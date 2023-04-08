package ermishina.rxjava;

import java.util.Map;

public enum Currency {
    RUB, USD, EUR;

    private final static Map<String, Currency> stringCurrencyMap =
            Map.of("RUB", RUB, "USD", USD, "EUR", EUR);

    public static Currency getFromString(String currency) {
        if (!stringCurrencyMap.containsKey(currency)) {
            return EUR;
        }
        return stringCurrencyMap.get(currency);
    }

    @Override
    public String toString() {
        return name();
    }
}
