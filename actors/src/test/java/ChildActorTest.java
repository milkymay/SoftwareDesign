import Actors.ChildActor;
import API.FakeBingApi;
import API.FakeGoogleApi;
import API.FakeYandexApi;
import Search.Response;
import Search.SearchResponse;
import Search.SearchStubServer;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;

public class ChildActorTest {
    private final static Timeout timeout = Timeout.create(Duration.ofMinutes(1));
    private final ActorSystem actorSystem = ActorSystem.create("MySystem");

    private SearchResponse getChildResult(ActorRef childActor, String query) {
        AtomicReference<SearchResponse> res = new AtomicReference<>(null);
        SearchStubServer.processWithStub(() -> {
            Future<Object> future = Patterns.ask(childActor, new Response(query), timeout);
            try {
                res.set((SearchResponse) Await.result(future, timeout.duration()));
            } catch (InterruptedException | TimeoutException e) {
                // skip this api' response
            }
        });

        return res.get();
    }

    @Test
    public void check_yandex() {
        ActorRef child = actorSystem.actorOf(Props.create(ChildActor.class, new FakeYandexApi()));
        SearchResponse res;

        res = getChildResult(child, "1");
        assertEquals("yandex", res.getApiName());
        assertEquals(List.of("yandex_answer1", "yandex_answer2", "yandex_answer3", "yandex_answer4", "yandex_answer5"), res.getResponses());
    }

    @Test
    public void check_google() {
        ActorRef child = actorSystem.actorOf(Props.create(ChildActor.class, new FakeGoogleApi()));
        SearchResponse res;

        res = getChildResult(child, "2");
        assertEquals("google", res.getApiName());
        assertEquals(List.of("google_answer1", "google_answer2", "google_answer3", "google_answer4", "google_answer5"), res.getResponses());
    }

    @Test
    public void check_bing() {
        ActorRef child = actorSystem.actorOf(Props.create(ChildActor.class, new FakeBingApi()));
        SearchResponse res;

        res = getChildResult(child, "3");
        assertEquals("bing", res.getApiName());
        assertEquals(List.of("bing_answer1", "bing_answer2", "bing_answer3", "bing_answer4", "bing_answer5"), res.getResponses());
    }
}
