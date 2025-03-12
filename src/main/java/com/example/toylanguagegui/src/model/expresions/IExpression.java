package com.example.toylanguagegui.src.model.expresions;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.model.adt.MyIHeap;
import com.example.toylanguagegui.src.model.types.IType;
import com.example.toylanguagegui.src.model.value.IValue;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;

public interface IExpression {
    IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException, ExpressionException;
    IExpression deepCopy();
    IType typeCheck(MyIDictionary<String,IType> typeEnv) throws ExpressionException;
}
