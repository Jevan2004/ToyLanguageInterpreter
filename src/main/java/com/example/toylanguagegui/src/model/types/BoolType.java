package com.example.toylanguagegui.src.model.types;

import com.example.toylanguagegui.src.model.value.BoolValue;
import com.example.toylanguagegui.src.model.value.IValue;

public class BoolType implements IType{

    @Override
    public boolean equals(IType obj) {
        return obj instanceof BoolType;
    }

    public String toString() {
        return "bool";
    }

    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }
}
