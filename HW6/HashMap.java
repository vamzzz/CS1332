import java.util.List;
import java.util.Set;
import java.util.NoSuchElementException;
import java.util.HashSet;
import java.util.ArrayList;

/**
 * Your implementation of HashMap.
 * 
 * @author Vamshi Adimulam
 * @version 1.0
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this.table = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        this.table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
        this.size = 0;
    }

    @Override
    public V put(K key, V value) {
        V result = null;
        int i = 0;
        if (key == null) {
            throw new IllegalArgumentException("Can't insert null key.");
        } else if (value == null) {
            throw new IllegalArgumentException("Can't insert value key.");
        }
        if ((((double) size + 1) / table.length) > MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }
        int index = Math.abs(key.hashCode()) % table.length;
        int x = 0;
        boolean find = true;
        while (containsKey(key)) {
            if (table[index].getKey().equals(key)) {
                result = table[index].getValue();
                table[index].setValue(value);
                return result;
            } else {
                i++;
                index = ((Math.abs(key.hashCode()) + i * i) % table.length);
            }
        }
        while (find && x < table.length) {
            if (table[index] == null) {
                MapEntry<K, V> fill = new MapEntry<>(key, value);
                table[index] = fill;
                size++;
                return result;
            } else if (table[index].isRemoved()) {
                if (table[index].getKey().equals(key)) {
                    result = table[index].getValue();
                }
                table[index].setKey(key);
                table[index].setValue(value);
                table[index].setRemoved(false);
                size++;
                return result;
            } else if (table[index].getKey().equals(key)) {
                result = table[index].getValue();
                table[index].setValue(value);
                return result;
            } else {
                i++;
                index = ((Math.abs(key.hashCode()) + i * i) % table.length);
                x++;
            }
        }
        return result;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null.");
        }
        int i = 0;
        V result = null;
        int index = Math.abs(key.hashCode()) % table.length;
        boolean find = true;
        while (find) {
            if (table[index] == null) {
                throw new NoSuchElementException("Key doesn't exist.");  
            } else if (table[index].isRemoved()) {
                i++;
                index = (Math.abs(hashCode()) + i * i) % table.length;
            } else if (table[index].getKey().equals(key)) {
                result = table[index].getValue();
                table[index].setRemoved(true);
                size--;
                return result;
            } else {
                i++;
                index = (Math.abs(key.hashCode()) + (i * i)) % table.length;
            }
        }
        return result;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null.");
        }
        V result = null;
        int i = 0;
        int j = 0;
        int index = Math.abs(key.hashCode()) % table.length;
        boolean find = true;
        while (find) {
            if (table[index] == null) {
                throw new NoSuchElementException("Key doesn't exist.");
            } else if (table[index].isRemoved()) {
                i++;
                index = (Math.abs(key.hashCode()) + i * i) % table.length;
            } else if (table[index].getKey().equals(key)) {
                return table[index].getValue();
            } else {
                i++;
                index = (Math.abs(key.hashCode()) + (i * i)) % table.length;
            }
        }
        return result;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Try again. Passed in item"
                + " that is null.");
        }
        V result = null;
        int i = 0;
        int index = Math.abs(key.hashCode()) % table.length;
        boolean find = true;
        while (find) {
            if (table[index] == null) {
                return false;
            } else if (table[index].isRemoved()) {
                i++;
                index = (Math.abs(key.hashCode()) + i * i) % table.length;
            } else if (table[index].getKey().equals(key)) {
                return true;
            } else {
                i++;
                index = (Math.abs(key.hashCode()) + (i * i)) % table.length;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        table = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<>();
        for (int x = 0; x < table.length; x++) {
            if (table[x] != null && !table[x].isRemoved()) {
                set.add(table[x].getKey());
            }
        }
        return set;
    }

    @Override
    public List<V> values() {
        ArrayList<V> list = new ArrayList<>();
        for (int x = 0; x < table.length; x++) {
            if (table[x] != null && !table[x].isRemoved()) {
                list.add(table[x].getValue());
            }
        }
        return list;
    }

    @Override
    public void resizeBackingTable(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length is negative.");
        }
        if (length < size) {
            throw new IllegalArgumentException("Length is smaller than"
                + " array length.");
        }
        MapEntry<K, V>[] oldArray = table;
        size = 0;
        table = (MapEntry<K, V>[]) new MapEntry[length];
        for (int i = 0; i < oldArray.length; i++) {
            if (oldArray[i] != null) {
                put(oldArray[i].getKey(), oldArray[i].getValue());
            }
        }
    }
    
    @Override
    public MapEntry<K, V>[] getTable() {
        // DO NOT EDIT THIS METHOD!
        return table;
    }
}
