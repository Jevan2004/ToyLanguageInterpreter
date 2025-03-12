package com.example.toylanguagegui.src.model.types;

import com.example.toylanguagegui.src.model.value.IValue;
import com.example.toylanguagegui.src.model.value.IntValue;

public class IntType implements IType{

    @Override
    public boolean equals(IType obj) {
        return obj instanceof IntType;
    }

    public String toString(){
        return "int";
    }

    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }
}
