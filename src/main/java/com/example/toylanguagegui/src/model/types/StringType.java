package com.example.toylanguagegui.src.model.types;

import com.example.toylanguagegui.src.model.value.IValue;
import com.example.toylanguagegui.src.model.value.StringValue;

public class StringType implements IType {
    public StringType() {}

    public String toString() {
        return "String";
    }

    @Override
    public boolean equals(IType obj){
        return obj instanceof StringType;
    }

    @Override
    public IValue defaultValue(){
        return new StringValue("");
    }
}
