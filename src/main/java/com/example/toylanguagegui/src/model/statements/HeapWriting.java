package com.example.toylanguagegui.src.model.statements;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.exceptions.KeyNotFoundException;
import com.example.toylanguagegui.src.exceptions.StatementException;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.expresions.IExpression;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.types.IType;
import com.example.toylanguagegui.src.model.types.RefType;
import com.example.toylanguagegui.src.model.value.IValue;
import com.example.toylanguagegui.src.model.value.RefValue;

import java.io.IOException;

public class HeapWriting implements IStatement{

    private IExpression expression;
    private String variableName;

    public HeapWriting(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException, ExpressionException, ADTException, IOException {
        var symTable = prgState.getSymTable();
        var heap = prgState.getHeap();

        if(!symTable.contains(variableName)) {
            throw new StatementException("The variable '" + variableName + "' does not exist");
        }

        IValue value = symTable.getValue(variableName);

        if(!(value.getType() instanceof RefType)) {
            throw new StatementException("The variable '" + variableName + "' is not a reference type");
        }

        RefValue refValue = (RefValue) value;
        if(!(heap.containsKey(refValue.getAddress()))){
            throw new StatementException("The variable '" + variableName + "' does not exist in the heap");
        }

        IValue evaluatedExpression = this.expression.eval(symTable, heap);

        if(!evaluatedExpression.getType().equals(refValue.getLocationType())){
            throw new StatementException("The variable '" + variableName + " is of type " + evaluatedExpression.getType() + " but the ref type is of type " + refValue.getLocationType() );
        }

        int address = refValue.getAddress();

        heap.set(address, evaluatedExpression);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapWriting(variableName, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException, KeyNotFoundException {

        IType typeVar = typeEnv.lookup(variableName);

        IType typeExp = this.expression.typeCheck(typeEnv);

        if(!typeVar.equals(new RefType(typeExp))){
            throw new StatementException("The variable '" + variableName + "' is not a reference type");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "wH(" + variableName + ")";
    }
}
