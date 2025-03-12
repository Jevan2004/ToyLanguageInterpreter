package com.example.toylanguagegui.src.model.adt;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.model.value.IValue;

import java.util.Map;

public interface MyIHeap {
    public int allocate(IValue value);
    public void deallocate(int address) throws ADTException;
    public IValue getValue(int key) throws ADTException;
    public void set(int key, IValue value) throws ADTException;
    public boolean containsKey(int key);
    Map<Integer,IValue> getHeapMap();
    public void setContent(Map<Integer,IValue> content) throws ADTException;
    void clear() throws ADTException;
}
