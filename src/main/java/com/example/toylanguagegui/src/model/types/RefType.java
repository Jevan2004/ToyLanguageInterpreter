package com.example.toylanguagegui.src.model.types;

import com.example.toylanguagegui.src.model.value.IValue;
import com.example.toylanguagegui.src.model.value.RefValue;

public class   RefType implements IType{
    IType inner;

    public RefType(IType inner) {
        this.inner = inner;
    }

    public IType getInner() {
        return inner;
    }

    @Override
    public boolean equals(IType obj) {
        if(obj instanceof RefType) {
            return inner.equals(((RefType) obj).getInner());
        }
        else {
            return false;
        }
    }
    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }
    @Override
    public IValue defaultValue() {
        return new RefValue(0,inner);
    }
}
