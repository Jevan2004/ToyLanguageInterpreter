package com.example.toylanguagegui.src.View.command;

import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.exceptions.StatementException;

import java.io.IOException;

public abstract class Command {
    private String key, description;
    public Command(String key, String description){
        this.key = key;
        this.description = description;
    }
    public abstract void execute() throws StatementException, ADTException, IOException, ExpressionException;

    public String getKey(){
        return this.key;
    }

    public String getDescription(){
        return this.description;
    }
}
