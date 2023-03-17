package Actors;

import API.FakeAPI;
import Search.Response;
import Search.SearchResponder;
import akka.actor.AbstractActor;

public class ChildActor extends AbstractActor {
    private final FakeAPI fakeAPI;
    private final int responseTime;

    public ChildActor(FakeAPI fakeAPI) {
        this.fakeAPI = fakeAPI;
        this.responseTime = 0;
    }

    public ChildActor(FakeAPI fakeAPI, int responseTime) {
        this.fakeAPI = fakeAPI;
        this.responseTime = responseTime;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Response.class, x -> sender().tell(
                        new SearchResponder(fakeAPI.getName(), fakeAPI.requestTop(x.getText(), responseTime)),
                        self())).build();
    }
}
