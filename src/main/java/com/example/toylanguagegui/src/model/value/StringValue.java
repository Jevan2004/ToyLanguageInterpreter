package com.example.toylanguagegui.src.model.value;

import com.example.toylanguagegui.src.model.types.IType;
import com.example.toylanguagegui.src.model.types.StringType;

public class StringValue implements IValue {
    private String value;
    public StringValue(String value){
        this.value = value;
    }

    @Override
    public IType getType(){
        return new StringType();
    }

    public String getValue(){
        return value;
    }

    public void setValue(String value){
        this.value = value;
    }

    public boolean equals(IValue val){
        return val.getType().equals(new StringType()) && ((StringValue) val).getValue().equals(this.value);
    }

    public String toString(){
        return value;
    }
}
