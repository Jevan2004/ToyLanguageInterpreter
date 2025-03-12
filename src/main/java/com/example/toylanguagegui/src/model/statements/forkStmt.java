package com.example.toylanguagegui.src.model.statements;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.exceptions.KeyNotFoundException;
import com.example.toylanguagegui.src.exceptions.StatementException;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.adt.MyIStack;
import com.example.toylanguagegui.src.model.adt.MyStack;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.types.IType;

import java.io.IOException;

public class forkStmt implements IStatement{
    private IStatement statement;

    public forkStmt(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException, ExpressionException, ADTException, IOException {
        MyIStack<IStatement> exeStack2 = new MyStack<>();
        //exeStack2.push(statement.deepCopy());
        return new PrgState(prgState.getSymTable().deepCopy(),exeStack2,prgState.getOutput(),this.statement,prgState.getFileTable(),prgState.getHeap(),prgState.getBarrierTable());
    }

    @Override
    public IStatement deepCopy() {
        return new forkStmt(this.statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException, KeyNotFoundException {
        this.statement.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + this.statement + ")";
    }
}
