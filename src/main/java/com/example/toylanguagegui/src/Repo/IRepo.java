package com.example.toylanguagegui.src.Repo;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.RepoException;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.statements.IStatement;

import java.util.List;

public interface IRepo {
    //PrgState getCrtPrg();
    public void addProgramState(PrgState programState);
    public void clearProgramStates();
    List<PrgState> getProgramStates();
    void logPrgStateExec(PrgState prgState) throws RepoException;
    void setPrgList(List<PrgState> prgStates);
    void reinitializeMainProgram(IStatement statement) throws ADTException;
    IStatement getLoadedProgramState() throws ADTException;
    void notifyListeners();
    void addListener(Runnable listener);
}
