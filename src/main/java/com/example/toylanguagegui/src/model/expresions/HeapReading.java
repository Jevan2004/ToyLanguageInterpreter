package com.example.toylanguagegui.src.model.expresions;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.adt.MyIHeap;
import com.example.toylanguagegui.src.model.types.IType;
import com.example.toylanguagegui.src.model.types.RefType;
import com.example.toylanguagegui.src.model.value.IValue;
import com.example.toylanguagegui.src.model.value.RefValue;

public class HeapReading implements IExpression{
    private IExpression expression;

    public HeapReading(IExpression expression){
        this.expression = expression;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException, ExpressionException {
        IValue evaluatedExpression = this.expression.eval(symTbl, heap);
        if(!(evaluatedExpression instanceof RefValue)){
            throw new ExpressionException("Expression is not a RefValue");
        }

        RefValue refValue = (RefValue) evaluatedExpression;

        int address = refValue.getAddress();

        if(!(heap.containsKey(address))){
            throw new ExpressionException("Heap doesn't contain:" + address + " address");
        }

        return heap.getValue(address);
    }

    @Override
    public IExpression deepCopy() {
        return new HeapReading(this.expression.deepCopy());
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        IType type = this.expression.typeCheck(typeEnv);
        if (type instanceof RefType){
            RefType refType = (RefType) type;
            return refType.getInner();
        }
        else {
            throw new ExpressionException("The heap reading argument is not a Ref Type");
        }
    }

    @Override
    public String toString() {
        return "rH(" + this.expression.toString() + ")";
    }
}
