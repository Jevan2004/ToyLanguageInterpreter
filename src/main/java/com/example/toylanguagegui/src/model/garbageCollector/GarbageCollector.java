package com.example.toylanguagegui.src.model.garbageCollector;

import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.value.IValue;
import com.example.toylanguagegui.src.model.value.RefValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GarbageCollector implements IGarbageCollector{
//    private MyIDictionary<String, IValue> symTable;
//    private MyIHeap heap;


    @Override
    public List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(value -> value instanceof RefValue)
                .map(value -> ((RefValue) value).getAddress())
                .collect(Collectors.toList());
    }

    @Override
    public List<Integer> getAddrFromHeap(Map<Integer, IValue> heap, List<Integer> addreses) {
        List<Integer> visited = new ArrayList<>(addreses);
        List<Integer> toVisit = new ArrayList<>(addreses);

        while (!toVisit.isEmpty()) {
            int current = toVisit.removeFirst();
            IValue value = heap.get(current);

            if(value instanceof RefValue) {
                int newAddress = ((RefValue) value).getAddress();

                if (!visited.contains(newAddress)) {
                    visited.add(newAddress);
                    toVisit.add(newAddress);
                }
            }
        }
        return visited;
    }

    @Override
    public Map<Integer, IValue> garbageCollectorAllSymTables(Map<Integer, IValue> heap, List<PrgState> prgStateList) {
//        prgStateList.forEach(prgState -> {heap = garbageCollector(getAddrFromSymTable(prgState.getSymTable().getMap().values())}, heap));
        List<Integer> allAddresses = prgStateList.stream()
                .flatMap(prgState -> getAddrFromSymTable(prgState.getSymTable().getMap().values()).stream())
                .collect(Collectors.toList());

        return garbageCollector(allAddresses, heap);
    }

    @Override
    public Map<Integer, IValue> garbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap) {
        List<Integer> rechableAdress = getAddrFromHeap(heap, symTableAddr);

        return heap.entrySet().stream()
                .filter(e->rechableAdress.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
