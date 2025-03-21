package com.example.toylanguagegui.src.model.adt;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public class BarrierTable implements IBarrierTable{

    private HashMap<Integer, Pair < Integer, List<Integer>>> barrierTable;
    private int freeLocation;

    public BarrierTable() {
        barrierTable = new HashMap<>();
    }

    @Override
    public void put(int key, Pair<Integer, List<Integer>> value) {
        synchronized (this) {
            barrierTable.put(key, value);
        }
    }

    @Override
    public Pair<Integer, List<Integer>> get(int key) {
        synchronized (this) {
            return barrierTable.get(key);
        }
    }

    @Override
    public boolean containsKey(int key) {
        synchronized (this) {
            return barrierTable.containsKey(key);
        }
    }

    @Override
    public int getFreeAddress() {
        synchronized (this) {
            freeLocation++;
            return freeLocation;
        }
    }

    @Override
    public void setFreeAddress(int address) {
        synchronized (this) {
            freeLocation = address;
        }
    }

    @Override
    public void update(int key, Pair<Integer, List<Integer>> value) {
        synchronized (this) {
            barrierTable.replace(key, value);
        }
    }

    @Override
    public HashMap<Integer, Pair<Integer, List<Integer>>> getBarrierTable() {
        synchronized (this) {
            return barrierTable;
        }
    }

    @Override
    public void setBarrierTable(HashMap<Integer, Pair<Integer, List<Integer>>> newBarrierTable) {
            synchronized (this) {
                barrierTable = newBarrierTable;
            }
    }

    @Override
    public void clear() {
        synchronized (this) {
            setFreeAddress(0);
            barrierTable.clear();
        }
    }

    @Override
    public String toString() {
        return barrierTable.toString();
    }
}
