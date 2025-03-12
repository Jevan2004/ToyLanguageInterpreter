package com.example.toylanguagegui.src.Repo;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.RepoException;
import com.example.toylanguagegui.src.model.adt.*;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.statements.IStatement;
import com.example.toylanguagegui.src.model.statements.NopStatement;
import com.example.toylanguagegui.src.model.value.IValue;
import com.example.toylanguagegui.src.model.value.StringValue;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repo implements IRepo{

    private List<PrgState> programStates;
    private List<Runnable> listeners = new ArrayList<>();
    private String logFilePath;
    private int currentIndex;
    private PrgState mainProgram;

    public Repo(String logFilePath) {
        programStates = new ArrayList<PrgState>();
        this.logFilePath = logFilePath;
        this.currentIndex = 0;
        MyIHeap heap = new MyHeap();
        MyIList<String> output = new MyList<>();
        MyIStack<IStatement> exeStack = new MyStack<>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        IBarrierTable barrierTable = new BarrierTable();
        mainProgram = new PrgState(symTable,exeStack,output,null,fileTable ,heap,barrierTable);
        programStates.add(mainProgram);
    }

    @Override
    public List<PrgState> getProgramStates() {
        return this.programStates;
    }

//    @Override
//    public PrgState getCrtPrg() throws RepoException {
//        if (programStates.isEmpty()) {
//            throw new RepoException("No program states found");
//        }
//        return programStates.get(this.currentIndex);
//    }

    @Override
    public void addProgramState(PrgState programState){
        this.programStates.add(programState);
//        notifyListeners();
    }

    @Override
    public void clearProgramStates() {
        programStates.clear();
//        notifyListeners();
    }

    @Override
    public void logPrgStateExec(PrgState prgState) throws RepoException {
        try{
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath,true)));
            logFile.println(prgState.toString());
            logFile.close();
        } catch (IOException e) {
            throw new RepoException("File does not exists");
        }
    }

    @Override
    public void setPrgList(List<PrgState> prgStates) {
        this.programStates = prgStates;
    }

    public void reinitializeMainProgram(IStatement statement) throws RepoException, ADTException {
        this.clearProgramStates();
        this.mainProgram.reinitializePrg(statement);
        PrgState prgState = new PrgState(mainProgram.getSymTable(),mainProgram.getExeStack(),mainProgram.getOutput(),statement,mainProgram.getFileTable(),mainProgram.getHeap(), mainProgram.getBarrierTable());
        this.programStates.add(prgState);
    }

    @Override
    public IStatement getLoadedProgramState() throws ADTException {
        return this.mainProgram.getStatement();
    }
    public void addListener(Runnable listener){
        listeners.add(listener);
    }
    @Override
    public void notifyListeners(){
        for(Runnable listener : listeners){
            listener.run();
        }
    }
}

