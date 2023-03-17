package Search;

import com.xebialabs.restito.server.StubServer;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.glassfish.grizzly.http.Method;

import java.util.function.Consumer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.*;

public class SearchStubServer {
    public static void processWithStub(Runnable task) {
        withStub(s -> {
            handleQuery(s, "yandex");
            handleQuery(s, "google");
            handleQuery(s, "bing");
            task.run();
        });
    }

    private static void handleQuery(StubServer s, String service) {
        whenHttp(s)
                .match(method(Method.GET), startsWithUri("/" + service), custom(x -> {
                    String responseTimeStr = x.getRequest().getParameter("responseTime");
                    int responseTime = 0;
                    try {
                        responseTime = Integer.parseInt(responseTimeStr);
                    } catch (NumberFormatException ignored) {
                    }
                    try {
                        Thread.sleep(responseTime * 1000L);
                    } catch (InterruptedException ignored) {
                    }
                    return true;
                })).then(stringContent(generateResponse(service)));
    }

    private static String generateResponse(String APIname) {
        JSONArray response = new JSONArray();
        for (int i = 0; i < 15; i++) {
            JSONObject cur = new JSONObject();
            cur.put(Integer.toString(i), APIname + "_answer" + i);
            response.add(cur);
        }
        return response.toString();
    }

    public static void withStub(Consumer<StubServer> callback) {
        StubServer stubServer = null;
        try {
            stubServer = new StubServer(11111).run();
            callback.accept(stubServer);
        } finally {
            if (stubServer != null) {
                stubServer.stop();
            }
        }
    }
}
