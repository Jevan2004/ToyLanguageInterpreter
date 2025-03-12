package com.example.toylanguagegui.src.model.expresions;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.exceptions.KeyNotFoundException;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.adt.MyIHeap;
import com.example.toylanguagegui.src.model.types.IType;
import com.example.toylanguagegui.src.model.value.IValue;

public class VariableExpression implements IExpression {

    private String variable;

    public VariableExpression(String variable) {
        this.variable = variable;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException {
        return symTbl.getValue(variable);
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(variable);
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        try{
            return typeEnv.lookup(variable);
        } catch (KeyNotFoundException e) {
            throw new ExpressionException("Variable '" + variable + "' not found");
        }
    }

    public String toString(){
        return variable.toString();
    }
}
