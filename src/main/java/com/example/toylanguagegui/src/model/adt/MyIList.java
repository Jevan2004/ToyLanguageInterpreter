package com.example.toylanguagegui.src.model.adt;

import java.util.List;

public interface MyIList <T>{
    void add(T element);
    void clear();
    List<T> getAll();
}
