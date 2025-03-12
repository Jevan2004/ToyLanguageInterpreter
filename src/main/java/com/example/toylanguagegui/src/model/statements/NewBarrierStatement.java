package com.example.toylanguagegui.src.model.statements;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.exceptions.KeyNotFoundException;
import com.example.toylanguagegui.src.exceptions.StatementException;
import com.example.toylanguagegui.src.model.adt.IBarrierTable;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.adt.MyIHeap;
import com.example.toylanguagegui.src.model.expresions.IExpression;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.types.IType;
import com.example.toylanguagegui.src.model.types.IntType;
import com.example.toylanguagegui.src.model.value.IValue;
import com.example.toylanguagegui.src.model.value.IntValue;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewBarrierStatement implements IStatement{

    private String variable;
    private IExpression expression;
    private static Lock lock = new ReentrantLock();

    public NewBarrierStatement(String variable, IExpression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException, ExpressionException, ADTException, IOException {
        lock.lock();
        MyIDictionary<String, IValue> symTable = prgState.getSymTable();
        MyIHeap heap = prgState.getHeap();
        IBarrierTable barrierTable = prgState.getBarrierTable();
        //check if evaluation of the expression is of type int
        if(expression.eval(symTable,heap).getType().equals(new IntType())){
            //evaluate expression
            IntValue nr1 = (IntValue) expression.eval(symTable,heap);
            int nr = nr1.getValue();
            //get a free address from barrier table
            int freeAddress = barrierTable.getFreeAddress();
            //add a entry to barrier table
            barrierTable.put(freeAddress, new Pair<>(nr,new ArrayList<>()));
            //check if the variable is in the symtable
            if(symTable.contains(variable)){
                //if so assoociate the variable with the entry in the barrier table
                symTable.update(variable,new IntValue(freeAddress));
            }else {
                throw new StatementException("Variable '" + variable + "' does not exist in symbol Table");
            }
        }else {
            throw new StatementException("Expression is not an int");
        }
        lock.unlock();
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NewBarrierStatement(variable, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException, KeyNotFoundException {
        if(typeEnv.lookup(variable).equals(new IntType())){
                if (expression.typeCheck(typeEnv).equals(new IntType())) {
                    return typeEnv;
                }else{
                    throw new StatementException("Expression is not an int");
                }
        }else {
            throw new StatementException("Variable must be of type int");
        }
    }
    @Override
    public String toString(){
        return "NewBarrier(" + variable + "," + expression + ")";
    }
}
