package com.example.toylanguagegui.src.model.value;

import com.example.toylanguagegui.src.model.types.IType;
import com.example.toylanguagegui.src.model.types.RefType;

public class RefValue implements IValue{
    int address;
    IType locationType;


    public RefValue(int address, IType locationType) {
        this.locationType = locationType;
        this.address = address;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public IType getLocationType() {
        return locationType;
    }

    public boolean equals(Object object){
        return object instanceof RefValue &&
                ((RefValue)object).getLocationType().equals(this.locationType) && ((RefValue)object).getAddress() == this.address;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public String toString() {
        return this.locationType.toString();
    }
}
