package ermishina.rxjava;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoCollection;
import org.bson.Document;
import rx.Observable;

import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author akirakozov
 */
public class ReactiveMongoDriver {

    private static MongoClient client;

    public ReactiveMongoDriver(String connectionString) {
        client = MongoClients.create(connectionString);
    }

    private final static String USERS_COLLECTION = "users";
    private final static String PRODUCTS_COLLECTION = "products";
    private final static String DB_NAME = "catalogue";

    public Observable<String> registerUser(Integer id, String name, Currency currency) {
        MongoCollection<Document> collection = client.getDatabase(DB_NAME).getCollection(USERS_COLLECTION);
        return collection.find(eq("id", id)).toObservable().isEmpty().flatMap(notFound -> {
            if (notFound) {
                final Document document = new Document("id", id).append("name", name).append("currency", currency);
                return collection.insertOne(document).map(x -> "Registered user: " + name);
            } else {
                return Observable.just("User already exists: " + id);
            }
        });
    }

    public Observable<String> registerProduct(int id, String name, final Map<Currency, Double> prices) {
        final MongoCollection<Document> products = client.getDatabase(DB_NAME).getCollection(PRODUCTS_COLLECTION);
        final Document document = new Document("id", id).append("name", name);
        for (Currency currency : prices.keySet()) {
            document.append("price: " + currency.toString(), prices.get(currency));
        }
        return products.find(eq("id", id)).toObservable().isEmpty().flatMap(notFound -> {
            if (notFound) {
                return products.insertOne(document).map(x -> "Registered product: " + name);
            } else {
                return Observable.just("Product already exists: " + id);
            }
        });
    }

    public Observable<String> showUser(int id) {
        final MongoCollection<Document> users = client.getDatabase(DB_NAME).getCollection(USERS_COLLECTION);
        Observable<Document> userDoc = users.find(eq("id", id)).toObservable();
        return userDoc.isEmpty().flatMap(notFound -> {
            if (notFound) {
                return Observable.just("No such user: " + id);
            } else {
                return userDoc.map(User::new).map(User::toString);
            }
        });
    }

    public Observable<String> showProduct(int productId, int userId) {
        Observable<User> user = client.getDatabase(DB_NAME).getCollection(USERS_COLLECTION)
                .find(eq("id", userId)).toObservable().map(User::new);
        return user.isEmpty().flatMap(notFoundUser -> {
            if (notFoundUser) {
                return Observable.just("No id");
            } else {
                Observable<Product> product = client.getDatabase(DB_NAME).getCollection(PRODUCTS_COLLECTION)
                        .find(eq("id", productId)).toObservable().map(Product::new);
                return product.isEmpty().flatMap(notFoundProduct -> {
                    if (notFoundProduct) {
                        return Observable.just("No such product: " + productId);
                    } else {
                        return user.flatMap(u -> product.map(
                                p -> p.toStringInCurrency(u.getCurrency())));
                    }
                });
            }
        });
    }
}

