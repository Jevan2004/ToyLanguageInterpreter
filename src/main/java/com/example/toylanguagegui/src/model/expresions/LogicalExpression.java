package com.example.toylanguagegui.src.model.expresions;


import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.adt.MyIHeap;
import com.example.toylanguagegui.src.model.types.BoolType;
import com.example.toylanguagegui.src.model.types.IType;
import com.example.toylanguagegui.src.model.value.BoolValue;
import com.example.toylanguagegui.src.model.value.IValue;

public class LogicalExpression implements IExpression {
    private IExpression left;
    private IExpression right;
    private LogicalOperator operator;

    public LogicalExpression(IExpression left, LogicalOperator operator, IExpression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException, ExpressionException {
        IValue evaluatedExpressionLeft = left.eval(symTbl, heap);
        IValue evaluatedExpressionRight = right.eval(symTbl, heap);
        if (!evaluatedExpressionLeft.getType().equals(new BoolType())) {
            throw new ExpressionException("Left expression is not of type BoolType");
        }
        if (!evaluatedExpressionRight.getType().equals(new BoolType())) {
            throw new ExpressionException("Right expression is not of type BoolType");
        }
        switch (operator) {
            case AND:
                return new BoolValue(((BoolValue) evaluatedExpressionLeft).getValue() &&
                        ((BoolValue) evaluatedExpressionRight).getValue());
            case OR:
                return new BoolValue(((BoolValue) evaluatedExpressionLeft).getValue() ||
                        ((BoolValue) evaluatedExpressionRight).getValue());
            default:
                throw new ExpressionException("Unknown operator");
        }
    }

    @Override
    public IExpression deepCopy() {
        return new LogicalExpression(left.deepCopy(), operator, right.deepCopy());
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        IType leftType, rightType;
        leftType = left.typeCheck(typeEnv);
        rightType = right.typeCheck(typeEnv);

        if(leftType.equals(new BoolType())) {
            if(rightType.equals(new BoolType())) {
                return new BoolType();
            }
            else {
                throw new ExpressionException("Right expression is not of type BoolType");
            }
        }else {
            throw new ExpressionException("Left expression is not of type BoolType");
        }
    }

    @Override
    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }
}
