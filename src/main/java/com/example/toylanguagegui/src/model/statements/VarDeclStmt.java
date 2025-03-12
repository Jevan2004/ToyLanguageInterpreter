package com.example.toylanguagegui.src.model.statements;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.exceptions.StatementException;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.types.IType;

public class VarDeclStmt  implements IStatement{
    private String varName;
    private IType type;

    public VarDeclStmt(String varName, IType type){
        this.varName = varName;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException, ExpressionException, ADTException {
        if(prgState.getSymTable().contains(varName)){
            throw new StatementException("Variable already exists");
        }
        else {
                prgState.getSymTable().insert(this.varName, this.type.defaultValue());
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new VarDeclStmt(this.varName, this.type);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        typeEnv.insert(this.varName, this.type);
        return typeEnv;
    }

    @Override
    public String toString(){
        return this.type + " " + this.varName;
    }
}
