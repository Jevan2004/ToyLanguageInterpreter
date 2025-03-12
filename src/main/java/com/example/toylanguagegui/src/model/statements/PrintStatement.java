package com.example.toylanguagegui.src.model.statements;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.exceptions.StatementException;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.expresions.IExpression;
import com.example.toylanguagegui.src.model.types.IType;
import com.example.toylanguagegui.src.model.value.IValue;

public class PrintStatement implements IStatement {
    private IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prgState) throws ADTException, ExpressionException {
        IValue result = this.expression.eval(prgState.getSymTable(), prgState.getHeap());
        prgState.getOutput().add(result.toString());
//        System.out.println(prgState.getOutput());
        return null;
    }
    @Override
    public IStatement deepCopy() {
        return new PrintStatement(this.expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
       try {
           expression.typeCheck(typeEnv);
       }
       catch (ExpressionException e) {
           throw new StatementException(e.getMessage());
       }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}