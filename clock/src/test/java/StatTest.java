import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StatTest {
    private final double delta = 1e-5;
    private TestClock clock;
    private EventsStatistic eventsStats;
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void before() {
        clock = new TestClock();
        eventsStats = new EventsStatisticImpl(clock);
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @Test
    public void empty() {
        assertTrue(eventsStats.getAllEventStatistic().isEmpty());
        assertEquals(0, eventsStats.getEventStatisticByName("name"), delta);

        eventsStats.printStatistic();
        final String output = out.toString().trim();
        assertEquals("", output);
    }

    @Test
    public void oneEvent() {
        String name = "name";
        eventsStats.incEvent(name);
        clock.offset(Duration.ofMillis(1));
        assertEquals(1 / 60d, eventsStats.getEventStatisticByName(name), delta);
        eventsStats.printStatistic();
        String output = out.toString().trim();
        assertEquals("Event name: \"name\", rpm: 0,016667", output);
        out.reset();

        clock.offset(Duration.ofMinutes(60));
        assertEquals(0, eventsStats.getEventStatisticByName(name), delta);
        eventsStats.printStatistic();
        output = out.toString().trim();
        assertEquals("", output);
    }

    @Test
    public void manyEvents() {
        for (int i = 1; i < 10; i++) {
            String name = "name" + i;
            for (int j = 0; j < i; j++) {
                eventsStats.incEvent(name);
            }
        }
        clock.offset(Duration.ofMillis(1));

        for (int i = 1; i < 10; i++) {
            String name = "name" + i;
            assertEquals(i / 60d, eventsStats.getEventStatisticByName(name), delta);
        }

        eventsStats.printStatistic();
        String output = out.toString().trim();
        assertEquals("""
                Event name: "name6", rpm: 0,100000
                Event name: "name5", rpm: 0,083333
                Event name: "name4", rpm: 0,066667
                Event name: "name3", rpm: 0,050000
                Event name: "name9", rpm: 0,150000
                Event name: "name8", rpm: 0,133333
                Event name: "name7", rpm: 0,116667
                Event name: "name2", rpm: 0,033333
                Event name: "name1", rpm: 0,016667""", output);
        out.reset();

        clock.offset(Duration.ofMinutes(60));
        eventsStats.printStatistic();
        output = out.toString().trim();
        assertEquals("", output);
    }
}
