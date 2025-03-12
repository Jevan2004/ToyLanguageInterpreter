package com.example.toylanguagegui.src.model.adt;
import com.example.toylanguagegui.src.exceptions.EmptyStackException;

import java.util.Stack;

public interface MyIStack <T>{
    public void push(T element);
    public T pop() throws EmptyStackException;
    public int size();
    public boolean isEmpty();
    public void clear();
    public Stack<T> getStack();
//    public Stack<T> deepClone();
}
