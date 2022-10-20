import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LRUCache<K, V> {
    private final HashMap<K, Node<K, V>> entries;
    private final Node<K, V> head;
    private Node<K, V> tail;
    private final int capacity;
    private int size = 0;

    private static class Node<K, V> {
        private final K key;
        private V val;
        private Node<K, V> prev, next;

        Node(K key, V val) {
            this.key = key;
            this.val = val;
            this.prev = null;
            this.next = null;
        }

        Node() {
            this(null, null);
        }
    }

    LRUCache(int capacity) {
        assert capacity > 0;
        this.head = this.tail = new Node<>();
        this.capacity = capacity;
        this.entries = new HashMap<>();
    }


    public V get(K key) {
        if (entries.containsKey(key)) {
            Node<K, V> foundValue = entries.get(key);
            moveToTail(foundValue);
            assert this.tail == foundValue;
            return foundValue.val;
        }
        return null;
    }

    public void put(K key, V value) {
        if (entries.containsKey(key)) {
            Node<K, V> nodeToBeUpdated = entries.get(key);
            nodeToBeUpdated.val = value;
            moveToTail(nodeToBeUpdated);
            assert this.tail == nodeToBeUpdated;
        } else {
            if (size == capacity) {
                remove(head.next.key);
            }
            int sizeBeforePut = this.size;
            Node<K, V> nodeToBeAdded = new Node<>(key, value);
            addToTail(nodeToBeAdded);
            entries.put(key, nodeToBeAdded);
            size++;
            assert this.tail == nodeToBeAdded;
            assert size == sizeBeforePut + 1;

        }
    }

    public void remove(K key) {
        if (entries.containsKey(key)) {
            int sizeBeforeRemove = this.size;
            Node<K, V> nodeToBeRemoved = entries.get(key);
            removeNode(nodeToBeRemoved);
            entries.remove(key);
            size--;
            assert sizeBeforeRemove == size + 1;
        }
    }


    private void moveToTail(Node<K, V> nodeToBeMoved) {
        int sizeBeforeMove = this.size;
        removeNode(nodeToBeMoved);
        addToTail(nodeToBeMoved);
        assert sizeBeforeMove == size;
        assert this.tail == nodeToBeMoved;
    }

    private void removeNode(Node<K, V> nodeToBeRemoved) {
        Node<K, V> prev = nodeToBeRemoved.prev,
                next = nodeToBeRemoved.next;
        if (prev != null) {
            prev.next = next;
        }
        if (next != null) {
            next.prev = prev;
        } else {
            this.tail = prev;
        }
    }

    private void addToTail(Node<K, V> nodeToBeAdded) {
        nodeToBeAdded.prev = this.tail;
        nodeToBeAdded.next = null;
        this.tail.next = nodeToBeAdded;
        this.tail = nodeToBeAdded;
    }

    public List<K> getOrderedKeys() {
        List<K> orderedKeysList = new ArrayList<>();
        Node<K, V> i = this.tail;
        while (i.prev != null) {
            orderedKeysList.add(i.key);
            i = i.prev;
        }
        return orderedKeysList;
    }
}
