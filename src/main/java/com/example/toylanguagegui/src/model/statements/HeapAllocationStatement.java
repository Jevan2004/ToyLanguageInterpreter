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

public class HeapAllocationStatement implements IStatement{

    private IExpression expression;
    private String variableName;

    public HeapAllocationStatement(String variableName, IExpression expression) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException, ExpressionException, ADTException, IOException {
        if(!prgState.getSymTable().contains(this.variableName)){
            throw new StatementException("Variable does not exist");
        }

        IValue value = prgState.getSymTable().getValue(this.variableName);

        if(!(value.getType() instanceof RefType)){
            throw new StatementException("Variable " + this.variableName + " is not of RefType");
        }

        RefType refType = (RefType) value.getType();
        IType locationType = refType.getInner();

        IValue evaluatedExpression = expression.eval(prgState.getSymTable(), prgState.getHeap());

        if(!evaluatedExpression.getType().equals(locationType)){
            throw new StatementException("Variable " + this.variableName + " is not of type " + locationType);
        }

        int newAddr = prgState.getHeap().allocate(evaluatedExpression);

        prgState.getSymTable().insert(this.variableName, new RefValue(newAddr,locationType));

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapAllocationStatement(variableName, this.expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException, KeyNotFoundException {
        IType typeVar = typeEnv.lookup(variableName);
        IType typeExp = expression.typeCheck(typeEnv);
        if(typeVar.equals(new RefType(typeExp))){
            return typeEnv;
        }
        else
            throw new StatementException("Heap alloc: right hand side and left hand side have different types");
    }

    @Override
    public String toString() {
        return "new(" + this.variableName + "," + this.expression.toString() + ")";
    }
}
