import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetProductsServletTest {
    private static final String GET_URL = "http://localhost:8081/get-products";
    static Server server;

    @BeforeAll
    public static void awakenDb() throws Exception {
        TestDatabase.create();

        server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new GetProductsServlet()), "/get-products");

        server.start();
    }

    @Test
    public void testEmptyGet() {
        String responseStatus = Utils.readAsText(GET_URL);
        assertEquals("<html><body>\n</body></html>", responseStatus.trim());
    }

    @Test
    public void testAddGet() {
        TestDatabase.add("ily", "3");
        TestDatabase.add("item", "100");
        String responseStatus = Utils.readAsText(GET_URL);
        assertEquals("<html><body>\n" +
                "ily\t3</br>\n" +
                "item\t100</br>\n" +
                "</body></html>", responseStatus.trim());
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


