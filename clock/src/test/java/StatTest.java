import org.junit.Before;
import org.junit.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
        assertEquals(0, eventsStats.getEventStatisticByName("name"), 1e-5);
        eventsStats.printStatistic();
        final String output = out.toString().trim();
        assertEquals("", output);
    }
}
