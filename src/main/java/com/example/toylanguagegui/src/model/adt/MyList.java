package com.example.toylanguagegui.src.model.adt;


import java.util.ArrayList;
import java.util.List;


public class MyList<T> implements MyIList<T>{
    private List<T> list;

    public MyList(){
        this.list = new ArrayList<>();
    }

    @Override
    public synchronized void  add(T element) {
        this.list.add(element);
    }

    @Override
    public void clear() {
        this.list.clear();
    }

    @Override
    public List<T> getAll() {
        return this.list;
    }


    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        for(T element: this.list){
//            System.out.println(element);
            str.append(element).append("\n");
        }
        return "MyList contains " + str;
    }
}
