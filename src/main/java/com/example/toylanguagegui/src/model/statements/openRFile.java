package com.example.toylanguagegui.src.model.statements;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.exceptions.KeyNotFoundException;
import com.example.toylanguagegui.src.exceptions.StatementException;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.expresions.IExpression;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.types.IType;
import com.example.toylanguagegui.src.model.types.StringType;
import com.example.toylanguagegui.src.model.value.IValue;
import com.example.toylanguagegui.src.model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class openRFile implements IStatement{
    IExpression expression;

    public openRFile(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException, IOException {
        var table = state.getSymTable();

        IValue res = this.expression.eval(table, state.getHeap());

        if(!res.getType().equals(new StringType())){
            throw new StatementException("The expression is not a string");
        }

        var fileTable = state.getFileTable();
        StringValue fileName = (StringValue) res;

        if(fileTable.contains(fileName)){
            throw new StatementException("The file is already open");
        }
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader(fileName.getValue()));
        }
        catch (Exception e){
            throw new StatementException("The file is not open");
        }

        fileTable.insert(fileName,bufferedReader);

        return null;
    }

    @Override
    public String toString() {
        return "openRFile(" + expression.toString() + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new openRFile(expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException, KeyNotFoundException {
        IType type = this.expression.typeCheck(typeEnv);
        if(!type.equals(new StringType())){
            throw new StatementException("The expression is not a string");
        }
        return typeEnv;
    }
}
