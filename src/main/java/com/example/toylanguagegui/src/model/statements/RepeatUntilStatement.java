package com.example.toylanguagegui.src.model.statements;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.exceptions.KeyNotFoundException;
import com.example.toylanguagegui.src.exceptions.StatementException;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.adt.MyIStack;
import com.example.toylanguagegui.src.model.expresions.IExpression;
import com.example.toylanguagegui.src.model.expresions.NotExp;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.types.BoolType;
import com.example.toylanguagegui.src.model.types.IType;
import com.example.toylanguagegui.src.model.value.IValue;

import java.io.IOException;

public class RepeatUntilStatement implements IStatement {

    private IStatement statement;
    private IExpression expression;

    public RepeatUntilStatement(IStatement statement, IExpression expression) {
        this.statement = statement;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException, ExpressionException, ADTException, IOException {
        MyIStack<IStatement> stack = prgState.getExeStack();
        IStatement converted = new CompStatement(statement,new WhileStatement(new NotExp(expression),statement));
        stack.push(converted);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new RepeatUntilStatement(statement.deepCopy(),expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException, KeyNotFoundException {
        IType type = expression.typeCheck(typeEnv);
        if(type.equals(new BoolType())){
            statement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }else {
            throw new StatementException("Expression is not a boolean");
        }
    }
    @Override
    public String toString() {
        return "repeat(" + statement.toString() + ")until(" + expression.toString() + ")";
    }
}
