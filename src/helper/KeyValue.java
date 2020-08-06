package helper;

import java.util.Map;

public class KeyValue<K, V> implements Map.Entry<K, V> {
    private K key;
    private V value;

    public KeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public KeyValue() {

    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V value) {
        return this.value = value;
    }

    public K setKey(K key) {
        return this.key = key;
    }
}
