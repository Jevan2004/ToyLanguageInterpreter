package com.example.toylanguagegui.src.model.adt;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.model.value.IValue;

import java.util.HashMap;
import java.util.Map;

public class MyHeap implements MyIHeap{

    private Map<Integer,IValue> heap;
    private int nextFreeLocation;

    public MyHeap(){
        this.heap = new HashMap<>();
        this.nextFreeLocation = 1;
    }

    @Override
    public int allocate(IValue value) {
        heap.put(this.nextFreeLocation,value);
        this.nextFreeLocation++;
        return this.nextFreeLocation - 1;
    }

    @Override
    public void deallocate(int address) throws ADTException{
        if(!this.heap.containsKey(address)){
            throw new ADTException("Invalid address");
        }
        this.heap.remove(address);
    }

    @Override
    public IValue getValue(int key) throws ADTException {
        if(!this.heap.containsKey(key)){
            throw new ADTException("Key not found");
        }
        else {
            return this.heap.get(key);
        }
    }

    @Override
    public void set(int key, IValue value) throws ADTException {
        if(!this.heap.containsKey(key)){
            throw new ADTException("Key not found");
        }
        this.heap.put(key,value);
    }

    @Override
    public boolean containsKey(int key) {
        return this.heap.containsKey(key);
    }

    @Override
    public Map<Integer, IValue> getHeapMap() {
        return this.heap;
    }

    @Override
    public void setContent(Map<Integer, IValue> content) throws ADTException {
        this.heap = content;
    }

    @Override
    public void clear() throws ADTException {
        this.getHeapMap().clear();
        this.nextFreeLocation = 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for( Integer key : this.heap.keySet()){
            sb.append(key).append(" -> ").append(heap.get(key)).append("\n");
        }
        return sb.toString();
    }

}
