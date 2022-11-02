import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;

import java.io.UncheckedIOException;

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
        String responseStatus = Utils.readAsText(Utils.generateAddUrl("ily", "3"));
        assertEquals("OK", responseStatus.trim());
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
    }
}
