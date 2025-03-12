package com.example.toylanguagegui.src.model.adt;

import com.example.toylanguagegui.src.exceptions.KeyNotFoundException;

import java.util.Map;
import java.util.Set;

public interface MyIDictionary <K, V>{
    void insert(K key, V value);
    V getValue(K key) throws KeyNotFoundException;
    void remove(K key) throws KeyNotFoundException;
    boolean contains(K key);
    Set<K> getKeys();
    Map<K, V> getMap();
    MyIDictionary<K,V> deepCopy();
    void clear();
    Map<K,V> setMap(Map<K,V> map);
     V lookup(K key) throws KeyNotFoundException;
     void update(K key, V value);
}
