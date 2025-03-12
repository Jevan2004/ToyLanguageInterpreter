package com.example.toylanguagegui.src.model.expresions;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.adt.MyIHeap;
import com.example.toylanguagegui.src.model.types.IType;
import com.example.toylanguagegui.src.model.types.IntType;
import com.example.toylanguagegui.src.model.value.IValue;
import com.example.toylanguagegui.src.model.value.IntValue;

public class AritmeticalExpression implements IExpression {
    private IExpression left;
    private IExpression right;
    private AritmeticalOperator operator;

    public AritmeticalExpression(IExpression left, AritmeticalOperator operator, IExpression right) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException, ExpressionException {
        IValue value1 = left.eval(symTbl, heap);
        IValue value2 = right.eval(symTbl, heap);
        if (!value1.getType().equals(new IntType())) {
            throw new ExpressionException("First value is not int");
        }
        if (!value2.getType().equals(new IntType())) {
            throw new ExpressionException("Second value is not int");
        }

        IntValue int1 = (IntValue) value1;
        IntValue int2 = (IntValue) value2;

        switch (operator) {
            case ADD:
                return new IntValue(int1.getValue() + int2.getValue());
            case SUBTRACT:
                return new IntValue(int1.getValue() - int2.getValue());
            case MULTIPLY:
                return new IntValue(int1.getValue() * int2.getValue());
            case DIVIDE:
            {
                if(int2.getValue() == 0)
                    throw new ExpressionException("Divide by zero");
                return new IntValue(int1.getValue() / int2.getValue());
            }
            default:
                throw new ExpressionException("Unknown operator");

        }

    }
    @Override
    public String toString() {
        return left.toString() + " " + operator.toString() + " " + right.toString();
    }
    @Override
    public IExpression deepCopy() {
        return new AritmeticalExpression(this.left.deepCopy(),  this.operator,  this.right);
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        IType leftType, rightType;
        leftType = left.typeCheck(typeEnv);
        rightType = right.typeCheck(typeEnv);

        if(leftType.equals(new IntType())){
            if(rightType.equals(new IntType())){
                return new IntType();
            }
            else
                throw new ExpressionException("Second operand is not an integer");
        }
        else
            throw new ExpressionException("First operand is not an integer");
    }
}
