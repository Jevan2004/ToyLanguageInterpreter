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
import java.io.IOException;

public class closeRFile implements IStatement{
    private IExpression expression;

    public closeRFile(IExpression expression) {
        this.expression = expression;
    }


    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionException, StatementException, IOException {
        var table = state.getSymTable();

        IValue res = this.expression.eval(table, state.getHeap());

        if(!res.getType().equals(new StringType())){
            throw new StatementException("The expression is not a string");
        }

        var fileTable = state.getFileTable();
        StringValue fileName = (StringValue) res;

        if(!fileTable.contains(fileName)){
            throw new StatementException("There is no file with this name");
        }

        BufferedReader bufferedReader = fileTable.getValue(fileName);
        bufferedReader.close();
        fileTable.remove(fileName);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new closeRFile(expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException, KeyNotFoundException {
        IType type = expression.typeCheck(typeEnv);
        if(!type.equals(new StringType())){
            throw new StatementException("The expression is not a string");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "closeRFile\n";
    }
}
