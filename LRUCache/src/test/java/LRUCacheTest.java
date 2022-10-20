import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class LRUCacheTest {
    @Test
    public void testSinglePutAndGet() {
        LRUCache<String, Integer> lruCache = new LRUCache<>(6);
        lruCache.put("a", 1);
        Assert.assertEquals(lruCache.get("a"), Integer.valueOf(1));
    }

    @Test
    public void testUpdateValue() {
        LRUCache<String, Integer> lruCache = new LRUCache<>(6);
        lruCache.put("a", 1);
        lruCache.put("a", 2);
        Assert.assertEquals(lruCache.get("a"), Integer.valueOf(2));
    }


    @Test
    public void testSimpleOrderAfterPuts() {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(6);
        for (int i = 0; i < 6; i++) {
            lruCache.put(i, i + 1);
        }
        List<Integer> orderedKeysList = lruCache.getOrderedKeys();
        for (int i = 0; i < 6; i++) {
            Assert.assertEquals(Integer.valueOf(5 - i), orderedKeysList.get(i));
        }
    }

    @Test
    public void testOrderAfterPutsAndUpdates() {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(6);
        for (int i = 0; i < 6; i++) {
            lruCache.put(i, i + 1);
        }
        for (int i = 0; i < 6; i += 2) {
            lruCache.put(i, i + 1);
        }
        List<Integer> expectedKeysList = List.of(4, 2, 0, 5, 3, 1);
        List<Integer> orderedKeysList = lruCache.getOrderedKeys();
        for (int i = 0; i < 6; i++) {
            Assert.assertEquals(expectedKeysList.get(i), orderedKeysList.get(i));
        }
    }

    @Test
    public void testSimpleExtendCapacity() {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(6);
        for (int i = 0; i < 8; i++) {
            lruCache.put(i, i + 1);
        }
        List<Integer> orderedKeysList = lruCache.getOrderedKeys();
        for (int i = 0; i < 6; i++) {
            Assert.assertEquals(Integer.valueOf(7 - i), orderedKeysList.get(i));
        }
    }

    @Test
    public void testSimpleExtendCapacityWithUpdates() {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(10);
        for (int i = 0; i < 8; i++) {
            lruCache.put(i, i + 1);
        }
        for (int i = 0; i < 14; i += 2) {
            lruCache.put(i, i + 10);
        }
        List<Integer> expectedKeysList = List.of(12, 10, 8, 6, 4, 2, 0, 7, 5, 3);
        List<Integer> orderedKeysList = lruCache.getOrderedKeys();
        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(expectedKeysList.get(i), orderedKeysList.get(i));
        }
    }
}
