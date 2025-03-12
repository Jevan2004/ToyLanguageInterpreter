package com.example.toylanguagegui.src.Controller;

import com.example.toylanguagegui.src.Repo.IRepo;
import com.example.toylanguagegui.src.exceptions.*;
import com.example.toylanguagegui.src.model.adt.MyIHeap;
import com.example.toylanguagegui.src.model.garbageCollector.GarbageCollector;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.statements.IStatement;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller implements IController{

    private GarbageCollector garbageCollector;
    private IRepo repo;
    private boolean displayFlag = true;

    private ExecutorService executor;

    public Controller(IRepo repo) {
        this.repo = repo;
        this.garbageCollector = new GarbageCollector();
        this.executor = Executors.newFixedThreadPool(2);

    }

    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    public void addProgramState(PrgState newProgramState) {
        this.repo.addProgramState(newProgramState);
    }

    public IRepo getRepo() {
        return repo;
    }

//    @Override
//    public String oneStep(PrgState state) throws ControllerException, ExpressionException, ADTException, StatementException, IOException {
//        return "";
//    }

    @Override
    public List<String> allSteps() throws ControllerException, StatementException, ADTException, ExpressionException, IOException, InterruptedException {
        List<String> output = new ArrayList<>();
//        executor = Executors.newFixedThreadPool(2);

        List<PrgState> prgList = removeCompletedPrg(repo.getProgramStates());

        while (prgList.size() > 0) {
            MyIHeap heap = prgList.get(0).getHeap();
//            prgList.forEach(p->garbageCollector.garbageCollector(garbageCollector.getAddrFromSymTable(p.getSymTable().getMap().values()),heap.getHeapMap()));
            heap.setContent(garbageCollector.garbageCollectorAllSymTables(heap.getHeapMap(),prgList));
//            heapMap.con
            output = oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getProgramStates());
        }
//        executor.shutdown();
        repo.setPrgList(prgList);
        return output;
    }
    @Override
    public List<String> oneStep() throws ControllerException, StatementException, ADTException, ExpressionException, IOException, InterruptedException {
        List<PrgState> prgList = removeCompletedPrg(repo.getProgramStates());
//        repo.notifyListeners();

        if (prgList.isEmpty()) {
//            executor.shutdown();
//            repo.notifyListeners();
            throw new ControllerException("All programs are completed. Cannot execute further steps.");
        }

        MyIHeap heap = prgList.get(0).getHeap();
        heap.setContent(garbageCollector.garbageCollectorAllSymTables(heap.getHeapMap(), prgList));

        return oneStepForAllPrg(prgList);
    }


    @Override
    public void display() throws ControllerException {
//            PrgState currentState = this.repo.getCrtPrg();
//            System.out.println(currentState.toString());
    }

    @Override
    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) throws ControllerException {
        return inPrgList.stream()
                .filter(p->p.isNotCompleted())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> oneStepForAllPrg(List<PrgState> prgStateList) throws ControllerException, InterruptedException {
        this.removeCompletedPrg(prgStateList);
        List<String> output = new ArrayList<>();
        prgStateList.forEach(prgState -> {
            try {
                repo.logPrgStateExec(prgState);
            } catch (RepoException e){
                throw new RuntimeException(e.getMessage());
            }
        });

        List<Callable<PrgState>> callableList = prgStateList.stream()
                .map((PrgState p) -> (Callable<PrgState>) p::oneStep)
                .toList();
//        if (executor == null) {
//            executor = Executors.newFixedThreadPool(2);
//        }
        List<PrgState> newPrgList = executor.invokeAll(callableList).stream()
                .map(future->{try {
                    return future.get();
                }
                catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e.getMessage());
                }
                }).filter(Objects::nonNull)
                .toList();
        prgStateList.addAll(newPrgList);
        prgStateList.forEach(prgState -> repo.logPrgStateExec(prgState));
//        prgStateList.forEach(prgState -> {
//
////            System.out.println(output.getAll());
//        });
        output = prgStateList.getFirst().getOutput().getAll();
        repo.setPrgList(prgStateList);
        return output;
    }

    public void prepareExecution(){
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = repo.getProgramStates();
        repo.setPrgList(removeCompletedPrg(repo.getProgramStates()));
    }

    public void stopExecution(){
        executor.shutdown();
    }
    @Override
    public IStatement getLoadedProgram() throws ControllerException, ADTException {
        return repo.getLoadedProgramState();
    }

    @Override
    public void reinitializeProgram(IStatement statement) throws ADTException {
        this.repo.reinitializeMainProgram(statement);
//        repo.notifyListeners();
    }

    @Override
    public List<PrgState> getPrgList() throws ControllerException {
        return repo.getProgramStates();
    }

//    @Override
//    public List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
//        return symTableValues.stream()
//                .filter(value -> value instanceof RefValue)
//                .map(value -> ((RefValue) value).getAddress())
//                .collect(Collectors.toList());
//    }

//    @Override
//    public List<Integer> getAddrFromHeap(Map<Integer, IValue> heap, List<Integer> addreses) {
//        List<Integer> visited = new ArrayList<>(addreses);
//        List<Integer> toVisit = new ArrayList<>(addreses);
//
//        while (!toVisit.isEmpty()) {
//            int current = toVisit.removeFirst();
//            IValue value = heap.get(current);
//
//            if(value instanceof RefValue) {
//                int newAddress = ((RefValue) value).getAddress();
//
//                if (!visited.contains(newAddress)) {
//                    visited.add(newAddress);
//                    toVisit.add(newAddress);
//                }
//            }
//        }
//        return visited;
//    }

//    @Override
//    public Map<Integer, IValue> garbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap) {
//        List<Integer> rechableAdress = getAddrFromHeap(heap, symTableAddr);
//
//        return heap.entrySet().stream()
//                .filter(e->rechableAdress.contains(e.getKey()))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//    }

    public void clearRepo(){
        this.repo.clearProgramStates();
    }
}
