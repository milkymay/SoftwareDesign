import melmon.user.Application;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

@Testcontainers
public class UserTest {
    private static final int EXCHANGE_PORT = 8080;
    private static final int USER_PORT = 8081;
    private ConfigurableApplicationContext userServer;

    @Container
    private static final GenericContainer<?> exchange = new FixedHostPortGenericContainer<>("exchange:1.0-SNAPSHOT")
            .withFixedExposedPort(EXCHANGE_PORT, EXCHANGE_PORT).withExposedPorts(EXCHANGE_PORT);


    @Before
    public void start() {
        exchange.start();
        userServer = Application.run(new String[0]);
    }

    @Test
    public void test() {
        String response;
        response = addStock("abacaba", 10, 100);
        assertEquals("Stock info updated", response);

        response = registerUser(1);
        assertEquals("User 1 was registered", response);
        response = add(1, 10000);
        assertEquals("User 1 successfully added 10000 to their account", response);
        response = value(1);
        assertEquals("10000", response);

        response = getPrice("abacaba");
        assertEquals("100", response);

        response = getPrice("unknown");
        assertEquals("Stock unknown does not exist", response);

        response = buy(1, "abacaba", 9);
        assertEquals("User 1 successfully bought 9 of stocks abacaba", response);

        addStock("abacaba", 100, 100);
        response = buy(1, "abacaba", 5);
        assertEquals("User 1 successfully bought 5 of stocks abacaba", response);

        response = sell(1, "abacaba", 4);
        assertEquals("User 1 successfully sold 4 of stocks abacaba", response);

        response = sell(1, "abacaba", 4000);
        assertEquals("Not enough stocks to sell. (User 1)", response);

        response = registerUser(1);
        assertEquals("User with id 1 already exists", response);

        response = registerUser(2);
        assertEquals("User 2 was registered", response);

        response = addStock("abacaba", 1, 1);
        assertEquals("Stock info updated", response);

        response = addStock("stock2", 10, 200000);
        assertEquals("Stock info updated", response);
        response = value(3);
        assertEquals("User 3 does not exist", response);

        response = buy(2, "stock2", 1);
        assertEquals("Not enough balance. (User 2)", response);

        response = add(2, -1);
        assertEquals("Balance can't be negative. (User 2)", response);
        add(2, 100);

        response = updatePrice("stock2", 1);
        assertEquals("Successfully updated price", response);
        buy(2, "stock2", 10);

        response = value(2);
        assertEquals("100", response);
        response = updatePrice("stock2", 100);
        assertEquals("Successfully updated price", response);
        response = value(2);
        assertEquals("1090", response);


        response = info(1);
        assertEquals("abacaba: quantity = 18, price = 1", response);

        response = info(2);
        assertEquals("stock2: quantity = 10, price = 100", response);
    }


    private String addStock(String name, int quantity, int price) {
        return sendThenReceive(EXCHANGE_PORT, "add", "name=" + name + "&quantity=" + quantity + "&price=" + price);
    }

    private String registerUser(int userId) {
        return sendThenReceive(USER_PORT, "register", "userId=" + userId);
    }

    private String buy(int userId, String stock, int quantity) {
        return sendThenReceive(USER_PORT, "buy", "userId=" + userId + "&name=" + stock + "&quantity=" + quantity);
    }

    private String sell(int userId, String stock, int quantity) {
        return sendThenReceive(USER_PORT, "sell", "userId=" + userId + "&name=" + stock + "&quantity=" + quantity);
    }

    private String add(int userId, int additional) {
        return sendThenReceive(USER_PORT, "add_money", "userId=" + userId + "&additional=" + additional);
    }

    private String value(int userId) {
        return sendThenReceive(USER_PORT, "value", "userId=" + userId);
    }

    private String getPrice(String stock) {
        return sendThenReceive(EXCHANGE_PORT, "get_price", "name=" + stock);
    }

    private String updatePrice(String stock, int price) {
        return sendThenReceive(EXCHANGE_PORT, "update_price", "name=" + stock + "&price=" + price);
    }

    private String info(int userId) {
        return sendThenReceive(USER_PORT, "userInfo", "userId=" + userId);
    }


    private String sendThenReceive(int port, String endpoint, String params) {
        URL url;
        try {
            url = new URL("http://localhost:" + port + "/" + endpoint + "?" + params);
        } catch (MalformedURLException e) {
            throw new UncheckedIOException(e);
        }
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            StringBuilder buffer = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                buffer.append(inputLine);
            }
            return buffer.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    @After
    public void stopServers() {
        exchange.stop();
        userServer.stop();
    }

}
