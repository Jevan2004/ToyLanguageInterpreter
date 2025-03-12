package com.example.toylanguagegui.src.model.statements;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.exceptions.KeyNotFoundException;
import com.example.toylanguagegui.src.exceptions.StatementException;
import com.example.toylanguagegui.src.model.adt.IBarrierTable;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.types.IType;
import com.example.toylanguagegui.src.model.types.IntType;
import com.example.toylanguagegui.src.model.value.IValue;
import com.example.toylanguagegui.src.model.value.IntValue;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStatement implements IStatement{

    private String variable;
    private static Lock lock = new ReentrantLock();


    public AwaitStatement(String variable) {
        this.variable = variable;
    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException, ExpressionException, ADTException, IOException {
        lock.lock();

        MyIDictionary<String, IValue> symTable = prgState.getSymTable();
        IBarrierTable barrierTable = prgState.getBarrierTable();
        //check if the variable is in the symtable
        if(symTable.contains(variable)){
            //check if the variable is of type int
            if(symTable.lookup(variable).getType().equals(new IntType())){
                IntValue index = (IntValue) symTable.lookup(variable);
                //get it's associated value
                int foundIndex = index.getValue();
                //check if this value exists in the barrier table
                if(barrierTable.containsKey(foundIndex)){
                    //get the asociated values
                    Pair<Integer, List<Integer>> found = barrierTable.get(foundIndex);
                    //get n1 nlen
                    int N1 = found.getKey();
                    int Nl = found.getValue().size();
                    ArrayList<Integer> list = (ArrayList<Integer>) found.getValue();

                    if(N1 > Nl){
                        if(list.contains(prgState.getId())){
                            prgState.getExeStack().push(this);
                        }
                        else{
                            list.add(prgState.getId());
                            barrierTable.update(foundIndex,new Pair<>(N1,list));
                            prgState.getExeStack().push(this);
                        }
                    }
                }else{
                    throw new StatementException("Index is not a index in barrier table");
                }
            }else{
                throw new StatementException("Variable is not of type int");
            }
        }else{
            throw new StatementException("Variable does not exist in symbol table");
        }


        lock.unlock();
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AwaitStatement(variable);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException, ExpressionException, KeyNotFoundException {
        if(typeEnv.lookup(variable).equals(new IntType())){
            return typeEnv;
        }else {
            throw new StatementException("Variable is not of type int");
        }
    }
    @Override
    public String toString(){
        return "Await(" + variable + ")";
    }
}
