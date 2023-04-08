package ermishina.rxjava;


import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;

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
            switch (path) {
                case "register_user":
                    return registerUser(request);
                default:
                    return Observable.just("Incorrect request");
            }
        } catch (Exception e) {
            return Observable.just("Error while processing: " + e.getMessage() + " " + request.getQueryParameters());
        }
    }

    private Observable<String> registerUser(HttpServerRequest<ByteBuf> request) {
        final Integer id = Integer.parseInt(getFromParams(request, "id"));
        String name = getFromParams(request, "name");
        Currency currency = Currency.getFromString(getFromParams(request, "currency"));
        return mongo.registerUser(id, name, currency);
    }

    private String getFromParams(HttpServerRequest<ByteBuf> request, String id) {
        return request.getQueryParameters().get(id).get(0);
    }
}
