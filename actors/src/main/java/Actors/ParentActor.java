package Actors;

import API.FakeAPI;
import Search.Response;
import Search.SearchResponder;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class ParentActor extends AbstractActor {
    private final ActorSystem actorSystem;
    private final Map<FakeAPI, Integer> fakeAPIList;
    private final Timeout timeout = Timeout.create(Duration.of(3, ChronoUnit.SECONDS));

    public ParentActor(ActorSystem actorSystem, Map<FakeAPI, Integer> fakeAPIList) {
        this.actorSystem = actorSystem;
        this.fakeAPIList = fakeAPIList;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Response.class, x -> sender().tell(getResponses(x), self())).build();
    }

    private String getResponses(Response query) {
        List<ActorRef> children = new ArrayList<>();

        for (Map.Entry<FakeAPI, Integer> entry : fakeAPIList.entrySet()) {
            children.add(actorSystem.actorOf(Props.create(ChildActor.class, entry.getKey(), entry.getValue())));
        }

        List<Future<Object>> futures = new ArrayList<>();
        for (ActorRef child : children) {
            futures.add(Patterns.ask(child, query, timeout));
        }

        Map<String, String> responses = new HashMap<>();

        for (Future<Object> future : futures) {
            try {
                SearchResponder data = (SearchResponder) Await.result(future, timeout.duration());
                String result = String.join(" ", data.getResponses());
                responses.put(data.getApiName(), result);
            } catch (InterruptedException | TimeoutException ignored) {
                // skip this api' response
            }
        }

        for (ActorRef child : children) {
            actorSystem.stop(child);
        }

        return responses.toString();
    }
}
