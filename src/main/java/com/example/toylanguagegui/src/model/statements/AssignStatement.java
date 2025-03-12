package com.example.toylanguagegui.src.model.statements;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.exceptions.StatementException;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.expresions.IExpression;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.types.IType;
import com.example.toylanguagegui.src.model.value.IValue;

public class AssignStatement implements IStatement{
    private String variableName;
    private IExpression expression;
    public AssignStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException,ExpressionException, ADTException {
        if (!prgState.getSymTable().contains(this.variableName)) {
            throw new StatementException("Variable was not found");
        }
        IValue value = prgState.getSymTable().getValue(this.variableName);
        IValue evalValue = this.expression.eval(prgState.getSymTable(), prgState.getHeap());
        if (!value.getType().equals(evalValue.getType())){
            throw new StatementException("Value type mismatch");
        }
        prgState.getSymTable().insert(this.variableName, evalValue);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(new String(this.variableName), this.expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        try {
            IType typeVar = typeEnv.lookup(variableName);

            IType typeExp = expression.typeCheck(typeEnv);
            if (typeVar.equals(typeExp)) {
                return typeEnv;
            } else {
                throw new StatementException("Assignment: right hand side and left hand side have different types");
            }
        }
        catch (Exception e){
            throw new StatementException(e.getMessage());
        }
    }

    @Override
    public String toString(){
        return this.variableName + " = " + this.expression.toString();
    }
}
