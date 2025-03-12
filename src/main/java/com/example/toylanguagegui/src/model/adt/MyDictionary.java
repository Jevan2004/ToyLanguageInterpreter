package com.example.toylanguagegui.src.model.adt;

import com.example.toylanguagegui.src.exceptions.KeyNotFoundException;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class MyDictionary<K, V> implements MyIDictionary<K, V>{
    private Map<K, V> map;

    public MyDictionary() {

        this.map = new HashMap<>();
    }

    public MyDictionary(Map<K, V> map) {
        this.map = new HashMap<>(map);
    }

    @Override
    public void insert(K key, V value) {
        this.map.put(key, value);
    }

    @Override
    public V getValue(K key) throws KeyNotFoundException {
        if (!this.map.containsKey(key)) {
            throw new KeyNotFoundException("Key doesn't exist");
        }
        return this.map.get(key);
    }

    @Override
    public void remove(K key) throws KeyNotFoundException {
        if (!this.map.containsKey(key)) {
            throw new KeyNotFoundException("Key doesn't exist");
        }
        this.map.remove(key);
    }

    @Override
    public boolean contains(K key) {

        return map.containsKey(key);
    }
    @Override
    public Map<K, V> getMap() {

        return map;
    }

    @Override
    public MyIDictionary<K, V> deepCopy() {
        return new MyDictionary<K,V>(this.map);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public Map<K, V> setMap(Map<K, V> map) {
        this.map = map;
        return this.map;
    }

    @Override
    public V lookup(K key) throws KeyNotFoundException {
        if (!this.map.containsKey(key)) {
            throw new KeyNotFoundException("Key doesn't exist");
        }
        return this.map.get(key);
    }

    @Override
    public void update(K key, V value) {
        if(map.get(key) != null) {
            this.map.put(key, value);
        }
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        for(K key: this.map.keySet()){
            str.append(key).append(" -> ").append(this.map.get(key)).append("\n");
        }
        return "MyDictionary contains " + str;
    }
    @Override
    public Set<K> getKeys() {
        return this.map.keySet();
    }
}
