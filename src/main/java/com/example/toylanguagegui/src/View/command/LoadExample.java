package com.example.toylanguagegui.src.View.command;

import com.example.toylanguagegui.src.Controller.Controller;
import com.example.toylanguagegui.src.Repo.IRepo;
import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ViewException;
import com.example.toylanguagegui.src.model.adt.MyDictionary;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.statements.IStatement;
import com.example.toylanguagegui.src.model.types.IType;

public class LoadExample extends Command{
    private IStatement statement;
    private Controller controller;
    private IRepo repo;
    public LoadExample(String key, String description, IRepo repo,Controller controller,IStatement statement) {
        super(key, description);
        this.repo = repo;
        this.controller = controller;
        this.statement = statement;
    }

    @Override
    public void execute() throws ADTException {
//        repo.clearProgramStates();
        MyIDictionary<String, IType> typeEnv = new MyDictionary<>();
        try {
            this.statement.typeCheck(typeEnv);
        }
        catch (Exception e) {
            System.out.println("Program failed the type check test");
            throw new ViewException(e.getMessage());
        }
        repo.reinitializeMainProgram(this.statement);
//        repo.addProgramState(state);
    }

}
