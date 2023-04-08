package ermishina.rxjava;

import org.bson.Document;

/**
 * @author akirakozov
 */
public class User {
    public final int id;
    public final String name;
    public final String currency;


    public User(Document doc) {
        this(doc.getDouble("id").intValue(), doc.getString("name"), doc.getString("login"));
    }

    public User(int id, String name, String currency) {
        this.id = id;
        this.name = name;
        this.currency = currency;
    }

    public Currency getCurrency() {
        return Currency.getFromString(currency);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
