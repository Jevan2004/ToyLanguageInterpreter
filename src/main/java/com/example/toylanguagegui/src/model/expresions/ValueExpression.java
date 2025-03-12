package com.example.toylanguagegui.src.model.expresions;


import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.adt.MyIHeap;
import com.example.toylanguagegui.src.model.types.IType;
import com.example.toylanguagegui.src.model.value.IValue;

public class ValueExpression implements IExpression {

    private IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) {
        return value;
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(value);
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        return value.getType();
    }

    public String toString(){
        return value.toString();
    }

}
