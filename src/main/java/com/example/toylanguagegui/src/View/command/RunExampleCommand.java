package com.example.toylanguagegui.src.View.command;

import com.example.toylanguagegui.src.Controller.Controller;

import java.util.List;

public class RunExampleCommand extends Command {

    private Controller controller;

    public RunExampleCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            System.out.println("Final output");
            List<String> output = controller.allSteps();
            for(String el:output){
                System.out.println(el);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
