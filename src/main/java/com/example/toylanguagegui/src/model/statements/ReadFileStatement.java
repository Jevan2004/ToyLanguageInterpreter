package com.example.toylanguagegui.src.model.statements;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.exceptions.KeyNotFoundException;
import com.example.toylanguagegui.src.exceptions.StatementException;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.expresions.IExpression;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.types.IType;
import com.example.toylanguagegui.src.model.types.IntType;
import com.example.toylanguagegui.src.model.types.StringType;
import com.example.toylanguagegui.src.model.value.IntValue;
import com.example.toylanguagegui.src.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement {
    private IExpression expression;
    private String varName;

    public ReadFileStatement(IExpression expression, String varName) {
        this.expression = expression;
        this.varName = varName;
    }
    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException, ADTException, IOException {
        var table = state.getSymTable();

        if(!table.contains(varName)){
            throw new StatementException("The variable was not defined");
        }

        if(!table.getValue(varName).getType().equals(new IntType())){
            throw new StatementException("The type is incorrect");
        }

        var res = expression.eval(table, state.getHeap());

        if(!res.getType().equals(new StringType())){
            throw new StatementException("The result is not a String Type");
        }
        BufferedReader reader = state.getFileTable().getValue((StringValue) res);

        String read = reader.readLine();

        if(read.equals("")){
            read = "0";
        }

        int parser = Integer.parseInt(read);

        table.insert(varName,new IntValue(parser));

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFileStatement(expression.deepCopy(), varName);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException, KeyNotFoundException {
        IType type = expression.typeCheck(typeEnv);
        IType typeVal = typeEnv.lookup(varName);

        if(!type.equals(new StringType())){
            throw new StatementException("The type is incorrect");
        }

        if (!typeVal.equals(new IntType())) {
            throw new StatementException("Variable in ReadFileStatement is not of type Int");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "read\n";
    }
}
