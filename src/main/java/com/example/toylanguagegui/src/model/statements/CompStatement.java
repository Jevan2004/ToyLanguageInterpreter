package com.example.toylanguagegui.src.model.statements;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.exceptions.KeyNotFoundException;
import com.example.toylanguagegui.src.exceptions.StatementException;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.types.IType;

public class CompStatement implements IStatement{
    private IStatement statement1;
    private IStatement statement2;

    public CompStatement(IStatement statement1, IStatement statement2) {
        this.statement1 = statement1;
        this.statement2 = statement2;
    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException, ADTException {
        prgState.getExeStack().push(statement2);
        prgState.getExeStack().push(statement1);

        return null;
    }

    @Override
    public IStatement deepCopy(){
        return new CompStatement(statement1.deepCopy(), statement2.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException, KeyNotFoundException {
        MyIDictionary <String,IType> typeEnv1 = statement1.typeCheck(typeEnv);
        MyIDictionary <String,IType> typeEnv2 = statement2.typeCheck(typeEnv1);
        return typeEnv2;
    }

    @Override
    public String toString() {
        return statement1.toString() + ";" + statement2.toString();
    }

}
