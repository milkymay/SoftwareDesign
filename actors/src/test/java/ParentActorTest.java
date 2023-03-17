import Actors.ParentActor;
import API.FakeAPI;
import API.FakeBingApi;
import API.FakeGoogleApi;
import API.FakeYandexApi;
import Search.Response;
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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;

public class ParentActorTest {
    private final static Timeout timeout = Timeout.create(Duration.ofSeconds(30));
    private final ActorSystem actorSystem = ActorSystem.create("MySystem");

    private ActorRef createParent(int yandexResponseTime, int googleResponseTime, int bingResponseTime) {
        Map<FakeAPI, Integer> apisWithResponseTimes = new HashMap<>();
        apisWithResponseTimes.put(new FakeYandexApi(), yandexResponseTime);
        apisWithResponseTimes.put(new FakeGoogleApi(), googleResponseTime);
        apisWithResponseTimes.put(new FakeBingApi(), bingResponseTime);
        return actorSystem.actorOf(Props.create(ParentActor.class,
                actorSystem, apisWithResponseTimes));
    }

    private String getParentResult(ActorRef parentActor, String query) {
        AtomicReference<String> res = new AtomicReference<>(null);
        SearchStubServer.processWithStub(() -> {
            Future<Object> future = Patterns.ask(parentActor, new Response(query), timeout);
            try {
                res.set((String) Await.result(future, timeout.duration()));
            } catch (InterruptedException | TimeoutException e) {
                // skip this api' response
            }
        });

        return res.get();
    }

    @Test
    public void test_all_apis() {
        assertEquals("{yandex=yandex_answer1 yandex_answer2 yandex_answer3 yandex_answer4 yandex_answer5, " +
                        "google=google_answer1 google_answer2 google_answer3 google_answer4 google_answer5, " +
                        "bing=bing_answer1 bing_answer2 bing_answer3 bing_answer4 bing_answer5}",
                getParentResult(createParent(0, 0, 0), ""));
    }

    @Test
    public void test_except_bing() {
        assertEquals("{yandex=yandex_answer1 yandex_answer2 yandex_answer3 yandex_answer4 yandex_answer5, " +
                        "google=google_answer1 google_answer2 google_answer3 google_answer4 google_answer5}",
                getParentResult(createParent(0, 0, 10), ""));
    }

    @Test
    public void test_except_bing_and_yandex() {
        assertEquals("{google=google_answer1 google_answer2 google_answer3 google_answer4 google_answer5}",
                getParentResult(createParent(10, 0, 10), ""));
    }
}
