package ch.heig.items.api.utils;

public class Tuple<K, V> {

    public K first;
    public V second;

    public Tuple(K first, V second){
        this.first = first;
        this.second = second;
    }
}
