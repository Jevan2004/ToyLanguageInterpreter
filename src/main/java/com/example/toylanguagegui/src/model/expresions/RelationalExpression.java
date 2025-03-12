package com.example.toylanguagegui.src.model.expresions;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.model.adt.MyIDictionary;
import com.example.toylanguagegui.src.model.adt.MyIHeap;
import com.example.toylanguagegui.src.model.types.BoolType;
import com.example.toylanguagegui.src.model.types.IType;
import com.example.toylanguagegui.src.model.types.IntType;
import com.example.toylanguagegui.src.model.value.BoolValue;
import com.example.toylanguagegui.src.model.value.IValue;
import com.example.toylanguagegui.src.model.value.IntValue;

public class RelationalExpression implements IExpression {

    private IExpression left;
    private IExpression right;
    private RelationalOperator operator;

    public RelationalExpression(IExpression left, RelationalOperator operator, IExpression right) {
        if (left == null || right == null || operator == null) {
            throw new IllegalArgumentException("Operands and operator cannot be null");
        }
        this.left = left;
        this.operator = operator;
        this.right = right;
    }


    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException, ExpressionException {
        IValue value1 = left.eval(symTbl, heap);
        IValue value2 = right.eval(symTbl, heap);

        if (!(value1.getType().equals(new IntType())) || !(value2.getType().equals(new IntType()))) {
            throw new ExpressionException("Both expressions must be of int type");
        }

        int val1 = ((IntValue) value1).getValue();
        int val2 = ((IntValue) value2).getValue();

        switch (operator) {
            case LESS:
                return new BoolValue(val1 < val2);
            case GREATER:
                return new BoolValue(val1 > val2);
            case LESS_EQUAL:
                return new BoolValue(val1 <= val2);
            case GREATER_EQUAL:
                return new BoolValue(val1 >= val2);
            case EQUAL:
                return new BoolValue(val1 == val2);
            case NOT_EQUAL:
                return new BoolValue(val1 != val2);
            default:
                throw new ExpressionException("Unknown operator: " + operator);

        }

    }

    @Override
    public IExpression deepCopy() {
        return null;
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        IType leftType = left.typeCheck(typeEnv);
        IType rightType = right.typeCheck(typeEnv);

        if (!leftType.equals(new IntType())) {
            throw new ExpressionException("Left operand of relational expression is not an integer.");
        }

        if (!rightType.equals(new IntType())) {
            throw new ExpressionException("Right operand of relational expression is not an integer.");
        }

        return new BoolType();
    }

    @Override
    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }

}
