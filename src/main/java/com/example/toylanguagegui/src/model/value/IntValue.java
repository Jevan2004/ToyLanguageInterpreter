package com.example.toylanguagegui.src.model.value;

import com.example.toylanguagegui.src.model.types.IType;
import com.example.toylanguagegui.src.model.types.IntType;

public class IntValue implements IValue{
    private int value;

    public IntValue(int value) {
        this.value = value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    public boolean equals(IValue other) {
        return other instanceof IntValue && ((IntValue) other).value == this.value;
    }

    public int getValue(){
        return value;
    }

    @Override
    public String toString(){
        return Integer.toString(value);
    }
}
