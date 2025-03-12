package com.example.toylanguagegui.src.model.garbageCollector;

import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.value.IValue;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IGarbageCollector {
    Map<Integer, IValue> garbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap);
    List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues);
    List<Integer> getAddrFromHeap(Map<Integer, IValue> heap, List<Integer> symTableAddresses);
    Map<Integer, IValue> garbageCollectorAllSymTables(Map<Integer, IValue> heap, List<PrgState> prgStateList);
    }