package ermishina.rxjava;

import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;

/**
 * @author akirakozov
 */
public class WebCatalogue {

    public static void main(final String[] args) {
        final ReactiveMongoDriver mongo = new ReactiveMongoDriver("mongodb://localhost:27017");
        HttpServer
                .newServer(8081)
                .start((req, resp) -> {
                    Observable<String> response = new ServerController(mongo).process(req);
                    return resp.writeString(response);
                })
                .awaitShutdown();
    }
}
