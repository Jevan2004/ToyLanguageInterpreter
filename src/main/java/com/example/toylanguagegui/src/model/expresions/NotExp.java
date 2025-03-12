package com.example.toylanguagegui.src.model.expresions;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.adt.MyIHeap;
import com.example.toylanguagegui.src.model.types.IType;
import com.example.toylanguagegui.src.model.value.BoolValue;
import com.example.toylanguagegui.src.model.value.IValue;
import com.example.toylanguagegui.src.model.value.IntValue;

public class NotExp implements IExpression{

    private IExpression expression;

    public NotExp(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException, ExpressionException {
        BoolValue value = (BoolValue) expression.eval(symTbl, heap);
        if(!value.getValue()){
            return new BoolValue(true);
        }
        else {
            return new BoolValue(false);
        }
    }

    @Override
    public IExpression deepCopy() {
        return new NotExp(expression.deepCopy());
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        return expression.typeCheck(typeEnv);
    }

    @Override
    public String toString() {
        return "!" + expression.toString();
    }
}
