package day23;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

public final class DefaultHashMap<T, V> {
    private final HashMap<T, ArrayList<V>> map;

    public DefaultHashMap() {
        this.map = new HashMap<T, ArrayList<V>>();
    }

    public Set<Entry<T, ArrayList<V>>> entrySet() {
        return this.map.entrySet();
    }

    public void put(T key, V value) {
        if (this.map.containsKey(key)) {
            this.map.get(key).add(value);
        } else {
            final ArrayList<V> arr = new ArrayList<V>();
            arr.add(value);
            this.map.put(key, arr);
        }
    }
}
