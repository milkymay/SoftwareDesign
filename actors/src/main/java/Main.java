import API.FakeAPI;
import API.FakeBingApi;
import API.FakeGoogleApi;
import API.FakeYandexApi;
import Actors.ParentActor;
import Search.Response;
import Search.SearchStubServer;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Main {
    private final static Timeout timeout = Timeout.create(Duration.ofSeconds(30));
    private final static ActorSystem actorSystem = ActorSystem.create("MySystem");

    private static ActorRef createParent(int yandexResponseTime, int googleResponseTime, int bingResponseTime) {
        Map<FakeAPI, Integer> apisWithResponseTimes = new HashMap<>();
        apisWithResponseTimes.put(new FakeYandexApi(), yandexResponseTime);
        apisWithResponseTimes.put(new FakeGoogleApi(), googleResponseTime);
        apisWithResponseTimes.put(new FakeBingApi(), bingResponseTime);
        return actorSystem.actorOf(Props.create(ParentActor.class,
                actorSystem, apisWithResponseTimes));
    }

    public static void main(String[] args) {
        final String query = "query";
        SearchStubServer.processWithStub(() -> {
                    ActorRef parentActor = createParent(0, 0, 0);
                    Future<Object> future = Patterns.ask(parentActor, new Response(query), timeout);
                    try {
                        String res = (String) Await.result(future, timeout.duration());
                        System.out.println(res);
                    } catch (InterruptedException | TimeoutException e) {
                        System.out.println("Exception was thrown: " + e.getMessage());
                    } finally {
                        actorSystem.stop(parentActor);
                        actorSystem.terminate();
                    }
                }
        );
    }
}
