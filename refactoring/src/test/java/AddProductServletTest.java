import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;

import java.io.UncheckedIOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AddProductServletTest {
    static Server server;
    private static final int PORT = 8081;

    @BeforeAll
    public static void awakenDb() throws Exception {
        TestDatabase.create();

        server = new Server(PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet()), "/add-product");

        server.start();
    }

    @Test
    public void testOneCorrectAdd() {
        String name = "ily";
        String price = "3";
        String responseStatus = Utils.readAsText(Utils.generateAddUrl(name, price));
        assertEquals("OK", responseStatus.trim());
        Set<Utils.Pair> items = TestDatabase.get();
        assertEquals(items.size(), 1);
        assertTrue(items.contains(new Utils.Pair("ily", "3")));
    }

    @Test
    public void testMultipleAdds() {
        Set<Utils.Pair> itemsToAdd = new HashSet<Utils.Pair>() {{
            add(new Utils.Pair("ily", "3"));
            add(new Utils.Pair("item", "100"));
            add(new Utils.Pair("cats", "10"));
        }};
        for (Utils.Pair item : itemsToAdd) {
            String responseStatus = Utils.readAsText(Utils.generateAddUrl(item.getFirst(), item.getSecond()));
            assertEquals("OK", responseStatus.trim());
        }
        Set<Utils.Pair> addedItems = TestDatabase.get();
        assertEquals(3, addedItems.size());
        assertEquals(itemsToAdd, addedItems);
    }

    @Test
    public void testIncorrectAdd() {
        Exception exception = assertThrows(UncheckedIOException.class,
                () -> Utils.readAsText(Utils.generateAddUrl("me too", "l")));

        String expectedMessage = "Server returned HTTP response code: 500";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @AfterEach
    public void clear() throws Exception {
        TestDatabase.clear();
    }

    @AfterAll
    static void drop() throws Exception {
        TestDatabase.drop();
        server.stop();
    }
}
