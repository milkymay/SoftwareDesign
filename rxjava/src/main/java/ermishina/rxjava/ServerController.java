package ermishina.rxjava;


import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author akirakozov
 */
public class ServerController {
    private final ReactiveMongoDriver mongo;

    public ServerController(ReactiveMongoDriver mongo) {
        this.mongo = mongo;
    }

    public Observable<String> process(HttpServerRequest<ByteBuf> request) {
        final String path = request.getDecodedPath().substring(1);
        try {
            return switch (path) {
                case "register_user" -> registerUser(request);
                case "register_product" -> registerProduct(request);
                case "show_user" -> showUser(request);
                case "show_product" -> showProduct(request);
                default -> Observable.just("Incorrect request");
            };
        } catch (Exception e) {
            return Observable.just("Error while processing: " + e.getMessage() + " " + request.getQueryParameters());
        }
    }

    private Observable<String> registerUser(HttpServerRequest<ByteBuf> request) {
        final Integer id = Integer.parseInt(getFromParams(request, "id"));
        final String name = getFromParams(request, "name");
        final Currency currency = Currency.getFromString(getFromParams(request, "currency"));
        return mongo.registerUser(id, name, currency);
    }

    private Observable<String> registerProduct(HttpServerRequest<ByteBuf> request) {
        final int id = Integer.parseInt(getFromParams(request, "id"));
        final String name = getFromParams(request, "name");
        final Map<Currency, Double> prices = new HashMap<>();
        for (Currency currency : Currency.values()) {
            final List<String> cur = request.getQueryParameters().get(currency.toString());
            prices.put(currency, Double.parseDouble(cur.get(0)));
        }
        return mongo.registerProduct(id, name, prices);
    }

    private Observable<String> showUser(HttpServerRequest<ByteBuf> request) {
        final int id = Integer.parseInt(getFromParams(request, "id"));
        return mongo.showUser(id);
    }

    private Observable<String> showProduct(HttpServerRequest<ByteBuf> request) {
        final int userId = Integer.parseInt(getFromParams(request, "userId"));
        final int productId = Integer.parseInt(getFromParams(request, "productId"));
        return mongo.showProduct(productId, userId);
    }

    private String getFromParams(HttpServerRequest<ByteBuf> request, String id) {
        return request.getQueryParameters().get(id).get(0);
    }
}
