import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventsStatisticImpl implements EventsStatistic {
    private final Clock clock;
    private final Map<String, List<Instant>> stats = new HashMap<>();

    public EventsStatisticImpl(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void incEvent(String name) {
        stats.putIfAbsent(name, new ArrayList<>());
        stats.get(name).add(clock.instant());
    }

    @Override
    public double getEventStatisticByName(String name) {
        if (!stats.containsKey(name)) {
            return 0;
        }
        List<Instant> eventStat = stats.get(name);
        Instant to = clock.instant();
        Instant from = to.minus(1, ChronoUnit.HOURS);
        return getLastHourEvents(eventStat, from, to);
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        Instant to = clock.instant();
        Instant from = to.minus(1, ChronoUnit.HOURS);
        Map<String, Double> allStats = new HashMap<>();
        for (String name : stats.keySet()) {
            double stat = getLastHourEvents(stats.get(name), from, to);
            if (stat > 0) {
                allStats.put(name, stat);
            }
        }
        return allStats;
    }

    private double getLastHourEvents(List<Instant> stats, Instant from, Instant to) {
        return stats.stream().filter(event -> from.isBefore(event) && to.isAfter(event)).count() / 60d;
    }

    @Override
    public void printStatistic() {
        for (final Map.Entry<String, Double> entry : getAllEventStatistic().entrySet()) {
            System.out.printf("Event name: \"%s\", rpm: %.6f\n", entry.getKey(), entry.getValue());
        }
    }
}
