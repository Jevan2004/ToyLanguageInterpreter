package com.example.toylanguagegui.src.model.statements;


import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.exceptions.KeyNotFoundException;
import com.example.toylanguagegui.src.exceptions.StatementException;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.expresions.IExpression;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.types.BoolType;
import com.example.toylanguagegui.src.model.types.IType;
import com.example.toylanguagegui.src.model.value.BoolValue;
import com.example.toylanguagegui.src.model.value.IValue;

public class IfStatement implements IStatement {
    private IStatement statementThan;
    private IStatement statementElse;
    private IExpression expression;

    public IfStatement(IStatement statementThan, IStatement statementElse, IExpression expression) {
        this.statementThan = statementThan;
        this.statementElse = statementElse;
        this.expression = expression;
    }

    public PrgState execute(PrgState state) throws StatementException, ADTException, ExpressionException {
        IValue value = expression.eval(state.getSymTable(), state.getHeap());
        if(!value.getType().equals(new BoolType())){
            throw new StatementException("Expression is not boolean");
        }
        if(((BoolValue) value).getValue()){
            state.getExeStack().push(statementThan);
        }
        else {
            state.getExeStack().push(statementElse);
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(this.statementThan.deepCopy(), this.statementElse.deepCopy(),this.expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        try {
            IType typeExp = expression.typeCheck(typeEnv);
            if (typeExp.equals(new BoolType())) {
                statementThan.typeCheck(typeEnv);
                statementElse.typeCheck(typeEnv.deepCopy());
                return typeEnv;
            }
            else throw new StatementException("Expression is not boolean");
        }
        catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        } catch (KeyNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "if(" + expression + "){" + statementThan + "}else{" + statementElse + "}\n";
    }

}
