package com.example.toylanguagegui.src.View;

import com.example.toylanguagegui.src.View.command.Command;
import com.example.toylanguagegui.src.exceptions.ADTException;
import com.example.toylanguagegui.src.exceptions.ExpressionException;
import com.example.toylanguagegui.src.exceptions.StatementException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu() {
        this.commands = new HashMap<>();
    }

    public void addCommand(Command command) {
        this.commands.put(command.getKey(), command);
    }

    private void printMenu(){
        for (Command command : commands.values()) {
            String line = String.format("%4s: %s", command.getKey(), command.getDescription());
            System.out.println(line);
        }
    }

    public void show() throws StatementException, ADTException, IOException, ExpressionException {
        Scanner scanner = new Scanner(System.in);
        while(true){
            printMenu();
            System.out.print("Input the option: ");
            String key = scanner.nextLine();
            Command command = this.commands.get(key);
            if(command == null){
                System.out.println("Invalid option!");
                continue;
            }
            try {
                command.execute();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Try loading a correct command");
            }
        }
    }
}
