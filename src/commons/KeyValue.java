package commons;

public class KeyValue <K, V>{
    public K key;
    public V value;

    public KeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        return key.equals(((KeyValue<K, V>)obj).key);
    }

    @Override
    public String toString() {
        return "KeyValue{" +
                "key=" + key.toString() +
                ", value=" + value.toString() +
                '}';
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }


}
