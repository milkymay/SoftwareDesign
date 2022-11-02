import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryServletTest {
    static Server server;

    @BeforeAll
    public static void awakenDb() throws Exception {
        TestDatabase.create();

        server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new QueryServlet()), "/query");

        server.start();
    }

    @Test
    public void testMax() {
        TestDatabase.add("ily", "3");
        TestDatabase.add("item", "100");
        TestDatabase.add("cats", "90");
        String responseStatus = Utils.readAsText(Utils.generateQueryUrl("max"));
        assertEquals("<html><body>\n" +
                "<h1>Product with max price: </h1>\n" +
                "item\t100</br>\n" +
                "</body></html>", responseStatus.trim());
    }

    @Test
    public void testMin() {
        TestDatabase.add("ily", "300");
        TestDatabase.add("item", "100");
        TestDatabase.add("cats", "1909");
        String responseStatus = Utils.readAsText(Utils.generateQueryUrl("min"));
        assertEquals("<html><body>\n" +
                "<h1>Product with min price: </h1>\n" +
                "item\t100</br>\n" +
                "</body></html>", responseStatus.trim());
    }

    @Test
    public void testCount() {
        TestDatabase.add("ily", "300");
        TestDatabase.add("item", "100");
        String responseStatus = Utils.readAsText(Utils.generateQueryUrl("count"));
        assertEquals("<html><body>\n" +
                "Number of products: \n" +
                "2\n" +
                "</body></html>", responseStatus.trim());
    }

    @Test
    public void testSum() {
        TestDatabase.add("ily", "300");
        TestDatabase.add("item", "100");
        String responseStatus = Utils.readAsText(Utils.generateQueryUrl("sum"));
        assertEquals("<html><body>\n" +
                "Summary price: \n" +
                "400\n" +
                "</body></html>", responseStatus.trim());
    }

    @Test
    public void testInvalidCommand() {
        String responseStatus = Utils.readAsText(Utils.generateQueryUrl("magic"));
        assertEquals("Unknown command: magic", responseStatus.trim());
    }

    @AfterEach
    public void clear() throws Exception {
        TestDatabase.clear();
    }

    @AfterAll
    static void drop() throws Exception {
        TestDatabase.drop();
    }
}


