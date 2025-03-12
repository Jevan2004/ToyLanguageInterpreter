package com.example.toylanguagegui.src.model.statements;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.exceptions.KeyNotFoundException;
import com.example.toylanguagegui.src.exceptions.StatementException;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.adt.MyIHeap;
import com.example.toylanguagegui.src.model.expresions.IExpression;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.types.BoolType;
import com.example.toylanguagegui.src.model.types.IType;
import com.example.toylanguagegui.src.model.value.BoolValue;
import com.example.toylanguagegui.src.model.value.IValue;

import java.io.IOException;

public class WhileStatement implements IStatement{
    private IExpression condition;
    private IStatement statement;

    public WhileStatement(IExpression condition, IStatement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException, ExpressionException, ADTException, IOException {
        MyIDictionary<String, IValue> symTable = prgState.getSymTable();
        MyIHeap heap= prgState.getHeap();

        IValue evaluatedExpression = this.condition.eval(symTable, heap);

        if(!evaluatedExpression.getType().equals(new BoolType())){
            throw new StatementException("Condition is not a boolean type");
        }

        BoolValue boolValue = (BoolValue) evaluatedExpression;

        if(boolValue.getValue()){
            prgState.getExeStack().push(this);
            prgState.getExeStack().push(statement);
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(condition.deepCopy(), statement.deepCopy());

    }

        @Override
        public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException, KeyNotFoundException {
            IType typeVal = this.condition.typeCheck(typeEnv);
            if(!typeVal.equals(new BoolType())){
                throw new StatementException("Condition is not a boolean type");
            }
            this.statement.typeCheck(typeEnv);
            return typeEnv;
        }

    @Override
    public String toString() {
        return "while(" + condition.toString() + "){" + statement.toString() + "}";
    }
}
