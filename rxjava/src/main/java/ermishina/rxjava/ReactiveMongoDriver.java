package ermishina.rxjava;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoCollection;
import org.bson.Document;
import rx.Observable;

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

    private static void getPrintln(User user) {
        System.out.println(user);
    }


    private static Observable<User> getAllUsers(MongoCollection<Document> collection) {
        return collection.find().toObservable().map(User::new);
    }
}

