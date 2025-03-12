package com.example.toylanguagegui.src.model.state;

import com.example.toylanguagegui.src.exceptions.*;
import com.example.toylanguagegui.src.model.adt.*;
import com.example.toylanguagegui.src.model.statements.IStatement;
import com.example.toylanguagegui.src.model.value.IValue;
import com.example.toylanguagegui.src.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class PrgState {
    private MyIDictionary<String, IValue> symTable;
    private MyIStack<IStatement> exeStack;
    private MyIList<String> output;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap heap;
    private IStatement initialStatement;
    private IBarrierTable barrierTable;

//    private IGarbageCollector gc;
    static int lastId;
    int id;

    static synchronized int idIncrement() {
//        lastId++;
        return ++lastId;
    }

    public int getId(){
        return id;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public void setSymTable(MyIDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public MyIStack<IStatement> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyIStack<IStatement> exeStack) {
        this.exeStack = exeStack;
    }

    public PrgState(MyIDictionary<String, IValue> symTable,MyIStack<IStatement> exeStack, MyIList<String> output,IStatement initialState, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap heap, IBarrierTable barrierTable ) {

        this.symTable = symTable;
        this.exeStack = exeStack;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        this.initialStatement = initialState;
        this.exeStack.push(this.initialStatement);
        this.id = idIncrement();
        this.barrierTable = barrierTable;
//        this.gc = new GarbageCollector(this.getSymTable(),this.getHeap());
    }

//    public IGarbageCollector getGarbageCollector(){
//        return this.gc;
//    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public MyIHeap getHeap() {
        return this.heap;
    }

    public void setHeap(MyIHeap heap) {
        this.heap = heap;
    }

    public String fileTableToString() {
        StringBuilder text = new StringBuilder();
        text.append("File Table:\n");
        for(StringValue key : this.fileTable.getKeys()){
            text.append(key).append("\n");
        }
        return text.toString();
    }

    public MyIList<String> getOutput() {
        return output;
    }

    public void setOutput(MyIList<String> output) {
        this.output = output;
    }

    public void clearOutput(){
        this.output.clear();
    }

    @Override
    public String toString() {
        return   "Program State id:" + this.id + "Execution Stack: " + exeStack.toString() + "\n" +
                "Symbol Table: " + symTable.toString() + "\n" +
                "Output: " + output.toString() + "\n"+
                "FileTable: " + fileTableToString() + "\n"
                + "Heap: " + heap.toString()
                + "BarrierTable: " + barrierTable.toString()
                +"\n----------------------------------";
    }

    public Boolean isNotCompleted(){
        return !this.getExeStack().isEmpty();
    }

    public void reinitializePrg(IStatement statement) throws ADTException {
        this.initialStatement = statement;
        this.exeStack.clear();
        this.heap.clear();
        this.output.clear();
        this.symTable.clear();
        this.fileTable.clear();
        this.barrierTable.clear();
//        this.exeStack.push(this.initialStatement);
    }

    public IBarrierTable getBarrierTable() {
        return barrierTable;
    }

    public void setBarrierTable(IBarrierTable barrierTable) {
        this.barrierTable = barrierTable;
    }
    public IStatement getStatement(){
        return this.initialStatement;
    }
    public PrgState oneStep() throws ProgramStateExeption,ExpressionException, ADTException, StatementException, IOException {

        if (this.exeStack.isEmpty()) {
            throw new ControllerException("PrgState Stack is empty");
        }

        IStatement crtStmt = this.exeStack.pop();

        return crtStmt.execute(this);
//        repo.logPrgStateExec();
//        if(this.displayFlag)
//        {
//            System.out.println("Current stmt: " + crtStmt);
//            this.display();
//        }
//
//        StringBuilder stringBuilder = new StringBuilder();
//        if(state.getOutput() != null)
//        {
//            for (String value : state.getOutput().getAll()) {
//                stringBuilder.append(value);
//                stringBuilder.append("\n");
//            }
//            //state.clearOutput();
//            return stringBuilder.toString();
//        }
//        return "";
    }
}
