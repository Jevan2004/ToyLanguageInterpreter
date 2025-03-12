package com.example.toylanguagegui.src.model.types;

import com.example.toylanguagegui.src.model.value.IValue;

public interface IType {
    boolean equals(IType obj);
    IValue defaultValue();
}
