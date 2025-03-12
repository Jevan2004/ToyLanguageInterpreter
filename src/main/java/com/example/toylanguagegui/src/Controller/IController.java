package com.example.toylanguagegui.src.Controller;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ControllerException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.exceptions.StatementException;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.statements.IStatement;

import java.io.IOException;
import java.util.List;

public interface IController {
//    String oneStep(PrgState state) throws ControllerException, ExpressionException, ADTException, StatementException, IOException;
List<String> allSteps() throws ControllerException, StatementException, ADTException, ExpressionException, IOException, InterruptedException;
    void display() throws ControllerException;
    //List<Integer> getAddrFromHeap(Map<Integer,IValue> heap, List<Integer> symTable);
    //Map<Integer,IValue> garbageCollector(List<Integer> symTableAddr, Map<Integer,IValue> heap);
    //List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues);
    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) throws ControllerException;
    List<String> oneStepForAllPrg(List<PrgState> prgStateList) throws ControllerException, InterruptedException;
    IStatement getLoadedProgram() throws ControllerException, ADTException;
    void reinitializeProgram(IStatement statement) throws ADTException;
    List<PrgState> getPrgList() throws ControllerException;
    public List<String> oneStep() throws ControllerException, StatementException, ADTException, ExpressionException, IOException, InterruptedException;
    void prepareExecution();
    public void stopExecution();
}
