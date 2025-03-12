package com.example.toylanguagegui.src.View.command;

import com.example.toylanguagegui.src.Controller.Controller;
import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.exceptions.StatementException;

import java.io.IOException;
import java.util.List;

public class OneStep extends Command {
    private Controller controller;

    public OneStep(String key, String description,Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() throws StatementException, ADTException, IOException, ExpressionException {
        try {
            List<String> output = controller.oneStepForAllPrg(controller.getRepo().getProgramStates());
             var proglist= controller.getRepo().getProgramStates();
             System.out.println("Current output:");
            for(String el:output){
                System.out.println(el);
            }
             System.out.println();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
