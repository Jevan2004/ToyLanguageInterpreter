package com.example.toylanguagegui.src.model.statements;
import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.exceptions.KeyNotFoundException;
import com.example.toylanguagegui.src.exceptions.StatementException;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.state.PrgState;
import com.example.toylanguagegui.src.model.types.IType;

import java.io.IOException;

public interface IStatement {
    PrgState execute(PrgState prgState) throws StatementException, ExpressionException, ADTException, IOException;
    IStatement deepCopy();
    MyIDictionary<String, IType> typeCheck(MyIDictionary <String, IType> typeEnv) throws StatementException, ExpressionException, KeyNotFoundException;
}
